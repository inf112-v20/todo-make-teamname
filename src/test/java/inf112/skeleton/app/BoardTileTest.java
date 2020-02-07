package inf112.skeleton.app;

import com.badlogic.gdx.graphics.Texture;
import inf112.skeleton.app.objects.Robot;
import org.junit.Before;
import org.junit.Test;

import static junit.framework.TestCase.assertTrue;
import static org.junit.Assert.assertEquals;

public class BoardTileTest {
    BoardTile tile;

    @Before
    public void setUp(){
        tile = new BoardTile(1,1);
    }

    @Test
    public void testAdd(){
        Robot testBot = new Robot();
        tile.add(testBot);
        testBot.setTileX(1);
        testBot.setTileY(1);
        assertEquals(testBot,tile.getObjects()[BoardTile.ROBOT]);
    }


}
