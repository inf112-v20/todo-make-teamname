package inf112.skeleton.app.TurnHandlerTests;

import inf112.skeleton.app.board.Board;
import inf112.skeleton.app.main.Game;
import inf112.skeleton.app.main.TurnHandler;
import inf112.skeleton.app.objects.player.Robot;
import org.junit.BeforeClass;
import org.junit.Test;

import static org.junit.Assert.assertTrue;

public class ConveyorBeltTest {
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
        robot = new Robot();
        board.addObject(robot, 1, 0);
    }

    @Test
    public void conveyorMoveTest(){
        assertTrue(board.getTile(1,0).getObjects()[2] instanceof Robot);
        turnHandler.conveyorMove(robot);
        assertTrue(board.getTile(1,1).getObjects()[2] instanceof Robot);
    }

}
