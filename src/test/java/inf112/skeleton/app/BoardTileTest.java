package inf112.skeleton.app;

import org.junit.Before;
import org.junit.Test;

import static junit.framework.TestCase.assertTrue;

public class BoardTileTest {
    BoardTile tile;

    @Before
    public void setUp(){
        tile = new BoardTile(1,1);
    }

    /*@Test
    public void sortTest(){

        for (int i = 1; i < 5; i++) {
            TempBoardObject object = new TempBoardObject(i);
            tile.add(object);
        }
        IBoardObject last = tile.remove();
        for (int i = 0; i < 3; i++) {
            IBoardObject next = tile.remove();
            assertTrue(last.getSize() > next.getSize());
            last = next;
        }
    }
    */

}
