package inf112.skeleton.app.networkTests;

import inf112.skeleton.app.main.Game;
import inf112.skeleton.app.networking.MPClient;
import inf112.skeleton.app.networking.MPServer;
import org.junit.BeforeClass;
import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.mock;

public class NetworkTest {
    private static MPServer server;
    private static MPClient client;
    private static Game game;

    @BeforeClass
    public static void setUp(){
        game = mock(Game.class);
        server = new MPServer();
        server.run();
        client = new MPClient(server.getAddress(),game);
    }

    @Test
    public void idTest(){
        assertEquals(client.getId(), 1);
        MPClient newClient = new MPClient(server.getAddress(), game);
        try {
            Thread.sleep(15);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        assertEquals(newClient.getId(), 2);
    }

    @Test
    public void connectTest(){
        assertTrue(client.getConnection());
    }

}
