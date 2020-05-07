package inf112.skeleton.app.networkTests;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Graphics;
import com.badlogic.gdx.backends.headless.HeadlessApplication;
import com.badlogic.gdx.backends.headless.HeadlessApplicationConfiguration;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.scenes.scene2d.Stage;
import inf112.skeleton.app.EmptyApplicationListener;
import inf112.skeleton.app.main.Game;
import inf112.skeleton.app.networking.MPClient;
import inf112.skeleton.app.networking.MPServer;
import org.junit.BeforeClass;
import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.mock;

public class NetworkPacketSendingTest {

    private static MPServer server;
    private static MPClient client;
    private static Game game;
    private static MPClient newClient;

    @BeforeClass
    public static void setUp(){
        HeadlessApplicationConfiguration conf = new HeadlessApplicationConfiguration();
        new HeadlessApplication(new EmptyApplicationListener(), conf);
        Gdx.gl = mock(GL20.class);
        game = new Game();
        Game game1 = new Game();
        server = new MPServer(20000, 22000);
        server.run();
        client = new MPClient(server.getAddress(), game, 20000, 22000);
        newClient = new MPClient(server.getAddress(),game1, 20000, 22000);
        game.setClient(client);
        game.textureSetUp();
        game.cardBoxSetUp();
        game.readyButtonSetUp();
        game.logSetUp();
        game.createBoardAndPlayers("riskyexchange");
    }

    @Test
    public void deleteUnconnectedPlayersTest(){
        assertEquals(game.getIdPlayerHash().size(), 4);
        game.deleteUnconnectedPlayers();
        assertEquals(game.getIdPlayerHash().size(), 2);
    }

    @Test
    public void illDoThisLater(){

    }
}
