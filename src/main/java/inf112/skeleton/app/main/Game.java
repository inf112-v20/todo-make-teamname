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
import inf112.skeleton.app.main.menuScreens.TurnHandler;
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
    private Semaphore isReadySem;
    private boolean gameIsDone;
    private int cardBoxLeft;
    private int cardBoxRight;
    private int buttonReadyLeftX;
    private int buttonReadyLeftY;
    private ArrayList<Packets.Packet02Cards> allCards;
    private MPServer server;
    private TurnHandler turnHandler;

    private Texture tempMap;
    private Texture selectedFrame;
    private Texture buttonReady;
    private Texture[] damageTokens;
    private Texture[] lifeTokens;



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
        turnHandler.dispose();
    }

    //Call this when cards have been selected to be played
    public void isReady(Packets.Packet02Cards p){
        allCards.add(p);

        if(allCards.size() == nrOfPlayers){
            isReadySem.release();
            turnHandler.isReady();
        }
    }

    public void gamePhasesSetUp() {
        turnHandler = new TurnHandler();
        turnHandler.create(this);
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

    public HashMap<Integer, Player> getIdPlayerHash() {
        return idPlayerHash;
    }

    public ArrayList<Packets.Packet02Cards> getAllCards() {
        return allCards;
    }

    public TurnHandler getTurnHandler() {
        return turnHandler;
    }

    public void playerSetup() {
        isReadySem = new Semaphore(0);
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

    public void clearAllCards() {
        allCards.clear();
    }
}
