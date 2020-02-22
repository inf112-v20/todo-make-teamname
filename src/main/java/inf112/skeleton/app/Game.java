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

import java.util.HashMap;


public class Game extends InputAdapter implements ApplicationListener {
    private SpriteBatch batch;
    private BitmapFont font;
    private Board board;
    private int nrOfPlayers = 1;
    private Player[] players;
    private Player myPlayer;
    private Thread phase;

    @Override
    public void create() {
        batch = new SpriteBatch();
        font = new BitmapFont();
        font.setColor(Color.BLACK);
        board = BoardParser.parse("riskyexchange");
        Gdx.input.setInputProcessor(this);
        myPlayer = new Player();
        myPlayer.deal();
        board.addObject(myPlayer.getRobot(), 6, 8);
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
        for (IBoardObject o: board.getTile(screenX/32, Math.abs(15-(screenY/32))).getObjects()){
            if (o instanceof Robot){
                board.setSelected((Robot) o);
                return true;
            }
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
        BoardTile[][] grid = board.getGrid();
        for (int y=0; y < board.getHeight(); y++) {
            for (int x=0; x < board.getWidth(); x++) {
                for (Texture t : grid[y][x].getTextures()){
                    if (t != null)batch.draw(t, x*32, y*32);
                }
            }
        }
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
        try {
            phase.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }


    private void isReady(){
        phase = new Thread(() -> {
           ProgramCard[] cards = myPlayer.getCards();
           Robot robot = myPlayer.getRobot();
            for (ProgramCard card: cards) {
                //TODO: later do each step for all players too
                card.flip(); // flips the texture from back to front
                if(card.getValue() > 0){
                    for (int i = 0; i < card.getValue(); i++) {
                        board.moveObject(robot,robot.getDirection());
                    }
                }
                if(card.getRotate()){
                    if(card.getRotateLeft()){
                        robot.rotateLeft();
                    }
                    else if(card.getRotateRight()){
                        robot.rotateRight();
                    }
                    else {
                        robot.rotateRight();
                        robot.rotateRight();
                    }
                }
                BoardTile currentTile = board.getTile(robot.getTileX(), robot.getTileY());
                if(currentTile.getObjects()[0] instanceof Pit){
                    //TODO Destroy robot;

                }else {
                    currentTile = board.getTile(robot.getTileX(), robot.getTileY());
                    if(currentTile.getObjects()[0] instanceof ConveyorBelt){
                        ConveyorBelt conveyorBelt = (ConveyorBelt) currentTile.getObjects()[0];
                        if(conveyorBelt.getExpress()){
                            board.moveObject(robot, conveyorBelt.getDirection());
                        }
                    }
                    currentTile = board.getTile(robot.getTileX(), robot.getTileY());
                    if(currentTile.getObjects()[0] instanceof ConveyorBelt){
                        ConveyorBelt conveyorBelt = (ConveyorBelt) currentTile.getObjects()[0];
                        board.moveObject(robot, conveyorBelt.getDirection());
                    }
                    currentTile = board.getTile(robot.getTileX(), robot.getTileY());
                    if(currentTile.getObjects()[0] instanceof Pusher){
                        Pusher pusher = (Pusher) currentTile.getObjects()[0];
                        board.moveObject(robot, pusher.getDirection());
                    }
                    currentTile = board.getTile(robot.getTileX(), robot.getTileY());

                    if(currentTile.getObjects()[0] instanceof GearClockwise){robot.rotateRight();}

                    if(currentTile.getObjects()[0] instanceof GearCounterClockwise){robot.rotateLeft();}

                    currentTile = board.getTile(robot.getTileX(), robot.getTileY());
                    if(currentTile.getObjects()[1] instanceof BoardLaser){
                        //TODO Board does damage to robot
                    }
                    //TODO Robots hit each other

                    currentTile = board.getTile(robot.getTileX(), robot.getTileY());
                    if(currentTile.getObjects()[0] instanceof Flag){
                        //TODO pick up flag, player or robot?
                    }
                    currentTile = board.getTile(robot.getTileX(), robot.getTileY());
                    if(currentTile.getObjects()[0] instanceof RepairSite){
                        robot.healDamage();
                        robot.setRespawn(robot.getTileX(), robot.getTileY());
                        RepairSite repairSite = (RepairSite) currentTile.getObjects()[0];
                        if(repairSite.getHammer()){
                            myPlayer.giveOptionCard();
                        }
                    }
                }
            }
            //TODO clean up phase, respawning, remove register etc...
        });
        phase.start();
        try {
            phase.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        myPlayer.deal();
    }

}
