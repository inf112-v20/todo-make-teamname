package inf112.skeleton.app.main;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.InputAdapter;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import inf112.skeleton.app.board.Board;
import inf112.skeleton.app.board.BoardParser;
import inf112.skeleton.app.board.BoardTile;
import inf112.skeleton.app.board.Direction;
import inf112.skeleton.app.networking.MPClient;
import inf112.skeleton.app.networking.MPServer;
import inf112.skeleton.app.networking.Packets;
import inf112.skeleton.app.objects.cards.CardTranslator;
import inf112.skeleton.app.objects.cards.NonTextureProgramCard;
import inf112.skeleton.app.objects.player.Player;
import inf112.skeleton.app.objects.player.Robot;
import inf112.skeleton.app.objects.boardObjects.*;

import java.net.InetAddress;
import java.util.ArrayList;
import java.util.ConcurrentModificationException;
import java.util.HashMap;
import java.util.Scanner;
import java.util.concurrent.Semaphore;


public class Game extends InputAdapter {
    private Board board;
    private MPClient client;
    private Player myPlayer;
    private int nrOfPlayers;
    private HashMap<Integer, Player> idPlayerHash;
    private String[] names;
    private Thread phase;
    private Semaphore isReadySem;
    private boolean gameIsDone;
    private int cardBoxLeft;
    private int cardBoxRight;
    private int buttonReadyLeftX;
    private int buttonReadyLeftY;
    private boolean hosting = true;
    private ArrayList<Packets.Packet02Cards> allCards;
    private MPServer server;

    private Texture tempMap;
    private Texture selectedFrame;
    private Texture buttonReady;
    private Texture[] damageTokens;
    private Texture[] lifeTokens;



    public void create() {
        boardSetUp("riskyexchange");
        Gdx.input.setInputProcessor(this);
        playerSetup();
//        lanStartUp();
//       gamePhasesSetUp();
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
                screenY < (Settings.SCREEN_HEIGHT-(Settings.SCREEN_HEIGHT/3))){
            //TODO fix so that one player cant send multiple sets of cards
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
                    batch.draw(r.getTexture(), (Settings.BOARD_LOC_X) + (r.getTileX() * Settings.TILE_WIDTH), (Settings.BOARD_LOC_Y) + (r.getTileY() * Settings.TILE_HEIGHT));
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
    }

    public void dispose() {
        phase.interrupt();
    }

    //Call this when cards have been selected to be played
    public void isReady(Packets.Packet02Cards p){
        allCards.add(p);

        if(allCards.size() == nrOfPlayers){
            isReadySem.release();
        }
    }

    private void doTurn() {
        while (!gameIsDone) {
            //Does a full register for each iteration of the while loop
            try {
                //Waits until cards have been selected
                isReadySem.acquire();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            if(Thread.interrupted()) return;


            for (int i = 0; i < 5; i++) {
                HashMap<NonTextureProgramCard, Integer> cards = new HashMap<>();
                for (Packets.Packet02Cards packet: allCards) {
                    cards.put(CardTranslator.intToProgramCard(packet.programCards[i]), packet.playerId);
                }
                for (NonTextureProgramCard card: cards.keySet()) {
                    cardMove(card, idPlayerHash.get(cards.get(card)).getRobot()); // moves robot
                }
                for (int id: idPlayerHash.keySet()) {
                    Robot robot = idPlayerHash.get(id).getRobot();
                    expressConveyorMove(robot);
                }
                for (int id: idPlayerHash.keySet()) {
                    Robot robot = idPlayerHash.get(id).getRobot();
                    conveyorMove(robot); // conveyorbelt moves
                }
                for (int id: idPlayerHash.keySet()) {
                    Robot robot = idPlayerHash.get(id).getRobot();
                    pushersMove(robot);
                }
                for (int id: idPlayerHash.keySet()) {
                    Robot robot = idPlayerHash.get(id).getRobot();
                    gearsMove(robot);
                }
                for (int id: idPlayerHash.keySet()) {
                    Robot robot = idPlayerHash.get(id).getRobot();
                    boardLasersShoot(robot);
                }
                //TODO Robots hit each other
                for (int id: idPlayerHash.keySet()) {
                    Robot robot = idPlayerHash.get(id).getRobot();
                    pickUpFlag(robot);
                }
                for (int id: idPlayerHash.keySet()) {
                    Robot robot = idPlayerHash.get(id).getRobot();
                    repair(robot);
                }
                for (int id: idPlayerHash.keySet()) {
                    Robot robot = idPlayerHash.get(id).getRobot();
                    pitFall(robot);
                }
                try {
                    Thread.sleep(400);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
            //TODO remove register etc...
            for (int id: idPlayerHash.keySet()) {
                Robot robot = idPlayerHash.get(id).getRobot();
                cleanUp(robot);
            }

        }
    }

    public void pitFall(Robot robot) {
        if (!robot.isDestroyed()) {
            if (board.getTile(robot.getTileX(), robot.getTileY()).getObjects()[0] instanceof Pit) {
                //Robot falls into pit
                board.removeObject(robot);
                robot.destroy();
            }
        }
    }

    public void cleanUp(Robot robot) {
        if (robot.isDestroyed()) {
            if (myPlayer.getLife() > 0) {
                //Respawn robot if player has more life left
                board.addObject(robot, robot.getRespawnX(), robot.getRespawnY());
                robot.respawn();
                System.out.println("respawn");
            } else {
                //Not the case if there are more players
                gameIsDone = true;
            }
        }
        allCards.clear();
    }

    public void repair(Robot robot) {
        if(robot.isDestroyed())return;
        BoardTile currentTile = board.getTile(robot.getTileX(), robot.getTileY());
        if (currentTile.getObjects()[0] instanceof RepairSite) {
            //Heals a damage token if robot
            robot.healDamage();
            robot.setRespawn(robot.getTileX(), robot.getTileY());
            RepairSite repairSite = (RepairSite) currentTile.getObjects()[0];
            if (repairSite.getHammer()) {
                //Player gets an option card
                //TODO implement optionCards
                myPlayer.giveOptionCard();
            }
        }
    }

    public void pickUpFlag(Robot robot) {
        if(robot.isDestroyed())return;
        BoardTile currentTile = board.getTile(robot.getTileX(), robot.getTileY());
        if (currentTile.getObjects()[0] instanceof Flag) {
            Flag flag = (Flag) currentTile.getObjects()[0];
            if (!myPlayer.getFlags().contains(flag) && myPlayer.getFlags().size() + 1 == flag.getNr()) {
                myPlayer.addFlag(flag);
                if (myPlayer.getFlags().size() == board.getFlagNr()) {
                    //TODO endscreen
                    ScreenHandler.changeScreenState(ScreenState.MAINMENU);
                    gameIsDone = true;
                }
            }
        }
    }

    public void boardLasersShoot(Robot robot) {
        if(robot.isDestroyed())return;
        BoardTile currentTile = board.getTile(robot.getTileX(), robot.getTileY());
        if (currentTile.getObjects()[1] instanceof BoardLaser) {
            robot.takeDamage();
        }
    }

    public void gearsMove(Robot robot) {
        if(robot.isDestroyed())return;
        BoardTile currentTile = board.getTile(robot.getTileX(), robot.getTileY());
        if (currentTile.getObjects()[0] instanceof GearClockwise) {
            //Rotate right
            robot.rotateRight();
        }

        if (currentTile.getObjects()[0] instanceof GearCounterClockwise) {
            //Rotate left
            robot.rotateLeft();
        }
    }

    public void pushersMove(Robot robot) {
        if(robot.isDestroyed())return;
        BoardTile currentTile = board.getTile(robot.getTileX(), robot.getTileY());
        if (currentTile.getObjects()[0] instanceof Pusher) {
            //Robot gets pushed
            Pusher pusher = (Pusher) currentTile.getObjects()[0];
            board.moveObject(robot, pusher.getDirection());
        }
    }

    public void expressConveyorMove(Robot robot) {
        if (robot.isDestroyed()) return;
        BoardTile currentTile = board.getTile(robot.getTileX(), robot.getTileY());
        //Board elements do their things
        if (currentTile.getObjects()[0] instanceof ConveyorBelt) {
            ConveyorBelt conveyorBelt = (ConveyorBelt) currentTile.getObjects()[0];
            if (conveyorBelt.getExpress()) {
                //Expressconveoyrbelt moves robot
                board.moveObject(robot, conveyorBelt.getDirection());
                try {
                    Thread.sleep(100);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }

        }
    }
    public void conveyorMove(Robot robot) {

        if(robot.isDestroyed())return;
        BoardTile currentTile = board.getTile(robot.getTileX(), robot.getTileY());
        if (currentTile.getObjects()[0] instanceof ConveyorBelt) {
            //Conveoyrbelt moves robot
            ConveyorBelt conveyorBelt = (ConveyorBelt) currentTile.getObjects()[0];
            board.moveObject(robot, conveyorBelt.getDirection());
            try {
                Thread.sleep(100);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    public void cardMove(NonTextureProgramCard card, Robot robot){
        if (card.getValue() > 0) {
            for (int i = 0; i < card.getValue(); i++) {
                if(robot.isDestroyed()) break;
                //Move robot
                board.moveObject(robot, robot.getDirection());
                if (!robot.isDestroyed()){
                    if (board.getTile(robot.getTileX(), robot.getTileY()).getObjects()[0] instanceof Pit) {
                        board.removeObject(robot);
                        robot.destroy();
                    }
                }
                try {
                    Thread.sleep(300);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }
        else if (card.getRotate()) {
            if (card.getRotateLeft()) {
                robot.rotateLeft();
            } else if (card.getRotateRight()) {
                robot.rotateRight();
            } else {
                robot.rotateRight();
                robot.rotateRight();
            }
        }
    }

    public void gamePhasesSetUp() {
        phase = new Thread(this::doTurn);
        phase.start();
    }

    public void boardSetUp(String boardName) {
        setBoard(BoardParser.parse(boardName));
    }
    public void setBoard(Board board){
        this.board = board;
    }
    public Board getBoard(){
        return board;
    }

    public void playerSetup() {
        isReadySem = new Semaphore(0);
        gameIsDone = false;
        idPlayerHash = new HashMap<>();
        names = new String[4];
        allCards = new ArrayList<>();
        for (int i = 1; i < 5; i++) {
            Player player = new Player();
            player.deal();
            board.addObject(player.getRobot(), i, 0);
            idPlayerHash.put(i, player);
        }
    }

    private void cardBoxSetUp() {
        cardBoxLeft = (Settings.CARD_WIDTH/2) * (10-5);
        cardBoxRight = (Settings.CARD_WIDTH/2) * (10+5);
        buttonReadyLeftX = Settings.SCREEN_WIDTH-(Settings.SCREEN_WIDTH/4);
        buttonReadyLeftY = Settings.SCREEN_HEIGHT/3;
    }

    private void textureSetUp() {
        tempMap = new Texture("assets/maps/riskyexchange.png");
        selectedFrame = new Texture("assets/cards/card_selected.png");
        buttonReady = new Texture("assets/button_ready.png");
        damageTokens = new Texture[3];
        damageTokens[0] = new Texture("assets/damage-dead.png");
        damageTokens[1] = new Texture("assets/Damage-not-taken.png");
        damageTokens[2] = new Texture("assets/damage-taken.png");
        lifeTokens = new Texture[2];
        lifeTokens[0] = new Texture("assets/life-token-lost.png");
        lifeTokens[1] = new Texture("assets/life-tokens-alive.png");
    }

    public void lanStartUp() {
        Scanner scanner = new Scanner(System.in);
        if(scanner.nextBoolean()){
            hosting = false;
        }
        if(hosting){
            System.out.println("Hosting");
            hostGame();
            System.out.println("Server address: " + server.getAddress());
        }
        else {
            System.out.println("Joining");
            joinGame("10.0.0.59"); //Set to own ip
        }
    }
    public InetAddress hostGame(){
        server = new MPServer();
        server.run();
        client = new MPClient(server.getAddress(),this);
        setMyPlayer(idPlayerHash.get(client.getId()));
        return server.getAddress();
    }

    public void joinGame(String ipAddress){
        client = new MPClient(ipAddress, this);
        setMyPlayer(idPlayerHash.get(client.getId()));
    }

    public int getId(){
        return client.getId();
    }
    public void setMyPlayer(Player player){
        myPlayer = player;
    }

    public void setNrOfPlayers(int i){
        nrOfPlayers = i;
        System.out.println(nrOfPlayers);
    }
    public void deleteUnconnectedPlayers(){
        while(nrOfPlayers < idPlayerHash.size()){
            int j = idPlayerHash.size();
            board.removeObject(idPlayerHash.get(j).getRobot());
            idPlayerHash.get(j).getRobot().setTileX(-1);
            idPlayerHash.get(j).getRobot().setTileY(-1);
            idPlayerHash.remove(j);
        }
    }

    public void sendStartSignal(){
        client.sendStartSignal();
    }

    public void receiveStart() {
        gamePhasesSetUp();
        deleteUnconnectedPlayers();
        ScreenHandler.changeScreenState(ScreenState.GAME);
    }

    public void sendName(String text) {
        client.sendName(text);
    }

    public void receiveNames(Packets.Packet05Name name) {
        names = name.name;
    }
    public String[] getNames(){
        return names;
    }
}
