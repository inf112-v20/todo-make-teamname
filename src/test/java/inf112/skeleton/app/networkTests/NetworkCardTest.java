package inf112.skeleton.app.networkTests;


import com.badlogic.gdx.graphics.Texture;
import inf112.skeleton.app.main.Game;
import inf112.skeleton.app.networking.MPClient;
import inf112.skeleton.app.networking.MPServer;
import inf112.skeleton.app.objects.cards.CardTranslator;
import inf112.skeleton.app.objects.cards.ProgramCard;
import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.mock;

public class NetworkCardTest {
    private static MPServer server;
    private static MPClient client;
    private static Game game;
    private static MPClient newClient;

    @BeforeClass
    public static void setUp(){
        game = mock(Game.class);
        server = new MPServer(40000, 42000);
        server.run();
        client = new MPClient(server.getAddress(), game, 40000, 42000);
    }

    @Test
    public void serverReceivedCardsTest(){
        Texture mockTexture = mock(Texture.class);

        ProgramCard card0 = new ProgramCard("Move1", mockTexture, 1);
        ProgramCard card1 = new ProgramCard("Move2",  mockTexture, 1);
        ProgramCard card2 = new ProgramCard("UTurn",  mockTexture, 1);
        ProgramCard card3 = new ProgramCard("RotateLeft",  mockTexture, 1);
        ProgramCard card4 = new ProgramCard("RotateRight",  mockTexture, 1);
        ProgramCard[] cards = {card0, card1, card2, card3, card4};
        client.sendCards(cards);
        try {
            //This thread sleeps so the server gets some time to receive the cards
            Thread.sleep(50);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        for (int i = 0; i < cards.length; i++) {
            assertEquals((long) cards[i].getValue(), CardTranslator.intToProgramCard(server.getLastCards().get(i)).getValue());
            assertEquals(cards[i].getRotate(), CardTranslator.intToProgramCard(server.getLastCards().get(i)).getRotate());
            assertEquals(cards[i].getRotateLeft(), CardTranslator.intToProgramCard(server.getLastCards().get(i)).getRotateLeft());
            assertEquals(cards[i].getRotateRight(), CardTranslator.intToProgramCard(server.getLastCards().get(i)).getRotateRight());
        }

    }

    @Test
    public void clientReceivedCardsTest(){
        Texture mockTexture = mock(Texture.class);
        ProgramCard card0 = new ProgramCard("Move1", mockTexture, 1);
        ProgramCard card1 = new ProgramCard("Move2",  mockTexture, 1);
        ProgramCard card2 = new ProgramCard("UTurn",  mockTexture, 1);
        ProgramCard card3 = new ProgramCard("RotateLeft",  mockTexture, 1);
        ProgramCard card4 = new ProgramCard("RotateRight",  mockTexture, 1);
        ProgramCard[] cards = {card0, card1, card2, card3, card4};
        client.sendCards(cards);
        try {
            //This thread sleeps so the server gets some time to receive the cards
            Thread.sleep(10);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        for (int i = 0; i < cards.length; i++) {
            assertEquals((long) cards[i].getValue(), client.getLastCardTransfer()[i].getValue());
            assertEquals(cards[i].getRotate(), client.getLastCardTransfer()[i].getRotate());
            assertEquals(cards[i].getRotateLeft(), client.getLastCardTransfer()[i].getRotateLeft());
            assertEquals(cards[i].getRotateRight(), client.getLastCardTransfer()[i].getRotateRight());
        }
    }


    @Test
    public void idTest(){
        assertEquals(client.getId(), 1);
        newClient = new MPClient(server.getAddress(), game,40000, 42000);
        try {
            Thread.sleep(15);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        assertEquals(newClient.getId(), 2);
        newClient.dispose();
    }

    @Test
    public void connectTest(){
        assertTrue(client.getConnection());
    }

    @AfterClass
    public static void dispose(){
        server.dispose();
        client.dispose();
    }
}
