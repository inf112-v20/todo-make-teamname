package inf112.skeleton.app.gameTests;

import com.badlogic.gdx.ApplicationListener;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.backends.headless.HeadlessApplication;
import com.badlogic.gdx.backends.headless.HeadlessApplicationConfiguration;
import com.badlogic.gdx.graphics.GL20;
import inf112.skeleton.app.main.Game;
import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;


import java.net.InetAddress;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.mock;

public class LanSetUpTest {
    private static Game game1, game2;

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
        game1 = new Game();
        game2 = new Game();
        game1.boardSetUp("riskyexchange");
        game2.boardSetUp("riskyexchange");
        game1.playerSetup();
        game2.playerSetup();
        InetAddress ip = game1.hostGame();
        game2.joinGame(ip);
    }

    @Test
    public void getHostConnectionTest(){
        assertTrue(game1.getConnection());
    }

    @Test
    public void getJoinedConnectionTest() {
        assertTrue(game2.getConnection());
    }

    @Test
    public void deleteUnconnectedPlayersTest(){
        assertEquals(game1.getIdPlayerHash().size(), 4);
        game1.deleteUnconnectedPlayers();
        assertEquals(game1.getIdPlayerHash().size(), 2);
    }

    @Test
    public void nrOfPlayersTest(){
        assertEquals(game1.getNrOfPlayers(), game2.getNrOfPlayers());
        assertEquals(game1.getNrOfPlayers(), 2);
    }

    @Test
    public void sendNameTest(){
        game1.sendName("a");
        game2.sendName("b");
        for (int i = 0; i < 2; i++) {
            assertEquals(game1.getNames()[i], game2.getNames()[i]);
        }
    }

}
