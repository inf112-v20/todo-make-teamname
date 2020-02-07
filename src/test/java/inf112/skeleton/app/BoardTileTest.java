package inf112.skeleton.app;

import com.badlogic.gdx.graphics.Texture;
import inf112.skeleton.app.objects.Robot;
import org.junit.Before;
import org.junit.Test;

import static junit.framework.Assert.assertNotNull;
import static junit.framework.Assert.assertNull;

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
