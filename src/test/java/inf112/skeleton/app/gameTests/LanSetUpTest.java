package inf112.skeleton.app.gameTests;

import com.badlogic.gdx.ApplicationListener;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.backends.headless.HeadlessApplication;
import com.badlogic.gdx.backends.headless.HeadlessApplicationConfiguration;
import com.badlogic.gdx.graphics.GL20;
import inf112.skeleton.app.EmptyApplicationListener;
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
        new HeadlessApplication(new EmptyApplicationListener(), conf);
        Gdx.gl = mock(GL20.class);
        game1 = new Game();
        game2 = new Game();
        InetAddress ip = game1.hostGame("riskyexchange");
        game2.joinGame(ip);
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
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
    public void nrOfPlayersTest(){
        assertEquals(game1.getNrOfPlayers(), game2.getNrOfPlayers());
        assertEquals(game1.getNrOfPlayers(), 2);
    }


}
