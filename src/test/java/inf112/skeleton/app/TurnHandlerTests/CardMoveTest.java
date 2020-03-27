package inf112.skeleton.app.TurnHandlerTests;

import com.badlogic.gdx.ApplicationListener;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.backends.headless.HeadlessApplication;
import com.badlogic.gdx.backends.headless.HeadlessApplicationConfiguration;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import inf112.skeleton.app.EmptyApplicationListener;
import inf112.skeleton.app.board.Board;
import inf112.skeleton.app.board.Direction;
import inf112.skeleton.app.main.Game;
import inf112.skeleton.app.main.TurnHandler;
import inf112.skeleton.app.objects.boardObjects.RepairSite;
import inf112.skeleton.app.objects.cards.NonTextureProgramCard;
import inf112.skeleton.app.objects.cards.ProgramCard;
import inf112.skeleton.app.objects.player.Player;
import inf112.skeleton.app.objects.player.Robot;
import org.junit.BeforeClass;
import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.mock;

public class CardMoveTest {
    private static Game game;
    private static Player player;
    private static Robot robot;
    private static Board board;
    private static TurnHandler turnHandler;


    @BeforeClass
    public static void setUp(){
        HeadlessApplicationConfiguration conf = new HeadlessApplicationConfiguration();
        new HeadlessApplication(new EmptyApplicationListener(), conf);
        Gdx.gl = mock(GL20.class);
        game = new Game();
        game.setBoard(new Board(4,1,1));
        game.gamePhasesSetUp();
        player = new Player();
        turnHandler = game.getTurnHandler();
        board = game.getBoard();
        board.addObject(player.getRobot(), 1, 0);
        robot = player.getRobot();
        robot.setDirection(Direction.EAST);
    }

    @Test
    public void cardMoveTestMoveOne(){
        board.addObject(robot, 0, 0);
        assertEquals(0, robot.getTileX());
        NonTextureProgramCard card = new NonTextureProgramCard(1,false,false,false);
        turnHandler.cardMove(card, robot);
        assertEquals(1, robot.getTileX());

        board.removeObject(robot);
    }
    @Test
    public void cardMoveTestMoveTwo(){
        board.addObject(robot, 0, 0);
        assertEquals(0, robot.getTileX());
        NonTextureProgramCard card = new NonTextureProgramCard(2,false,false,false);
        turnHandler.cardMove(card, robot);
        assertEquals(2, robot.getTileX());

        board.removeObject(robot);
    }
    @Test
    public void cardMoveTestMoveThree(){
        board.addObject(robot, 0, 0);
        assertEquals(0, robot.getTileX());
        NonTextureProgramCard card = new NonTextureProgramCard(3,false,false,false);
        turnHandler.cardMove(card, robot);
        assertEquals(3, robot.getTileX());

        board.removeObject(robot);
    }
    @Test
    public void cardMoveTestRotateLeft(){
        board.addObject(robot, 0, 0);
        assertEquals(0, robot.getTileX());
        NonTextureProgramCard card = new NonTextureProgramCard(0,true,true,false);
        turnHandler.cardMove(card, robot);
        assertEquals(0, robot.getTileX());
        assertEquals(Direction.NORTH, robot.getDirection());
        robot.setDirection(Direction.EAST);
        board.removeObject(robot);
    }
    @Test
    public void cardMoveTestRotateRight(){
        board.addObject(robot, 0, 0);
        assertEquals(0, robot.getTileX());
        NonTextureProgramCard card = new NonTextureProgramCard(0,true,false,true);
        turnHandler.cardMove(card, robot);
        assertEquals(0, robot.getTileX());
        assertEquals(Direction.SOUTH, robot.getDirection());
        robot.setDirection(Direction.EAST);

        board.removeObject(robot);
    }
    @Test
    public void cardMoveTestUTurn(){
        board.addObject(robot, 0, 0);
        assertEquals(0, robot.getTileX());
        NonTextureProgramCard card = new NonTextureProgramCard(0,true,false,false);
        turnHandler.cardMove(card, robot);
        assertEquals(0, robot.getTileX());
        assertEquals(Direction.WEST, robot.getDirection());
        robot.setDirection(Direction.EAST);

        board.removeObject(robot);
    }
}
