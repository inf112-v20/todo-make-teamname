package inf112.skeleton.app;

import static org.junit.Assert.*;

import inf112.skeleton.app.objects.IBoardObject;
import inf112.skeleton.app.objects.Robot;
import org.junit.Before;
import org.junit.Test;
import sun.nio.cs.ext.IBM037;


public class BoardTests {
    private Board board;
    private Robot robot;

    @Before
    public void setUp(){
        board = new Board(12, 12);
        robot = new Robot(Direction.NORTH);
    }

    @Test
    public void addObjectTest(){
        IBoardObject prevObjec = board.getTile(0, 0).getObjects()[BoardTile.ROBOT];
        assertNull(prevObjec);
        board.addObject(robot, 0, 0);
        IBoardObject newObject = board.getTile(0, 0).getObjects()[BoardTile.ROBOT];
        assertNotNull(newObject);
    }

    @Test
    public void checkMoveDirTest(){
        board.addObject(robot, 0, 0);
        int prevY = robot.getTileY();
        board.moveObjectDir(robot, robot.getDirection());
        assertNotEquals(prevY, robot.getTileY());
    }

}
