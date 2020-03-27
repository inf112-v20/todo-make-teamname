package inf112.skeleton.app.main;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.InputAdapter;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
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
import java.util.concurrent.Semaphore;


/**
 * The Game class is the centerpiece that ties all the different parts of the game together.<BR> It sends information to the
 * MPClient which sends it to the server.<BR> It creates all the player and board objects, and then keeps control of
 * it.
 */
public class Game extends InputAdapter {
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

    private Texture tempMap;
    private Texture selectedFrame;
    private Texture buttonReady;
    private Texture buttonReadySelected;
    private Texture[] damageTokens;
    private Texture[] lifeTokens;



    /**
     * This method calls all the methods needed to start the "playing" part of the game.
     */
    public void create() {
        boardSetUp("riskyexchange");
        Gdx.input.setInputProcessor(this);
        playerSetup();
        textureSetUp();
        cardBoxSetUp();
    }



    @Override
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

    @Override
    public boolean touchDown(int screenX, int screenY, int pointer, int button){
        //checks if the click occurs in the "cardbox"
        if (screenX > cardBoxLeft &&
                screenX < cardBoxRight &&
                screenY > Settings.SCREEN_HEIGHT-Settings.CARD_HEIGHT &&
                screenY < Settings.SCREEN_HEIGHT){
            int card = (screenX - cardBoxLeft)/Settings.CARD_WIDTH;
            myPlayer.addSelectedCard(card);
        }
        //checks if the click occurs on the "ready-button"
        else if (screenX > Settings.SCREEN_WIDTH-(Settings.SCREEN_WIDTH/4) &&
                screenX < Settings.SCREEN_WIDTH-(Settings.SCREEN_WIDTH/4)+64 &&
                screenY > (Settings.SCREEN_HEIGHT-(Settings.SCREEN_HEIGHT/3))-32&&
                screenY < (Settings.SCREEN_HEIGHT-(Settings.SCREEN_HEIGHT/3)) && !myPlayer.getReadyButton() && myPlayer.getArrayCards().length == 5){
            //TODO fix so that one player cant send multiple sets of cards
            myPlayer.setReadyButon(true);
            if (myPlayer.getArrayCards().length == 5) client.sendCards(myPlayer.getArrayCards());
        }
        return false;
    }

    @Override
    public boolean touchDragged(int screenX, int screenY, int pointer){
        return false;
    }

    //IMPORTANT
    //An object has to be initialized before being rendered
    public void render(SpriteBatch batch, BitmapFont font) {
        batch.draw(tempMap, Settings.BOARD_LOC_X , Settings.BOARD_LOC_Y);
        try {
            for (Robot r : board.getRobots()) {
                if (r.getTileX() != -1 && r.getTileY() != -1) {
//                    batch.draw(r.getTexture(), (Settings.BOARD_LOC_X) + (r.getTileX() * Settings.TILE_WIDTH), (Settings.BOARD_LOC_Y) + (r.getTileY() * Settings.TILE_HEIGHT));
                    batch.draw(r.getTexture(), (Settings.BOARD_LOC_X) + (r.getTileX() * Settings.TILE_WIDTH), (Settings.BOARD_LOC_Y) + (r.getTileY() * Settings.TILE_HEIGHT), Settings.TILE_WIDTH, Settings.TILE_HEIGHT);
//                    TextureRegion t = new TextureRegion();
//                    t.setRegion(r.getTexture());
//                    batch.draw(r.getTexture(),
//                            (Settings.BOARD_LOC_X) + (r.getTileX() * Settings.TILE_WIDTH),
//                            (Settings.BOARD_LOC_Y) + (r.getTileY() * Settings.TILE_HEIGHT),
//                            Settings.TILE_WIDTH/2,
//                            Settings.TILE_HEIGHT/2,
//                            Settings.TILE_WIDTH,
//                            Settings.TILE_HEIGHT);
                }
            }
        }catch (ConcurrentModificationException ex){
            //TODO fjern gaffateip og lag en skikkelig løsning
            //får en concurrentmodificationException i enkelte tilfeller, men ved å catche den er alt som skjer
            //at render skipper en iterasjon som ikke er merkbart for bruker.

        }

        for (int i = 0; i < myPlayer.getCards().length; i++){
            batch.draw(myPlayer.getCards()[i].getImage(), cardBoxLeft+ (i*Settings.CARD_WIDTH), 0, Settings.CARD_WIDTH, Settings.CARD_HEIGHT);
            if (myPlayer.getCards()[i].getSelected()){
                font.draw(batch, myPlayer.getSelectedCards().indexOf(myPlayer.getCards()[i], true) + 1 + "", cardBoxLeft + (i*Settings.CARD_WIDTH) + (Settings.CARD_WIDTH/5), Settings.CARD_HEIGHT- (Settings.CARD_HEIGHT/10));
                batch.draw(selectedFrame, cardBoxLeft+ (i*Settings.CARD_WIDTH), 0, Settings.CARD_WIDTH, Settings.CARD_HEIGHT);
            }
        }
        for (int i = 0 ; i < myPlayer.getRobot().getHealth(); i++){
            batch.draw(damageTokens[1], i*34, Settings.SCREEN_HEIGHT-32, 32, 32);
        }
        for (int i = myPlayer.getRobot().getHealth() ; i < 9; i++){
            batch.draw(damageTokens[2], i*34, Settings.SCREEN_HEIGHT-32, 32, 32);
        }
        for (int i = 0; i < myPlayer.getRobot().getLife(); i++) {
            batch.draw(lifeTokens[1],i*34, Settings.SCREEN_HEIGHT-64, 32, 32 );
        }
        batch.draw(buttonReady, buttonReadyLeftX, buttonReadyLeftY   , 64, 32);
        if (myPlayer.getReadyButton()){
            batch.draw(buttonReadySelected, buttonReadyLeftX, buttonReadyLeftY   , 64, 32);
        }

        font.setColor(Color.BLACK);
        font.draw(batch, "Players in game:", Settings.SCREEN_WIDTH / 16, (Settings.SCREEN_HEIGHT / 18) * 11);
        for (int i = 0; i < names.length; i++) {
            if(names[i] == null) continue;
            font.draw(batch, names[i] + " Player number " + i, Settings.SCREEN_WIDTH / 16, (Settings.SCREEN_HEIGHT / 18) * (11 - i));
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
        for (int i = 1; i < 5; i++) {
            Player player = new Player(textures[i-1]);
            player.deal();
            board.addObject(player.getRobot(), i, 0);
            idPlayerHash.put(i, player);
        }
    }

    private Texture[][] getRobotTextures() {
        Texture[][] textures = new Texture[4][4];
        textures[0] = new Texture[]{new Texture("assets/robot_design/elias_robot_forward.png"),
                new Texture("assets/robot_design/elias_robot.png"),
                new Texture("assets/robot_design/elias_robot_backwards.png"),
                new Texture("assets/robot_design/elias_robot_left.png")};
        textures[1] = new Texture[] {new Texture("assets/robot_design/elias_robot_forward.png"),
            new Texture("assets/robot_design/elias_robot.png"),
            new Texture("assets/robot_design/elias_robot_backwards.png"),
            new Texture("assets/robot_design/elias_robot_left.png")};
        textures[2] = new Texture[]{new Texture("assets/robot_design/elias_robot_forward.png"),
                new Texture("assets/robot_design/elias_robot.png"),
                new Texture("assets/robot_design/elias_robot_backwards.png"),
                new Texture("assets/robot_design/elias_robot_left.png")};
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
    }

    /**
     * The hostGame method starts a new {@link MPServer} and a {@link MPClient}. This should only be called by the one hosting the game.
     * @return Returns an InetAddress that is the IP Address that other players need to connect to the server.
     */
    public InetAddress hostGame(){
        server = new MPServer();
        server.run();
        client = new MPClient(server.getAddress(),this);
        setMyPlayer(idPlayerHash.get(client.getId()));
        host = true;
        return server.getAddress();
    }

    /**
     * The joinGame method initializes a new  {@link MPClient} and tries to connect to the server on the IP Address given.
     * @param ipAddress The IP Address for the server as a String.
     */
    public void joinGame(String ipAddress){
        client = new MPClient(ipAddress, this);
        setMyPlayer(idPlayerHash.get(client.getId()));
        host = false;
    }

    /**
     * The joinGame method initializes a new  {@link MPClient} and tries to connect to the server on the IP Address given.
     * @param ipAddress The IP Address for the server as a InetAddress.
     */
    public void joinGame(InetAddress ipAddress){
        client = new MPClient(ipAddress, this);
        setMyPlayer(idPlayerHash.get(client.getId()));
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
}
