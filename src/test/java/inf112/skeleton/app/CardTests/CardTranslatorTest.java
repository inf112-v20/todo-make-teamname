package inf112.skeleton.app.CardTests;

import com.badlogic.gdx.graphics.Texture;
import inf112.skeleton.app.objects.cards.CardTranslator;
import inf112.skeleton.app.objects.cards.NonTextureProgramCard;
import inf112.skeleton.app.objects.cards.ProgramCard;
import org.junit.Test;

import static org.junit.Assert.*;
import static org.mockito.Mockito.mock;

public class CardTranslatorTest {

    @Test
    public void programCardsToIntCards(){
        Texture mockTexture = mock(Texture.class);
        Texture[] mockImages = {mockTexture};
        ProgramCard card0 = new ProgramCard(1, false, false, false, mockImages);
        ProgramCard card1 = new ProgramCard(2, false, false, false, mockImages);
        ProgramCard card2 = new ProgramCard(0, true, false, false, mockImages);
        ProgramCard card3 = new ProgramCard(0, true, true, false, mockImages);
        ProgramCard card4 = new ProgramCard(0, true, false, true, mockImages);
        ProgramCard[] cards = {card0, card1, card2, card3, card4};
        int[][] intCards = new int[cards.length][4];
        for (int i = 0; i < cards.length; i++) {
            intCards[i] = CardTranslator.programCardsToInt(cards[i]);
        }for (int i = 0; i < cards.length; i++) {
            assertEquals(intCards[i][0], (int) cards[i].getValue());
            assertEquals(intCards[i][1], CardTranslator.booleanToInt(cards[i].getRotate()));
            assertEquals(intCards[i][2], CardTranslator.booleanToInt(cards[i].getRotateLeft()));
            assertEquals(intCards[i][3], CardTranslator.booleanToInt(cards[i].getRotateRight()));
        }

    }
    @Test
    public void intCardToProgramCard(){
        int[] card0 = {1, 0, 0, 0};
        int[] card1 = {0, 1, 0, 0};
        int[] card2 = {0, 1, 1, 0};
        int[] card3 = {2, 0, 0, 0};
        int[] card4 = {0, 1, 0, 1};
        int[][] intCards = {card0, card1, card2, card3, card4};
        NonTextureProgramCard[] cards = new NonTextureProgramCard[intCards.length];
        for (int i = 0; i < intCards.length; i++) {
            cards[i] = CardTranslator.intToProgramCard(intCards[i]);
        }for (int i = 0; i < cards.length; i++) {
            assertEquals((int) cards[i].getValue(), intCards[i][0]);
            assertEquals(cards[i].getRotate(), CardTranslator.intToBoolean(intCards[i][1]));
            assertEquals(cards[i].getRotateLeft(), CardTranslator.intToBoolean(intCards[i][2]));
            assertEquals(cards[i].getRotateRight(), CardTranslator.intToBoolean(intCards[i][3]));
        }
    }

    @Test
    public void booleanToInt(){
        assertEquals(CardTranslator.booleanToInt(true), 1);
        assertEquals(CardTranslator.booleanToInt(false), 0);
    }

    @Test
    public void intToBoolean(){
        assertTrue(CardTranslator.intToBoolean(1));
        assertFalse(CardTranslator.intToBoolean(0));
    }
}
