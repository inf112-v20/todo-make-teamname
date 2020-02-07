package inf112.skeleton.app;
import inf112.skeleton.app.objects.Robot;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;

public class RobotTest {
    Robot testBot;
    @Before
    public void setUp(){
        testBot = new Robot();

    }

    @Test
    public void testDirection(){
        testBot.setDirection(Direction.NORTH);
        assertEquals(Direction.NORTH,testBot.getDirection());
        testBot.setDirection(Direction.EAST);
        assertEquals(Direction.EAST, testBot.getDirection());

    }

    @Test
    public void testX(){
        testBot.setTileX(5);
        assertEquals(5, testBot.getTileX());
    }

    @Test
    public void testY(){
        testBot.setTileY(5);
        assertEquals(5, testBot.getTileY());
    }


}
