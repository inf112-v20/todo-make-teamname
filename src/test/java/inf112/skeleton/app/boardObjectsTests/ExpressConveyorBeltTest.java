package inf112.skeleton.app.boardObjectsTests;

import inf112.skeleton.app.board.Board;
import inf112.skeleton.app.main.Game;
import inf112.skeleton.app.objects.player.Robot;
import org.junit.BeforeClass;
import org.junit.Test;

import static org.junit.Assert.assertTrue;

public class ExpressConveyorBeltTest {
    private static Game game;
    private static Robot robot;
    private static Board board;


    @BeforeClass
    public static void setUp(){
        game = new Game();
        game.boardSetUp("riskyexchange");
        board = game.getBoard();
        robot = new Robot();
        board.addObject(robot, 6, 6);
    }

    @Test
    public void expressConveyorMoveTest(){
        assertTrue(board.getTile(6,6).getObjects()[2] instanceof Robot);
        game.expressConveyorMove(robot);
        game.conveyorMove(robot);
        assertTrue(board.getTile(4,6).getObjects()[2] instanceof Robot);
    }
}
