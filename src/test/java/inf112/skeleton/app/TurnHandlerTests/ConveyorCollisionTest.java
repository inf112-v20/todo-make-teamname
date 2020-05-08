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
import inf112.skeleton.app.objects.player.Robot;
import org.junit.BeforeClass;
import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.mock;


public class ConveyorCollisionTest {

    private static Game game;
    private static Robot robot;
    private static Board board;
    private static TurnHandler turnHandler;
    private static Robot robot0;
    private static ConveyorBelt conveyorBelt0, conveyorBelt1;

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
        board.addObject(robot, 0, 0);
        board.addObject(robot0,2,0);
        conveyorBelt0 = new ConveyorBelt(false, Direction.EAST);
        conveyorBelt1 = new ConveyorBelt(false, Direction.WEST);
        board.addObject(conveyorBelt0, 0 ,0);
        board.addObject(conveyorBelt1, 2, 0);
    }

    @Test
    public void collisionTest(){
        assertEquals(0,robot.getTileX());
        assertEquals(2, robot0.getTileX());
        turnHandler.conveyorMove(robot);
        turnHandler.conveyorMove(robot0);
        assertEquals(0,robot.getTileX());
        assertEquals(2, robot0.getTileX());
        assertTrue(turnHandler.conveyorCollision(conveyorBelt0, robot));
        assertTrue(turnHandler.conveyorCollision(conveyorBelt1, robot0));
    }
}
