package inf112.skeleton.app.main;


import com.badlogic.gdx.Input;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import inf112.skeleton.app.board.Board;
import inf112.skeleton.app.board.BoardParser;
import inf112.skeleton.app.board.Direction;
import inf112.skeleton.app.networking.MPClient;
import inf112.skeleton.app.networking.MPServer;
import inf112.skeleton.app.networking.Packets;
import inf112.skeleton.app.objects.player.Player;
import inf112.skeleton.app.objects.player.Robot;

import java.net.InetAddress;
import java.util.ArrayList;
import java.util.ConcurrentModificationException;
import java.util.HashMap;



/**
 * The Game class is the centerpiece that ties all the different parts of the game together.<BR> It sends information to the
 * MPClient which sends it to the server.<BR> It creates all the player and board objects, and then keeps control of
 * it.
 */
public class Game{
    private Board board;
    private MPClient client;
    private Player myPlayer;
    private int nrOfPlayers;
    private HashMap<Integer, Player> idPlayerHash;
    private String[] names;
    private int cardBoxLeft;
    private int cardBoxRight;
    private int buttonReadyLeftX;
    private int buttonReadyLeftY;
    private ArrayList<Packets.Packet02Cards> allCards;
    private MPServer server;
    private TurnHandler turnHandler;
    private boolean host;
    private long laserTime;

    private Texture tempMap;
    private Texture selectedFrame;
    private Texture buttonReady;
    private Texture buttonReadySelected;
    private Texture[] robotLaser;
    private Texture[] damageTokens;
    private Texture[] lifeTokens;
    private boolean[] allReady;
    private boolean[] playersShutdown;
    private boolean renderRobotLasers;
    private String boardName;


    /**
     * This method calls all the methods needed to start the "playing" part of the game.
     */
    public void create() {
        textureSetUp();
        cardBoxSetUp();
    }

    public void createBoardAndPlayers(String board){
        boardSetUp(board);
        playerSetup();
    }




    public boolean keyUp(int keycode) {
            switch (keycode) {
                case Input.Keys.UP:
                    board.moveSelected(Direction.NORTH);
                    break;
                case Input.Keys.DOWN:
                    board.moveSelected(Direction.SOUTH);
                    break;
                case Input.Keys.LEFT:
                    board.moveSelected(Direction.WEST);
                    break;
                case Input.Keys.RIGHT:
                    board.moveSelected(Direction.EAST);
                    break;
                default:
                    break;
            }
            return true;
        }

    public boolean touchDown(int screenX, int screenY, int pointer, int button){
        //checks if the click occurs in the "cardbox"

        if (screenX > cardBoxLeft &&
                screenX < cardBoxRight &&
                screenY > Settings.SCREEN_HEIGHT - Settings.CARD_HEIGHT &&
                screenY < Settings.SCREEN_HEIGHT) {
            int card = (screenX - cardBoxLeft) / Settings.CARD_WIDTH;
            if(myPlayer.getCards()[0] != null && myPlayer.getSelectedCards().size < 6) {
                myPlayer.addSelectedCard(card);
            }
        }
        //checks if the click occurs on the "ready-button"
        else if (screenX > Settings.SCREEN_WIDTH-(Settings.SCREEN_WIDTH/4) &&
                screenX < Settings.SCREEN_WIDTH-(Settings.SCREEN_WIDTH/4)+64 &&
                screenY > (Settings.SCREEN_HEIGHT-(Settings.SCREEN_HEIGHT/3))-32&&
                screenY < (Settings.SCREEN_HEIGHT-(Settings.SCREEN_HEIGHT/3)) && !myPlayer.getReadyButton() && myPlayer.getArrayCards().length == 5){
            if (myPlayer.getSelectedCards().size == 5 && !myPlayer.getDead()){
                client.sendCards(myPlayer.getArrayCards());
                myPlayer.setReadyButton(true);
            }
            else if (myPlayer.getSelectedCards().size == myPlayer.getRobot().getHealth() && !myPlayer.getDead()){
                client.sendCards(myPlayer.getArrayCards());
                myPlayer.setReadyButton(true);
            }
        }
        return false;
    }

    public boolean touchDragged(int screenX, int screenY, int pointer){
        return false;
    }

    //IMPORTANT
    //An object has to be initialized before being rendered
    public void render(SpriteBatch batch, BitmapFont font) {
        batch.draw(tempMap, Settings.BOARD_LOC_X , Settings.BOARD_LOC_Y);
        renderRobots(batch);
        renderCards(batch, font);
        renderHealthAndLife(batch);
        renderReadyButton(batch);
        renderNames(batch, font);
        if(renderRobotLasers) renderRobotLasers(batch);
    }

    /**
     * Renders the names of the players in the game
     * @param batch
     * @param font
     */
    private void renderNames(SpriteBatch batch, BitmapFont font) {
        font.setColor(Color.WHITE);
        font.draw(batch, "Players in game:", Settings.SCREEN_WIDTH / 16, (Settings.SCREEN_HEIGHT / 18) * 11);
        for (int i = 0; i < names.length; i++) {
            if(names[i] == null) continue;
            font.draw(batch, names[i], Settings.SCREEN_WIDTH / 16, (Settings.SCREEN_HEIGHT / 36) * (22 - i));
            batch.draw(idPlayerHash.get(i).getRobot().getNonRotatingTexture(),
                    Settings.SCREEN_WIDTH / 21,
                    (Settings.SCREEN_HEIGHT / 64) * (39 - (2*i)),
                    Settings.TILE_WIDTH/2, Settings.TILE_HEIGHT/2);

        }
    }

    /**
     * Renders the ready button on the screen
     * @param batch
     */
    private void renderReadyButton(SpriteBatch batch) {
        batch.draw(buttonReady, buttonReadyLeftX, buttonReadyLeftY   , 64, 32);
        if (myPlayer.getReadyButton()){
            batch.draw(buttonReadySelected, buttonReadyLeftX, buttonReadyLeftY   , 64, 32);
        }
    }

    /**
     * Renders the health of myPlayer.getRobot(), and the life of myPlayer
     * @param batch
     */
    private void renderHealthAndLife(SpriteBatch batch) {
        for (int i = 0 ; i < myPlayer.getRobot().getHealth(); i++){
            batch.draw(damageTokens[1], i*34, Settings.SCREEN_HEIGHT-32, 32, 32);
        }
        for (int i = myPlayer.getRobot().getHealth() ; i < 9; i++){
            batch.draw(damageTokens[2], i*34, Settings.SCREEN_HEIGHT-32, 32, 32);
        }
        for (int i = 0; i < myPlayer.getRobot().getLife(); i++) {
            batch.draw(lifeTokens[1],i*34, Settings.SCREEN_HEIGHT-64, 32, 32 );
        }
    }

    /**
     * Renders the robots on the board
     * @param batch
     */
    public void renderRobots(SpriteBatch batch){
        try {
            for (Robot r : board.getRobots()) {
                if (r.getTileX() != -1 && r.getTileY() != -1) {
                    batch.draw(r.getTexture(), (Settings.BOARD_LOC_X) + (r.getTileX() * Settings.TILE_WIDTH),
                            (Settings.BOARD_LOC_Y) + (r.getTileY() * Settings.TILE_HEIGHT),
                            Settings.TILE_WIDTH, Settings.TILE_HEIGHT);
                }
            }
        }catch (ConcurrentModificationException ex){
            //TODO fjern gaffateip og lag en skikkelig løsning
            //får en concurrentmodificationException i enkelte tilfeller, men ved å catche den er alt som skjer
            //at render skipper en iterasjon som ikke er merkbart for bruker.

        }
    }

    /**
     * Renders the cards in myPlayers hand
     * @param batch
     * @param font
     */
    public void renderCards(SpriteBatch batch, BitmapFont font){
        for (int i = 0; i < myPlayer.getCards().length; i++){
            if(myPlayer.getCards()[i] != null) {
                batch.draw(myPlayer.getCards()[i].getImage(), cardBoxLeft + (i * Settings.CARD_WIDTH), 0, Settings.CARD_WIDTH, Settings.CARD_HEIGHT);
                if (myPlayer.getCards()[i].getSelected()) {
                    font.draw(batch, myPlayer.getSelectedCards().indexOf(myPlayer.getCards()[i], true) + 1 + "", cardBoxLeft + (i * Settings.CARD_WIDTH) + (Settings.CARD_WIDTH / 5), Settings.CARD_HEIGHT - (Settings.CARD_HEIGHT / 10));
                    batch.draw(selectedFrame, cardBoxLeft + (i * Settings.CARD_WIDTH), 0, Settings.CARD_WIDTH, Settings.CARD_HEIGHT);
                }
            }
        }
        font.draw(batch, "Locked cards: ", Settings.SCREEN_WIDTH/12 * 10, Settings.SCREEN_HEIGHT/10 * 7);
        int j = 5;
        for (int i = 0; i < myPlayer.getLockedCards().size(); i++) {
            font.draw(batch, j-- + " ", Settings.SCREEN_WIDTH/12 * 10, Settings.SCREEN_HEIGHT/10 * (6-i));
            batch.draw(myPlayer.getLockedCards().get(i).getImage(), Settings.SCREEN_WIDTH/12 * 11, Settings.SCREEN_HEIGHT/10 * (6-i),
                    Settings.CARD_WIDTH/4, Settings.CARD_HEIGHT/4);
        }
    }

    /**
     * Renders the lasers that the robots just shot for 1 second
     * @param batch
     */
    public void renderRobotLasers(SpriteBatch batch){
        if(laserTime == 0){
            laserTime = System.currentTimeMillis();
        }
        if(laserTime + 1000 < System.currentTimeMillis()){
            laserTime = 0;
            renderRobotLasers = false;
            turnHandler.clearAllArraysOfCoordinates();
            return;
        }
        ArrayList<ArrayList<int[]>> arrayListArrayList = turnHandler.getAllArraysOfCoordinates();
        for (ArrayList<int[]> listOfCoordinates: arrayListArrayList) {
            for (int[] coordinates: listOfCoordinates) {
                batch.draw(robotLaser[coordinates[2]], (Settings.BOARD_LOC_X) + (coordinates[0] * Settings.TILE_WIDTH),
                        (Settings.BOARD_LOC_Y) + (coordinates[1] * Settings.TILE_HEIGHT),
                        Settings.TILE_WIDTH, Settings.TILE_HEIGHT);
            }
        }
    }

    public void dispose() {
        if(turnHandler != null)turnHandler.dispose();
    }

    /**
     * The isReady method adds a new set of cards to allCards, and if all players have sent their cards it calls
     * {@link TurnHandler#isReady()}, which then releases{@link TurnHandler#doTurn()}. and the Complete Registers phase starts.
     * @param packet
     */
    public void isReady(Packets.Packet02Cards packet){
        for (Packets.Packet02Cards pack: allCards) {
            if(pack.playerId == packet.playerId) return;
        }
        allCards.add(packet);

        if(allCards.size() == nrOfPlayers){
            turnHandler.isReady();
        }
    }

    /**
     * This method initializes a new {@link TurnHandler}.
     */
    public void gamePhasesSetUp() {
        turnHandler = new TurnHandler();
        turnHandler.create(this);
    }

    /**
     * This method calls {@link BoardParser#parse(String boardName)} to make a new board matching the string name, it the
     * calls setBoard to set the Game.board = BoardParser.parse(String boardName).
     * @param boardName This parameter is the name of the board that will be played.
     */
    public void boardSetUp(String boardName) {
        setBoard(BoardParser.parse(boardName));
    }

    public void setBoard(Board board){
        this.board = board;
    }
    public Board getBoard(){
        return board;
    }

    /**
     * This HashMap is used to connect a player with a client on the server.
     * @return This returns a HashMap with playerId ( the Kryonet clientId) as a key and a player as value.
     */
    public HashMap<Integer, Player> getIdPlayerHash() {
        return idPlayerHash;
    }

    /**
     * This ArrayList is used to store the newest set of cards from the server.
     * @return This returns an ArrayList of {@link Packets.Packet02Cards} which contains cards and player id.
     */
    public ArrayList<Packets.Packet02Cards> getAllCards() {
        return allCards;
    }

    public TurnHandler getTurnHandler() {
        return turnHandler;
    }

    /**
     * This method initializes {@link Game#idPlayerHash}, names and allCards. Then it creates four new players and puts them into
     * idPlayerHash.
     */
    public void playerSetup() {
        idPlayerHash = new HashMap<>();
        names = new String[4];
        allCards = new ArrayList<>();
        Texture[][] textures = getRobotTextures();
        playersShutdown = new boolean[5];
        allReady = new boolean[]{false, true, false, false, false};
        for (int i = 1; i < 5; i++) {
            Player player = new Player(textures[i-1]);
            player.deal();
            board.addObject(player.getRobot(), i+1, 0);
            idPlayerHash.put(i, player);
            playersShutdown[i] = false;
        }
        setMyPlayer(idPlayerHash.get(client.getId()));
        myPlayer.deal();
    }

    /**
     *
     * @return returns all the robot textures
     */
    private Texture[][] getRobotTextures() {
        Texture[][] textures = new Texture[4][4];
        textures[0] = new Texture[]{new Texture("assets/robot_design/elias_robot_forward.png"),
                new Texture("assets/robot_design/elias_robot.png"),
                new Texture("assets/robot_design/elias_robot_backwards.png"),
                new Texture("assets/robot_design/elias_robot_left.png")};
        textures[1] = new Texture[] {new Texture("assets/robot_design/Wall-e_Robot_forward.png"),
            new Texture("assets/robot_design/Wall-e_Robot_right.png"),
            new Texture("assets/robot_design/Wall-e_Robot_backwards.png"),
            new Texture("assets/robot_design/Wall-e_Robot_left.png")};
        textures[2][0] = new Texture("assets/robot_design/Darek_forwards.png");
        textures[2][1] = new Texture("assets/robot_design/Darek_right.png");
        textures[2][2] = new Texture("assets/robot_design/Darek_backwards.png");
        textures[2][3] = new Texture("assets/robot_design/Darek_left.png");
        textures[3] = new Texture[] {new Texture("assets/robot_design/elias_robot_forward.png"),
                new Texture("assets/robot_design/elias_robot.png"),
                new Texture("assets/robot_design/elias_robot_backwards.png"),
                new Texture("assets/robot_design/elias_robot_left.png")};
        return textures;
    }

    /**
     * This method sets the settings for the card boxes and button.
     */
    private void cardBoxSetUp() {
        cardBoxLeft = (Settings.CARD_WIDTH/2) * (10-5);
        cardBoxRight = (Settings.CARD_WIDTH/2) * (10+5);
        buttonReadyLeftX = Settings.SCREEN_WIDTH-(Settings.SCREEN_WIDTH/4);
        buttonReadyLeftY = Settings.SCREEN_HEIGHT/3;
    }

    /**
     * This method initializes the textures needed in the {@link Game} class.
     */
    private void textureSetUp() {
        tempMap = new Texture("assets/maps/riskyexchange.png");
        selectedFrame = new Texture("assets/cards/card_selected.png");
        buttonReady = new Texture("assets/button_ready.png");
        buttonReadySelected = new Texture("assets/button_ready_selected.png");
        damageTokens = new Texture[3];
        damageTokens[0] = new Texture("assets/damage-dead.png");
        damageTokens[1] = new Texture("assets/Damage-not-taken.png");
        damageTokens[2] = new Texture("assets/damage-taken.png");
        lifeTokens = new Texture[2];
        lifeTokens[0] = new Texture("assets/life-token-lost.png");
        lifeTokens[1] = new Texture("assets/life-tokens-alive.png");
        robotLaser = new Texture[] {new Texture("assets/temp_laser.png"),
                                    new Texture("assets/temp_laserNS.png")};
        laserTime = 0;
    }

    /**
     * The hostGame method starts a new {@link MPServer} and a {@link MPClient}. This should only be called by the one hosting the game.
     * @return Returns an InetAddress that is the IP Address that other players need to connect to the server.
     */
    public InetAddress hostGame(String boardName){
        server = new MPServer(boardName);
        server.run();
        client = new MPClient(server.getAddress(),this);
        host = true;
        return server.getAddress();
    }

    /**
     * The joinGame method initializes a new  {@link MPClient} and tries to connect to the server on the IP Address given.
     * @param ipAddress The IP Address for the server as a String.
     * @return
     */
    public boolean joinGame(String ipAddress){
        client = new MPClient(this);
        if(!client.connect(ipAddress)) return false;
        host = false;
        return true;
    }

    /**
     * The joinGame method initializes a new  {@link MPClient} and tries to connect to the server on the IP Address given.
     * @param ipAddress The IP Address for the server as a InetAddress.
     */
    public void joinGame(InetAddress ipAddress){
        client = new MPClient(ipAddress, this);
        host = false;
    }

    public int getId(){
        return client.getId();
    }
    public void setMyPlayer(Player player){
        myPlayer = player;
    }

    /**
     * This method updates how many player there are connected to the game.<BR> It gets called on by the client each time
     * a new  {@link MPClient} connects to the {@link MPServer}, and the server then tells all the clients.
     * @param i Number of people in the game.
     */
    public void setNrOfPlayers(int i){
        nrOfPlayers = i;
        System.out.println(nrOfPlayers);
    }

    /**
     *
     * @return Returns number of people in the game.
     */
    public int getNrOfPlayers() {
        return nrOfPlayers;
    }

    /**
     * The game initializes with 4 players, this gets called the first time the Program Cards phase starts, and deletes
     * the players that doesnt have a client connected to them.
     */
    public void deleteUnconnectedPlayers(){
        while(nrOfPlayers < idPlayerHash.size()){
            int j = idPlayerHash.size();
            board.removeObject(idPlayerHash.get(j).getRobot());
            idPlayerHash.get(j).getRobot().setTileX(-1);
            idPlayerHash.get(j).getRobot().setTileY(-1);
            idPlayerHash.remove(j);
        }
    }

    /**
     * Sends a startSignal from the host to all the players that them "playing" of the game starts now.
     */
    public void sendStartSignal(){
        client.sendStartSignal();
    }

    /**
     * Gets called by the client when the {@link MPServer} gets the start signal, then starts then "playing" part of the game.
     */
    public void receiveStart() {
        gamePhasesSetUp();
        deleteUnconnectedPlayers();
        ScreenHandler.changeScreenState(ScreenState.GAME);
    }

    /**
     * Sends the players username to the server
     * @param userName players username
     */
    public void sendName(String userName) {
        client.sendName(userName);
    }

    /**
     * Receives the names of all the players form the server, updates each time a player sends its name to the server.
     * @param name The names of the current players.
     */
    public void receiveNames(Packets.Packet05Name name) {
        names = name.name;
    }

    /**
     *
     * @return Returns all the user names of players currently in the game.
     */
    public String[] getNames(){
        return names;
    }

    /**
     * Deletes the cards of the last round played.
     */
    public void clearAllCards() {
        allCards.clear();
    }

    /**
     *
     * @return Returns true if the {@link MPClient} is connected to a server.
     */
    public boolean getConnection(){
        return client.getConnection();
    }

    public Player getMyPlayer() {
        return myPlayer;
    }

    /**
     * Sends a boolean to the server telling everybody that this player is ready to play
     */
    public void sendReadySignal() {
        Packets.Packet06ReadySignal signal = new Packets.Packet06ReadySignal();
        signal.signal = true;
        client.sendReady(signal);
    }

    /**
     * Takes a list of booleans representing the players who are ready
     * @param allReady
     */
    public void receiveAllReady(boolean[] allReady){
        this.allReady = allReady;
    }

    /**
     *
     * @return Returns a list of booleans that holds which players that are ready
     */
    public boolean[] getAllReady() {
        return allReady;
    }

    /**
     *
     * @return Returns a list of booleans describing which players have shut down their robot
     */
    public boolean[] getPlayersShutdown() {
        return playersShutdown;
    }

    /**
     * Sets the playersShutdown list to a new list
     * @param tempPlayersShutdown
     */
    public void setPlayersShutdown(boolean[] tempPlayersShutdown) {
        playersShutdown = tempPlayersShutdown;
    }

    /**
     * Call this when myPlayers robot needs to shut down
     */
    public void shutdownRobot(){
        client.sendShutdownRobot();
    }

    public void setRenderRobotLasers(boolean bool) {
        renderRobotLasers = bool;
    }

    public void removeOnePlayerFromServer() {
        client.removeOnePlayerFromServer();
    }

    public void setBoardName(String boardName) {
        this.boardName = boardName;
    }

    public String getBoardName(){
        return boardName;
    }
}
