package inf112.skeleton.app;

import static org.junit.Assert.*;

import inf112.skeleton.app.objects.IBoardObject;
import inf112.skeleton.app.objects.Robot;
import org.junit.After;
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
    public void addObjectTest(){
        IBoardObject prevObjec = board.getTile(1, 1).getObjects()[BoardTile.ROBOT];
        assertNull(prevObjec);
        board.addObject(robot, 1, 1);
        IBoardObject newObject = board.getTile(1, 1).getObjects()[BoardTile.ROBOT];
        assertNotNull(newObject);
    }

    @Test
    public void moveDirTest(){
        board.addObject(robot, 0, 0);
        int prevY = robot.getTileY();
        board.moveObjectDir(robot, robot.getDirection());
        assertNotEquals(prevY, robot.getTileY());
    }

    @Test
    public void moveTest(){
        board.addObject(robot, 0, 0);
        assertNotNull(board.getTile(0,0).getObjects()[BoardTile.ROBOT]);
        board.moveObject(robot, 5, 5);
        assertNull(board.getTile(0,0).getObjects()[BoardTile.ROBOT]);
        assertNotNull(board.getTile(5,5).getObjects()[BoardTile.ROBOT]);
    }

    //Testing both removeObject methods
    @Test
    public void removeObjectTest(){
        board.addObject(robot, 0, 0);
        assertNotNull(board.getTile(0,0).getObjects()[BoardTile.ROBOT]);
        board.removeObject(0, 0);
        assertNull(board.getTile(0,0).getObjects()[BoardTile.ROBOT]);
        board.addObject(robot, 0, 0);
        assertNotNull(board.getTile(0, 0).getObjects()[BoardTile.ROBOT]);
        board.removeObject(robot);
        assertNull(board.getTile(0,0).getObjects()[BoardTile.ROBOT]);
    }

    @After
    public void cleanBoard(){
        for (int x = 0; x < board.getWidth(); x++) {
            for (int y = 0; y < board.getHeight(); y++) {
                board.removeObject(x, y);
            }
        }
    }

}
