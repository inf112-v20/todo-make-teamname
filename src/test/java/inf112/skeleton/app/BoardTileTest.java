package inf112.skeleton.app;

import inf112.skeleton.app.board.BoardTile;
import inf112.skeleton.app.objects.player.Robot;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;

public class BoardTileTest {
    BoardTile tile;

    @Before
    public void setUp(){
        tile = new BoardTile();
    }


    @Test
    public void removeObj(){
        tile = new BoardTile();
        Robot robot = new Robot();
        tile.add(robot);
        tile.remove(BoardTile.ROBOT);
        assertNull(tile.getObjects()[BoardTile.ROBOT]);
    }
    @Test
    public void addObj(){
        tile = new BoardTile();
        Robot robot = new Robot();
        tile.add(robot);
        assertNotNull( tile.getObjects()[BoardTile.ROBOT]);
    }
}
