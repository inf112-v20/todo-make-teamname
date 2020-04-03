package inf112.skeleton.app.objects.cards;

import com.badlogic.gdx.graphics.Texture;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Random;
import java.util.Scanner;

public class CardParser {
    static ProgramCard[] cards = new ProgramCard[0];

    public static ProgramCard[] cardParser() {
        try{
            Scanner scanner = new Scanner(new File("assets/cards/cards.txt"));
            scanner.useDelimiter("[-\n]");
            while (scanner.hasNext()){
                try {
                    String[] cardValue = scanner.nextLine().split(" ");
                    int valueCard = Integer.parseInt(cardValue[0]);
                    String cardName = cardValue[1];

                    ProgramCard newCard = new ProgramCard( cardName, new Texture("assets/cards/card_" + cardName + ".png"), valueCard);
                    cards = addCard(cards, newCard);
                }
                catch (NumberFormatException f){
                    f.printStackTrace();
                }
            }
            randShuffle(cards);
        }catch (FileNotFoundException e){
            e.printStackTrace();
        }
        return cards;
    }
    private static ProgramCard[] addCard(ProgramCard[] cards, ProgramCard cardToAdd){
        ProgramCard[] newCard = new ProgramCard[cards.length + 1];
        System.arraycopy(cards, 0, newCard, 0, cards.length);
        newCard[newCard.length - 1] = cardToAdd;
        return newCard;
    }
    public static ProgramCard[] randShuffle(ProgramCard[] card){
        Random random = new Random();
        for (int i = 0; i < card.length; i++) {
            int randomPos = random.nextInt(card.length);
            ProgramCard temp = card[i];
            card[i] = card[randomPos];
            card[randomPos] = temp;
        }
        return card;
    }
}
