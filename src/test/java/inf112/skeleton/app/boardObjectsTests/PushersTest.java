package inf112.skeleton.app.boardObjectsTests;

import inf112.skeleton.app.board.Board;
import inf112.skeleton.app.board.Direction;
import inf112.skeleton.app.main.Game;
import inf112.skeleton.app.main.menuScreens.TurnHandler;
import inf112.skeleton.app.objects.boardObjects.Pusher;
import inf112.skeleton.app.objects.player.Robot;
import org.junit.BeforeClass;
import org.junit.Test;

import static org.junit.Assert.assertTrue;

public class PushersTest {
    private static Game game;
    private static Robot robot;
    private static Board board;
    private static TurnHandler turnHandler;


    @BeforeClass
    public static void setUp(){
        game = new Game();
        game.setBoard(new Board(2,1,1));
        game.gamePhasesSetUp();
        turnHandler = game.getTurnHandler();
        board = game.getBoard();
        robot = new Robot();
        Pusher pusher = new Pusher(Direction.WEST);
        board.addObject(robot, 1, 0);
        board.addObject(pusher,1,0);
    }

    @Test
    public void pushersTest(){
        assertTrue(board.getTile(1,0).getObjects()[2] instanceof Robot);
        turnHandler.pushersMove(robot);
        assertTrue(board.getTile(0,0).getObjects()[2] instanceof Robot);
    }
}
