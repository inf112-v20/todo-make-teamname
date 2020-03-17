package inf112.skeleton.app.boardObjectsTests;

import inf112.skeleton.app.board.Board;
import inf112.skeleton.app.board.Direction;
import inf112.skeleton.app.main.Game;
import inf112.skeleton.app.objects.boardObjects.Pit;
import inf112.skeleton.app.objects.boardObjects.Pusher;
import inf112.skeleton.app.objects.player.Robot;
import org.junit.BeforeClass;
import org.junit.Test;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

public class PitTest {
    private static Game game;
    private static Robot robot;
    private static Board board;


    @BeforeClass
    public static void setUp(){
        game = new Game();
        game.setBoard(new Board(2,1,1));
        board = game.getBoard();
        robot = new Robot();
        Pit pit = new Pit();
        board.addObject(robot, 1, 0);
        board.addObject(pit,1,0);
    }

    @Test
    public void pitTest(){
        assertFalse(robot.isDestroyed());
        game.pitFall(robot);
        assertTrue(robot.isDestroyed());
    }
}
