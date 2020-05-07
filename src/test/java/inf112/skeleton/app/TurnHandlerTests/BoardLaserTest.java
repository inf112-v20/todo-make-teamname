package inf112.skeleton.app.TurnHandlerTests;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.backends.headless.HeadlessApplication;
import com.badlogic.gdx.backends.headless.HeadlessApplicationConfiguration;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import inf112.skeleton.app.EmptyApplicationListener;
import inf112.skeleton.app.board.Board;
import inf112.skeleton.app.main.Game;
import inf112.skeleton.app.main.TurnHandler;
import inf112.skeleton.app.networking.Packets;
import inf112.skeleton.app.objects.boardObjects.BoardLaser;
import inf112.skeleton.app.objects.player.Robot;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import static org.junit.Assert.*;
import static org.mockito.Mockito.mock;

public class BoardLaserTest {
    private static Game game;
    private static Robot robot;
    private static Board board;
    private static TurnHandler turnHandler;


    @BeforeClass
    public static void setUp(){
        HeadlessApplicationConfiguration conf = new HeadlessApplicationConfiguration();
        new HeadlessApplication(new EmptyApplicationListener(), conf);
        Gdx.gl = mock(GL20.class);
        game = new Game();
        game.setBoard(new Board(2,1,1));
        game.gamePhasesSetUp();
        turnHandler = game.getTurnHandler();
        board = game.getBoard();
        BoardLaser laser = new BoardLaser();
        board.addObject(laser,1,0);
        game.logSetUp();
        Packets.Packet05Name packet05Name = new Packets.Packet05Name();
        packet05Name.name = new String[]{"a","b"};
        game.receiveNames(packet05Name);
    }

    @Before
    public void robotSetUp(){
        Texture mockTexture = mock(Texture.class);
        Texture[] mockImages = {mockTexture};
        robot = new Robot(mockImages);
        board.addObject(robot, 1, 0);
    }

    @Test
    public void boardLaserDamageTest(){
        assertEquals(robot.getHealth(), 9);
        assertFalse(robot.isDestroyed());
        turnHandler.boardLasersShoot(robot);
        assertEquals(robot.getHealth(), 8);
    }

    @Test
    public void boardLaserKillTest(){
        assertEquals(robot.getHealth(), 9);
        assertFalse(robot.isDestroyed());
        for (int i = 0; i < 9; i++) {
            turnHandler.boardLasersShoot(robot);
        }
        assertEquals(robot.getHealth(), 0);
        assertTrue(robot.isDestroyed());
    }

}
