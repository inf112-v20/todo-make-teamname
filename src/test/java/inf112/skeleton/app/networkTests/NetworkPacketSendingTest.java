package inf112.skeleton.app.networkTests;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.backends.headless.HeadlessApplication;
import com.badlogic.gdx.backends.headless.HeadlessApplicationConfiguration;
import com.badlogic.gdx.graphics.GL20;
import inf112.skeleton.app.EmptyApplicationListener;
import inf112.skeleton.app.main.Game;
import inf112.skeleton.app.networking.MPClient;
import inf112.skeleton.app.networking.MPServer;
import inf112.skeleton.app.networking.Packets;
import org.junit.BeforeClass;
import org.junit.Test;

import static org.junit.Assert.*;
import static org.mockito.Mockito.mock;

public class NetworkPacketSendingTest {

    private static MPServer server;
    private static MPClient client;
    private static Game game;
    private static MPClient newClient;
    private static Game game1;

    @BeforeClass
    public static void setUp(){
        HeadlessApplicationConfiguration conf = new HeadlessApplicationConfiguration();
        new HeadlessApplication(new EmptyApplicationListener(), conf);
        Gdx.gl = mock(GL20.class);
        game = new Game();
        game1 = new Game();
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
        game1.setClient(newClient);
        game1.textureSetUp();
        game1.cardBoxSetUp();
        game1.readyButtonSetUp();
        game1.logSetUp();
        game1.createBoardAndPlayers("riskyexchange");
        Packets.Packet05Name packet05Name = new Packets.Packet05Name();
        packet05Name.name = new String[]{"a","b"};
        game.receiveNames(packet05Name);
        game1.receiveNames(packet05Name);
    }

    @Test
    public void deleteUnconnectedPlayersTest(){
        assertEquals(game.getIdPlayerHash().size(), 4);
        game.deleteUnconnectedPlayers();
        assertEquals(game.getIdPlayerHash().size(), 2);
    }

    @Test
    public void messageTest(){
        client.sendMessage("Testing");
        try {
            Thread.sleep(250);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        assertEquals(game.getLog().getFirst(), game1.getLog().getFirst());
        assertNotEquals(game.getLog().getFirst(), null);
    }

    @Test
    public void sendNameTest(){
        client.sendName("Host");
        newClient.sendName("Guest");
        try {
            Thread.sleep(250);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        assertEquals(game.getNames()[1], game1.getNames()[1]);
        assertEquals(game.getNames()[2], game1.getNames()[2]);
    }

    @Test
    public void sendReadyTest(){
        Packets.Packet06ReadySignal signal = new Packets.Packet06ReadySignal();
        signal.signal = true;
        client.sendReady(signal);
        newClient.sendReady(signal);
        try {
            Thread.sleep(250);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        for (int i = 1; i < 3; i++) {
            assertTrue(game.getAllReady()[i]);
            assertTrue(game1.getAllReady()[i]);
        }
    }

    @Test
    public void sendShutdownTest(){
        game.shutdownRobot();
        try {
            Thread.sleep(250);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        assertTrue(game.getPlayersShutdown()[1]);
        assertTrue(game1.getPlayersShutdown()[1]);
    }
}
