package inf112.skeleton.app.TurnHandlerTests;

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
import inf112.skeleton.app.networking.Packets;
import inf112.skeleton.app.objects.boardObjects.Pusher;
import inf112.skeleton.app.objects.player.Player;
import inf112.skeleton.app.objects.player.Robot;
import org.junit.After;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.mock;

public class BumpRobotTest {
    private static Game game;
    private static Player player;
    private static Robot robot, robot0;
    private static Board board;
    private static TurnHandler turnHandler;
    private static Texture[] mockImages;


    @BeforeClass
    public static void setUp(){
        HeadlessApplicationConfiguration conf = new HeadlessApplicationConfiguration();
        new HeadlessApplication(new EmptyApplicationListener(), conf);
        Gdx.gl = mock(GL20.class);
        game = new Game();
        game.setBoard(new Board(3,1,1));
        game.gamePhasesSetUp();
        game.textureSetUp();
        game.cardBoxSetUp();
        game.readyButtonSetUp();
        game.logSetUp();
        Packets.Packet05Name packet05Name = new Packets.Packet05Name();
        packet05Name.name = new String[]{"a","b"};
        game.receiveNames(packet05Name);
        Texture mockTexture = mock(Texture.class);
        mockImages = new Texture[]{mockTexture};
        player = new Player(mockImages);
        turnHandler = game.getTurnHandler();
        board = game.getBoard();
        robot = player.getRobot();
        robot0 = new Robot(Direction.NORTH, mockImages);
        robot.setDirection(Direction.EAST);
    }

    @Before
    public void setUpRobots(){
        board.addObject(robot, 0, 0);
        board.addObject(robot0, 1 ,0);
    }

    @Test
    public void bumpOneRobot(){
        assertEquals(robot0.getTileX(), 1);
        turnHandler.moveRobot(robot, robot.getDirection());
        assertEquals(robot0.getTileX(), 2);
    }

    @Test
    public void bumpARobotOffBoard(){

        assertEquals(robot0.getTileX(), 1);
        turnHandler.moveRobot(robot, robot.getDirection());
        turnHandler.moveRobot(robot, robot.getDirection());
        assertTrue(robot0.isDestroyed());
    }

    @Test
    public void bumpTwoRobots(){
        Robot robot1 = new Robot(mockImages);
        board.addObject(robot1, 2, 0);
        assertEquals(robot.getTileX(), 0);
        assertEquals(robot0.getTileX(), 1);
        assertEquals(robot1.getTileX(), 2);
        turnHandler.moveRobot(robot, robot.getDirection());
        assertEquals(robot.getTileX(), 1);
        assertEquals(robot0.getTileX(), 2);
        assertTrue(robot1.isDestroyed());
    }

    @Test
    public void bumpOneRobotIntoPusherStop(){
        Pusher pusher = new Pusher(Direction.EAST);
        board.addObject(pusher, 2, 0);
        assertEquals(robot0.getTileX(), 1);
        turnHandler.moveRobot(robot, robot.getDirection());
        assertEquals(robot0.getTileX(), 1);
        board.removeObject(pusher, 0);
    }

    @Test
    public void bumpOneRobotIntoPusherDontStop(){
        Pusher pusher = new Pusher(Direction.WEST);
        board.addObject(pusher, 2, 0);
        assertEquals(robot0.getTileX(), 1);
        turnHandler.moveRobot(robot, robot.getDirection());
        assertEquals(robot0.getTileX(), 2);
        assertEquals(robot.getTileX(), 1);
        board.removeObject(pusher, 0);
    }
    @Test
    public void bumpOneRobotIntoWallStop(){

    }

    @Test
    public void bumpOneRobotIntoWallDontStop(){

    }

    @Test
    public void bumpTwoRobotIntoPusherStop(){

    }

    @Test
    public void bumpTwoRobotIntoPusherDontStop(){

    }

    @Test
    public void bumpTwoRobotIntoWallStop(){

    }

    @Test
    public void bumpTwoRobotIntoWallDontStop(){

    }


    @After
    public void removeRobots(){
        board.removeObject(robot0);
        board.removeObject(robot);
    }
}
