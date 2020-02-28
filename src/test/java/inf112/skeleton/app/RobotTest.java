package inf112.skeleton.app;
import inf112.skeleton.app.objects.Robot;
import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class RobotTest {
    Robot testBot;

    Robot mockRobot;
    Robot mockRobotClass;

    public static void main(String[] args) {
        RobotTest tester = new RobotTest();
        tester.setUp();
        System.out.println(tester.testX()?"pass":"fail");

    }


    @Before
    public void setUp(){
        testBot = new Robot();

        mockRobotClass = mock(Robot.class);
        mockRobot = new Robot();
    }

    @Test
    public boolean testX(){
        Robot aRobot = new Robot();
        aRobot.setTileX(5);

        when(mockRobotClass.getTileX()).thenReturn(5);
        int getRobotX = aRobot.getTileX();
        return getRobotX == 5;
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
}
