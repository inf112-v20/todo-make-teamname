package inf112.skeleton.app.objects.cards;

import com.badlogic.gdx.graphics.Texture;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

/**
 * CardParser grabs the card-information from cards.txt and translates that into cards
 * which can be read by the game.
 */
public class CardParser {
    static ProgramCard[] cards = new ProgramCard[0];

    /**
     * Read in card info from text file and makes them cards to use in game.
     * @return cards
     */
    public static ProgramCard[] cards() {
        try{
            Scanner scanner = new Scanner(new File("assets/cards/cards.txt"));
            scanner.useDelimiter("[-\n]");
            while (scanner.hasNext()){
                try {
                    String[] cardValue = scanner.nextLine().split(" ");
                    int valueCard = Integer.parseInt(cardValue[0]);
                    String cardName = cardValue[1];

                    ProgramCard newCard = new ProgramCard(cardName, new Texture("assets/cards/card_" + cardName + ".png"), valueCard);
                    cards = addCard(cards, newCard);
                }
                catch (NumberFormatException f){
                    f.printStackTrace();
                }
            }
        }catch (FileNotFoundException e){
            e.printStackTrace();
        }
        return cards;
    }

    /**
     * Adds a card to the card list
     * @param cards
     * @param cardToAdd
     * @return newCard
     */
    private static ProgramCard[] addCard(ProgramCard[] cards, ProgramCard cardToAdd){
        ProgramCard[] newCard = new ProgramCard[cards.length + 1];
        System.arraycopy(cards, 0, newCard, 0, cards.length);
        newCard[newCard.length - 1] = cardToAdd;
        return newCard;
    }
}
