package inf112.skeleton.app.objects.cards;

import java.util.ArrayList;
import java.util.Random;

public class Deck {
    private Card[] cards;
    private int nextCard = 0;
    private int deckSize = 84;
    private int shuffles = 5;

    /**
     * Creates new Deck
     */
    public Deck(){
        this.cards = new Card[deckSize];
        for (int i = 0; i < shuffles; i++) {
            shuffle(cards);
        }
    }

    public static Card[] shuffle(Card[] card){
        Random random = new Random();
        for (int i = 0; i < card.length; i++) {
            int randomPos = random.nextInt(card.length);
            Card temp = card[i];
            card[i] = card[randomPos];
            card[randomPos] = temp;
        }
        return card;
    }
}
