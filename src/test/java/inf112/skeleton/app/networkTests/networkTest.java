package inf112.skeleton.app.networkTests;

import inf112.skeleton.app.main.Game;
import inf112.skeleton.app.networking.MPClient;
import inf112.skeleton.app.networking.MPServer;
import inf112.skeleton.app.objects.cards.CardTranslator;
import inf112.skeleton.app.objects.cards.ProgramCard;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.mock;

public class networkTest {
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
    public void serverReceivedCardsTest(){
        ProgramCard card0 = new ProgramCard(1, false, false, false);
        ProgramCard card1 = new ProgramCard(2, false, false, false);
        ProgramCard card2 = new ProgramCard(0, true, false, false);
        ProgramCard card3 = new ProgramCard(0, true, true, false);
        ProgramCard card4 = new ProgramCard(0, true, false, true);
        ProgramCard[] cards = {card0, card1, card2, card3, card4};
        client.sendCards(cards);
        try {
            //This thread sleeps so the server gets some time to receive the cards
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        for (int i = 0; i < cards.length; i++) {
            assertEquals(cards[i].getValue(), CardTranslator.intToProgramCard(server.getLastCards().get(i)).getValue());
            assertEquals(cards[i].getRotate(), CardTranslator.intToProgramCard(server.getLastCards().get(i)).getRotate());
            assertEquals(cards[i].getRotateLeft(), CardTranslator.intToProgramCard(server.getLastCards().get(i)).getRotateLeft());
            assertEquals(cards[i].getRotateRight(), CardTranslator.intToProgramCard(server.getLastCards().get(i)).getRotateRight());
        }

    }

    @Test
    public void clientReceivedCardsTest(){
        ProgramCard card0 = new ProgramCard(1, false, false, false);
        ProgramCard card1 = new ProgramCard(2, false, false, false);
        ProgramCard card2 = new ProgramCard(0, true, false, false);
        ProgramCard card3 = new ProgramCard(0, true, true, false);
        ProgramCard card4 = new ProgramCard(0, true, false, true);
        ProgramCard[] cards = {card0, card1, card2, card3, card4};
        client.sendCards(cards);
        try {
            //This thread sleeps so the server gets some time to receive the cards
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        for (int i = 0; i < cards.length; i++) {
            assertEquals(cards[i].getValue(), client.getLastCardTransfer()[i].getValue());
            assertEquals(cards[i].getRotate(), client.getLastCardTransfer()[i].getRotate());
            assertEquals(cards[i].getRotateLeft(), client.getLastCardTransfer()[i].getRotateLeft());
            assertEquals(cards[i].getRotateRight(), client.getLastCardTransfer()[i].getRotateRight());
        }
    }
}
