package inf112.skeleton.app;

import static org.junit.Assert.*;
import static org.mockito.Mockito.mock;

import com.badlogic.gdx.graphics.Texture;
import inf112.skeleton.app.board.Board;
import inf112.skeleton.app.board.BoardTile;
import inf112.skeleton.app.board.Direction;
import inf112.skeleton.app.objects.boardObjects.IBoardObject;
import inf112.skeleton.app.objects.player.Robot;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;


public class BoardTests {
    private Board board;
    private Robot robot;

    @Before
    public void setUp(){
        board = new Board(12, 12, 1);
        Texture mockTexture = mock(Texture.class);
        Texture[] mockImages = {mockTexture};
        robot = new Robot(Direction.NORTH, mockImages);
        board.removeObject(0, 0);

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
        board.moveObject(robot, robot.getDirection());
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
