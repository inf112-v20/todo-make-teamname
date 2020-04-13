package inf112.skeleton.app.TurnHandlerTests;

import com.badlogic.gdx.graphics.Texture;
import inf112.skeleton.app.board.Board;
import inf112.skeleton.app.main.Game;
import inf112.skeleton.app.main.TurnHandler;
import inf112.skeleton.app.objects.player.Robot;
import org.junit.BeforeClass;
import org.junit.Test;

import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.mock;

public class ExpressConveyorBeltTest {
    private static Game game;
    private static Robot robot;
    private static Board board;
    private static TurnHandler turnHandler;


    @BeforeClass
    public static void setUp(){
        game = new Game();
        game.boardSetUp("riskyexchange");
        game.gamePhasesSetUp();
        turnHandler = game.getTurnHandler();
        board = game.getBoard();
        Texture mockTexture = mock(Texture.class);
        Texture[] mockImages = {mockTexture};
        robot = new Robot(mockImages);
        board.addObject(robot, 6, 6);
    }

    @Test
    public void expressConveyorMoveTest(){
        assertTrue(board.getTile(6,6).getObjects()[2] instanceof Robot);
        turnHandler.expressConveyorMove(robot);
        turnHandler.conveyorMove(robot);
        assertTrue(board.getTile(4,6).getObjects()[2] instanceof Robot);
    }
}
