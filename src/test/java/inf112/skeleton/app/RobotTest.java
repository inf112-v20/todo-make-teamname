package inf112.skeleton.app;
import com.badlogic.gdx.graphics.Texture;
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
        Texture mockTexture = mock(Texture.class);
        Texture[] mockImages = {mockTexture};
        testBot = new Robot(mockImages);

        mockRobotClass = mock(Robot.class);
        mockRobot = new Robot(mockImages);
    }

    @Test
    public void testX(){
        Texture mockTexture = mock(Texture.class);
        Texture[] mockImages = {mockTexture};
        Robot aRobot = new Robot(mockImages);
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
        testBot.takeDamage();
        assertEquals(8, testBot.getHealth());
        testBot.healDamage();
        assertEquals(9, testBot.getHealth());
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
        assertEquals(7, testBot.getHealth()); // supposed to take 2 damage when you respawn
    }

}
