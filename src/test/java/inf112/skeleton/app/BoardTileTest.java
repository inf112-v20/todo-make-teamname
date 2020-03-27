package inf112.skeleton.app;

import com.badlogic.gdx.graphics.Texture;
import inf112.skeleton.app.board.BoardTile;
import inf112.skeleton.app.objects.player.Robot;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.mockito.Mockito.mock;

public class BoardTileTest {
    BoardTile tile;

    @Before
    public void setUp(){
        tile = new BoardTile();
    }


    @Test
    public void removeObj(){
        tile = new BoardTile();
        Texture mockTexture = mock(Texture.class);
        Texture[] mockImages = {mockTexture};
        Robot robot = new Robot(mockImages);
        tile.add(robot);
        tile.remove(BoardTile.ROBOT);
        assertNull(tile.getObjects()[BoardTile.ROBOT]);
    }
    @Test
    public void addObj(){
        tile = new BoardTile();
        Texture mockTexture = mock(Texture.class);
        Texture[] mockImages = {mockTexture};
        Robot robot = new Robot(mockImages);
        tile.add(robot);
        assertNotNull( tile.getObjects()[BoardTile.ROBOT]);
    }
}
