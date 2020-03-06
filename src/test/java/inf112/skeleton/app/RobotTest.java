package inf112.skeleton.app;
import inf112.skeleton.app.board.Direction;
import inf112.skeleton.app.objects.player.Robot;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class RobotTest {
    Robot testBot;

    Robot mockRobot;
    Robot mockRobotClass;


    @Before
    public void setUp(){
        testBot = new Robot();

        mockRobotClass = mock(Robot.class);
        mockRobot = new Robot();
    }

    @Test
    public void testX(){
        Robot aRobot = new Robot();
        aRobot.setTileX(5);

        when(mockRobotClass.getTileX()).thenReturn(5);
        int getRobotX = aRobot.getTileX();
        assertEquals(getRobotX, 5);
    }

    @Test
    public void testDirection(){
        testBot.setDirection(Direction.NORTH);
        assertEquals(Direction.NORTH,testBot.getDirection());
        testBot.setDirection(Direction.EAST);
        assertEquals(Direction.EAST, testBot.getDirection());

    }

    @Test
    public void testY(){
        testBot.setTileY(5);
        assertEquals(5, testBot.getTileY());
    }

    @Test
    public void testTakeDamage(){
        assertEquals(9, testBot.getHealth());
        testBot.takeDamage();
        assertEquals(8, testBot.getHealth());
    }

    @Test
    public void testHealDamage(){
        assertEquals(9, testBot.getHealth());
        testBot.healDamage();
        assertEquals(10, testBot.getHealth());
    }

    @Test
    public void testDestroy(){
        assertFalse(testBot.isDestroyed());
        testBot.destroy();
        assertTrue(testBot.isDestroyed());
    }

    @Test
    public void testRespawn(){
        testBot.destroy();
        assertTrue(testBot.isDestroyed());
        assertEquals(0, testBot.getHealth());
        testBot.respawn();
        assertFalse(testBot.isDestroyed());
        assertEquals(9, testBot.getHealth());
    }

}
