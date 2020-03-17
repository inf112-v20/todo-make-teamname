package inf112.skeleton.app.boardObjectsTests;

import inf112.skeleton.app.board.Board;
import inf112.skeleton.app.board.Direction;
import inf112.skeleton.app.main.Game;
import inf112.skeleton.app.objects.boardObjects.GearClockwise;
import inf112.skeleton.app.objects.boardObjects.GearCounterClockwise;
import inf112.skeleton.app.objects.player.Robot;
import org.junit.BeforeClass;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class GearTest {
    private static Game game;
    private static Robot robot1, robot2;
    private static Board board;


    @BeforeClass
    public static void setUp(){
        game = new Game();
        game.setBoard(new Board(2,1,1));
        board = game.getBoard();
        robot1 = new Robot();
        robot2 = new Robot();
        GearClockwise gearClockwise = new GearClockwise();
        GearCounterClockwise gearCounterClockwise = new GearCounterClockwise();
        board.addObject(robot1, 1, 0);
        board.addObject(robot2, 0, 0);
        board.addObject(gearClockwise,1,0);
        board.addObject(gearCounterClockwise, 0, 0);
    }

    @Test
    public void gearClockwiseTest(){
        assertEquals(robot1.getDirection(), Direction.EAST);
        game.gearsMove(robot1);
        assertEquals(robot1.getDirection(), Direction.SOUTH);
    }

    @Test
    public void gearCounterClockwiseTest(){
        assertEquals(robot2.getDirection(), Direction.EAST);
        game.gearsMove(robot2);
        assertEquals(robot2.getDirection(), Direction.NORTH);
    }

}
