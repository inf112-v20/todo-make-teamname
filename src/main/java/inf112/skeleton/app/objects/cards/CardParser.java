package inf112.skeleton.app.objects.cards;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Random;
import java.util.Scanner;

public class CardParser {
    static Card[] cards = new Card[0];

    public static void cards() {
        try{
            Scanner scanner = new Scanner(new File("assets/cards/cards.txt"));
            scanner.useDelimiter("[-\n]");
            while (scanner.hasNext()){
                try {
                    String[] cardValue = scanner.nextLine().split(" ");
                    int valueCard = Integer.parseInt(cardValue[0]);
                    String cardName = cardValue[1];

                    Card newCard = new Card(valueCard, cardName);
                    cards = addCard(cards, newCard);
                }
                catch (NumberFormatException f){
                    f.printStackTrace();
                }
            }
        }catch (FileNotFoundException e){
            e.printStackTrace();
        }
    }
    private static Card[] addCard(Card[] cards, Card cardToAdd){
        Card[] newCard = new Card[cards.length + 1];
        System.arraycopy(cards, 0, newCard, 0, cards.length);
        newCard[newCard.length - 1] = cardToAdd;
        return newCard;
    }

}
