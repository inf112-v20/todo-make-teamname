package inf112.skeleton.app.boardObjectsTests;

import inf112.skeleton.app.board.Board;
import inf112.skeleton.app.main.Game;
import inf112.skeleton.app.objects.boardObjects.BoardLaser;
import inf112.skeleton.app.objects.player.Robot;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import static org.junit.Assert.*;

public class BoardLaserTest {
    private static Game game;
    private static Robot robot;
    private static Board board;


    @BeforeClass
    public static void setUp(){
        game = new Game();
        game.setBoard(new Board(2,1,1));
        board = game.getBoard();
        BoardLaser laser = new BoardLaser();
        board.addObject(laser,1,0);
    }

    @Before
    public void robotSetUp(){
        robot = new Robot();
        board.addObject(robot, 1, 0);
    }

    @Test
    public void boardLaserDamageTest(){
        assertEquals(robot.getHealth(), 9);
        assertFalse(robot.isDestroyed());
        game.boardLasersShoot(robot);
        assertEquals(robot.getHealth(), 8);
    }

    @Test
    public void boardLaserKillTest(){
        assertEquals(robot.getHealth(), 9);
        assertFalse(robot.isDestroyed());
        for (int i = 0; i < 9; i++) {
            game.boardLasersShoot(robot);
        }
        assertEquals(robot.getHealth(), 0);
        assertTrue(robot.isDestroyed());
    }

}
