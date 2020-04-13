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
import inf112.skeleton.app.objects.boardObjects.ConveyorBelt;
import inf112.skeleton.app.objects.boardObjects.Wall;
import inf112.skeleton.app.objects.player.Robot;
import org.junit.BeforeClass;
import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.mock;

public class RobotLaserTests {

    private static Game game;
    private static Robot robot,robot0, robot1;
    private static Robot[] robots;
    private static Board board;
    private static TurnHandler turnHandler;
    private static Wall westWall, eastWall;

    @BeforeClass
    public static void setUp() {
        HeadlessApplicationConfiguration conf = new HeadlessApplicationConfiguration();
        new HeadlessApplication(new EmptyApplicationListener(), conf);
        Gdx.gl = mock(GL20.class);
        game = new Game();
        game.setBoard(new Board(3, 1, 3));
        game.gamePhasesSetUp();
        turnHandler = game.getTurnHandler();
        board = game.getBoard();
        Texture mockTexture = mock(Texture.class);
        Texture[] mockImages = {mockTexture};
        robot = new Robot(mockImages);
        robot0 = new Robot(mockImages);
        robot1 = new Robot(mockImages);
        robots = new Robot[]{robot, robot0, robot1};
        westWall = new Wall(Direction.WEST);
        eastWall = new Wall(Direction.EAST);
    }

    @Test
    public void oneBigTest(){
        //Checking if it can shoot through multiple robots
        board.addObject(robot, 0, 0);
        board.addObject(robot0, 1, 0);
        board.addObject(robot1, 2, 0);
        assertEquals(9, robot0.getHealth());
        assertEquals(9, robot1.getHealth());
        turnHandler.robotLasersShoot(robot);
        assertEquals(8, robot0.getHealth());
        assertEquals(9, robot1.getHealth());

        //Checking that if a robot has a wall behind, it can still be hit in the front
        robot0.fullHealth();
        board.addObject(eastWall, 1, 0);
        assertEquals(9, robot0.getHealth());
        assertEquals(9, robot1.getHealth());
        turnHandler.robotLasersShoot(robot);
        assertEquals(8, robot0.getHealth());
        assertEquals(9, robot1.getHealth());

        //Checking if it can shoot through a wall
        robot0.fullHealth();
        board.addObject(westWall, 1, 0);
        assertEquals(9, robot0.getHealth());
        assertEquals(9, robot1.getHealth());
        turnHandler.robotLasersShoot(robot);
        assertEquals(9, robot0.getHealth());
        assertEquals(9, robot1.getHealth());


    }


}
