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
        Texture mockTexture = mock(Texture.class);
        Texture[] mockImages = {mockTexture};
        player = new Player(mockImages);
        turnHandler = game.getTurnHandler();
        board = game.getBoard();
        robot = player.getRobot();
        robot.setDirection(Direction.EAST);
        board.addObject(robot, 0, 0);
    }

    @Test
    public void cardMoveTestMoveOne(){
        assertEquals(0, robot.getTileX());
        NonTextureProgramCard card = new NonTextureProgramCard(1,false,false,false, 0);
        turnHandler.cardMove(card, robot);
        assertEquals(1, robot.getTileX());

        board.moveObject(robot, 0, 0);
    }
    @Test
    public void cardMoveTestMoveTwo(){
        assertEquals(0, robot.getTileX());
        NonTextureProgramCard card = new NonTextureProgramCard(2,false,false,false, 0);
        turnHandler.cardMove(card, robot);
        assertEquals(2, robot.getTileX());

        board.moveObject(robot, 0, 0);
    }
    @Test
    public void cardMoveTestMoveThree(){
        assertEquals(0, robot.getTileX());
        NonTextureProgramCard card = new NonTextureProgramCard(3,false,false,false, 0);
        turnHandler.cardMove(card, robot);
        assertEquals(3, robot.getTileX());

        board.moveObject(robot, 0, 0);
    }
    @Test
    public void cardMoveTestRotateLeft(){
        assertEquals(0, robot.getTileX());
        NonTextureProgramCard card = new NonTextureProgramCard(0,true,true,false, 0);
        turnHandler.cardMove(card, robot);
        assertEquals(0, robot.getTileX());
        assertEquals(Direction.NORTH, robot.getDirection());
        robot.setDirection(Direction.EAST);
        board.moveObject(robot, 0, 0);
    }
    @Test
    public void cardMoveTestRotateRight(){
        assertEquals(0, robot.getTileX());
        NonTextureProgramCard card = new NonTextureProgramCard(0,true,false,true, 0);
        turnHandler.cardMove(card, robot);
        assertEquals(0, robot.getTileX());
        assertEquals(Direction.SOUTH, robot.getDirection());
        robot.setDirection(Direction.EAST);

        board.moveObject(robot, 0, 0);
    }
    @Test
    public void cardMoveTestUTurn(){
        assertEquals(0, robot.getTileX());
        NonTextureProgramCard card = new NonTextureProgramCard(0,true,false,false, 0);
        turnHandler.cardMove(card, robot);
        assertEquals(0, robot.getTileX());
        assertEquals(Direction.WEST, robot.getDirection());
        robot.setDirection(Direction.EAST);

        board.moveObject(robot, 0, 0);
    }
}
