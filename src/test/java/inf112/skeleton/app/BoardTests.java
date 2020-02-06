package inf112.skeleton.app;

import static org.junit.Assert.*;

import inf112.skeleton.app.objects.Robot;
import org.junit.Before;
import org.junit.Test;

public class BoardTests {
    private Board board;
    private Robot robot;

    @Before
    public void setUp(){
        board = new Board(12, 12);
        robot = new Robot(Direction.NORTH);
    }

    @Test
    public void checkTrue() {
        assertTrue(true);
    }

    @Test
    public void checkMoveDir(){
        board.addObject(robot, 0, 0);
        int prevX = robot.getTileX();
        board.moveObjectDir(robot, robot.getDirection());
        assertNotEquals(prevX, robot.getTileX());
    }

}
