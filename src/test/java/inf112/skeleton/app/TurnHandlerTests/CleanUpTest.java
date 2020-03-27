package inf112.skeleton.app.TurnHandlerTests;

import com.badlogic.gdx.ApplicationListener;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.backends.headless.HeadlessApplication;
import com.badlogic.gdx.backends.headless.HeadlessApplicationConfiguration;
import com.badlogic.gdx.graphics.GL20;
import inf112.skeleton.app.board.Board;
import inf112.skeleton.app.main.Game;
import inf112.skeleton.app.main.TurnHandler;
import inf112.skeleton.app.objects.player.Player;
import inf112.skeleton.app.objects.player.Robot;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import static org.junit.Assert.*;
import static org.mockito.Mockito.mock;

public class CleanUpTest {

    private static Game game;
    private static Player player;
    private static Robot robot;
    private static Board board;
    private static TurnHandler turnHandler;


    @BeforeClass
    public static void setUp(){
        HeadlessApplicationConfiguration conf = new HeadlessApplicationConfiguration();
        new HeadlessApplication(new ApplicationListener() {
            @Override
            public void create() { }
            @Override
            public void resize(int i, int i1) { }
            @Override
            public void render() { }
            @Override
            public void pause() { }
            @Override
            public void resume() { }
            @Override
            public void dispose() { }
        }, conf);
        Gdx.gl = mock(GL20.class);
        game = new Game();
        game.setBoard(new Board(1,1,1));
        game.gamePhasesSetUp();
        turnHandler = game.getTurnHandler();
    }

    @Before
    public void resetPlayer(){
        player = new Player();
        board = game.getBoard();
        board.addObject(player.getRobot(), 0, 0);
        robot = player.getRobot();
    }

    @Test
    public void cleanUpTest(){
        robot.destroy();
        assertTrue(robot.isDestroyed());
        assertEquals(2, robot.getLife());
        turnHandler.cleanUp(player);
        assertFalse(robot.isDestroyed());
    }

    @Test
    public void cleanUpTestEndGame(){
        assertFalse(turnHandler.getGameIsDone());
        while(robot.getLife() > 0){
            robot.destroy();
        }
        turnHandler.cleanUp(player);
        assertTrue(turnHandler.getGameIsDone());
    }
}
