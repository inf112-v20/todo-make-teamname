package inf112.skeleton.app;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;

public class BoardTests {
    public Board board;

    @Before
    public void setUp(){
        board = new Board(12, 12);
    }

    @Test
    public void checkTrue() {
        assertTrue(true);
    }

    @Test
    public void checkMoveDir(){

    }

}
