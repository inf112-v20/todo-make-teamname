package inf112.skeleton.app;

import com.badlogic.gdx.ApplicationListener;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.InputAdapter;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import inf112.skeleton.app.objects.*;
import inf112.skeleton.app.objects.Robot;
import com.badlogic.gdx.utils.Queue;

import java.awt.*;
import java.util.ArrayList;
import java.util.PriorityQueue;
import java.util.Set;
import java.util.concurrent.Semaphore;


public class Game extends InputAdapter implements ApplicationListener {
    private SpriteBatch batch;
    private BitmapFont font;
    private Board board;
    private int nrOfPlayers = 1;
    private Player[] players;
    private Player myPlayer;
    private Thread phase;
    private Semaphore isReadySem;
    private boolean gameIsDone;
    private int cardBoxLeft;
    private int cardBoxRight;
    private int buttonReadyLeftX;
    private int buttonReadyLeftY;

    private Texture background;
    private Texture tempMap;
    private Texture selectedFrame;
    private Texture buttonReady;

    @Override
    public void create() {
        batch = new SpriteBatch();
        font = new BitmapFont();
        font.setColor(Color.BLACK);
        board = BoardParser.parse("riskyexchange");
        Gdx.input.setInputProcessor(this);
        isReadySem = new Semaphore(0);
        gameIsDone = false;
        myPlayer = new Player();
        myPlayer.deal();
        phase = new Thread(this::doTurn);
        phase.start();
        board.addObject(myPlayer.getRobot(), myPlayer.getRobot().getTileX(), myPlayer.getRobot().getTileY());

        background = new Texture("assets/pink_background.png");
        tempMap = new Texture("assets/maps/riskyexchange.png");
        selectedFrame = new Texture("assets/cards/card_selected.png");
        buttonReady = new Texture("assets/button_ready.png");
        cardBoxLeft = (Settings.CARD_WIDTH/2) * (10-myPlayer.getCards().length);
        cardBoxRight = (Settings.CARD_WIDTH/2) * (10+myPlayer.getCards().length);
        buttonReadyLeftX = Settings.SCREEN_WIDTH-(Settings.SCREEN_WIDTH/4);
        buttonReadyLeftY = Settings.SCREEN_HEIGHT/3;
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
            isReady();
        }



        return false;


    }

    @Override
    public boolean touchDragged(int screenX, int screenY, int pointer){
        if ((screenX < board.getWidth()*32 || screenY < board.getHeight()*32)){
            int x = screenX/32;
            int y = Math.abs(15-(screenY/32));
            board.moveSelected(x, y);
            return true;
        }
        else return false;
    }

    @Override
    public void pause() {

    }

    @Override
    public void resize(int i, int i1) {

    }

    //IMPORTANT
    //An object has to be initialized before being rendered
    @Override
    public void render() {
        batch.begin();

        batch.draw(background, 0, 0);
        batch.draw(tempMap, Settings.BOARD_LOC_X , Settings.BOARD_LOC_Y);
        for (Robot r : board.getRobots()){
            if(r.getTileX() != -1 && r.getTileY() != -1){
                batch.draw(r.getTexture(), (Settings.BOARD_LOC_X)+(r.getTileX()*Settings.TILE_WIDTH),(Settings.BOARD_LOC_Y)+(r.getTileY()*Settings.TILE_HEIGHT));
            }
        }
        BitmapFont font = new BitmapFont();

        for (int i = 0; i < myPlayer.getCards().length; i++){
            batch.draw(myPlayer.getCards()[i].getImage(), cardBoxLeft+ (i*Settings.CARD_WIDTH), 0, Settings.CARD_WIDTH, Settings.CARD_HEIGHT);
            if (myPlayer.getCards()[i].getSelected()){
                font.draw(batch, myPlayer.getSelectedCards().indexOf(myPlayer.getCards()[i], true) + 1 + "", cardBoxLeft + (i*Settings.CARD_WIDTH) + (Settings.CARD_WIDTH/5), Settings.CARD_HEIGHT- (Settings.CARD_HEIGHT/10));
                batch.draw(selectedFrame, cardBoxLeft+ (i*Settings.CARD_WIDTH), 0, Settings.CARD_WIDTH, Settings.CARD_HEIGHT);
            }
        }
        batch.draw(buttonReady, buttonReadyLeftX, buttonReadyLeftY   , 64, 32);
        batch.end();
    }

    @Override
    public void resume() {

    }

    /**
     * Is called when application is destroyed
     */
    //TODO Randomly crashes when exiting application
    @Override
    public void dispose() {
        batch.dispose();
        font.dispose();
        phase.interrupt();
    }

    //Call this when cards have been selected to be played
    private void isReady(){

        isReadySem.release();
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
            Queue<ProgramCard> cards = myPlayer.copySelected();
            Robot robot = myPlayer.getRobot();
            for (ProgramCard card : cards) {
                if(card == null || robot.isDestroyed()) continue;
                //TODO: later do each step for all players too
                card.flip(); // flips the texture from back to front
                if (card.getValue() > 0) {
                    for (int i = 0; i < card.getValue(); i++) {
                        if(robot.isDestroyed()) break;
                        //Move robot
                        board.moveObject(robot, robot.getDirection());
                        if (board.getTile(robot.getTileX(), robot.getTileY()).getObjects()[0] instanceof Pit) {
                            board.removeObject(robot);
                            robot.destroy();
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
                BoardTile currentTile = board.getTile(robot.getTileX(), robot.getTileY());
                if(!robot.isDestroyed()){
                    //Board elements do their things
                    currentTile = board.getTile(robot.getTileX(), robot.getTileY());
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
                    currentTile = board.getTile(robot.getTileX(), robot.getTileY());
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
                    currentTile = board.getTile(robot.getTileX(), robot.getTileY());
                    if (currentTile.getObjects()[0] instanceof Pusher) {
                        //Robot gets pushed
                        Pusher pusher = (Pusher) currentTile.getObjects()[0];
                        board.moveObject(robot, pusher.getDirection());
                    }
                    currentTile = board.getTile(robot.getTileX(), robot.getTileY());

                    if (currentTile.getObjects()[0] instanceof GearClockwise) {
                        //Rotate right
                        robot.rotateRight();
                    }

                    if (currentTile.getObjects()[0] instanceof GearCounterClockwise) {
                        //Rotate left
                        robot.rotateLeft();
                    }

                    currentTile = board.getTile(robot.getTileX(), robot.getTileY());
                    if (currentTile.getObjects()[1] instanceof BoardLaser) {
                        //TODO Board does damage to robot
                        robot.takeDamage();
                    }
                    //TODO Robots hit each other

                    currentTile = board.getTile(robot.getTileX(), robot.getTileY());
                    if (currentTile.getObjects()[0] instanceof Flag) {
                        //TODO pick up flag, player or robot?
                    }
                    currentTile = board.getTile(robot.getTileX(), robot.getTileY());
                    if (currentTile.getObjects()[0] instanceof RepairSite) {
                        //Heals a damage token if robot
                        robot.healDamage();
                        robot.setRespawn(robot.getTileX(), robot.getTileY());
                        RepairSite repairSite = (RepairSite) currentTile.getObjects()[0];
                        if (repairSite.getHammer()) {
                            //Player gets an option card
                            //TODO implement optioCards
                            myPlayer.giveOptionCard();
                        }
                    }
                }
                if (board.getTile(robot.getTileX(), robot.getTileY()).getObjects()[0] instanceof Pit) {
                    //Robot falls into pit
                    board.removeObject(robot);
                    robot.destroy();
                }
                try {
                    Thread.sleep(400);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
            //TODO clean up phase, remove register etc...
            if(robot.isDestroyed()){
                if(myPlayer.getLife() > 0) {
                    //Respawn robot if player has more life left
                    board.addObject(robot, robot.getRespawnX(), robot.getRespawnY());
                    robot.respawn();
                    System.out.println("respawn");
                }else{
                    //Not the case if there are more players
                    gameIsDone = true;
                }
            }

        }
    }

}
