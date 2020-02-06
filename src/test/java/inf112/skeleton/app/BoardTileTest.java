package inf112.skeleton.app;

import com.badlogic.gdx.graphics.Texture;
import org.junit.Before;
import org.junit.Test;

import static junit.framework.TestCase.assertTrue;

public class BoardTileTest {
    BoardTile tile;

    @Before
    public void setUp(){
        tile = new BoardTile(1,1);
    }

    @Test
    public void test(){
        tile = new BoardTile(1,1);
        Texture sprite = tile.getSprite();
    }


}
