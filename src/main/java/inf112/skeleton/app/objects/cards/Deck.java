package inf112.skeleton.app.objects.cards;

import java.util.ArrayList;
import java.util.Arrays;

public class Deck {
    private ArrayList<ProgramCard> drawPile = new ArrayList<>();
    private ArrayList<ProgramCard> discardPile = new ArrayList<>();

    public Deck(){
        ProgramCard[] newCards = CardParser.cardParser();
        drawPile.addAll(Arrays.asList(newCards));
    }

    public ProgramCard[] draw(){
        ProgramCard[] newHand = new ProgramCard[5];
        for (int i = 0; i < 5; i++) {
            if(drawPile.isEmpty()){
                drawPile = discardPile;
                discardPile.clear();
            }
            newHand[i] = drawPile.remove(0);
        }
        return newHand;
    }

    public void discard(ProgramCard[] cards){
        discardPile.addAll(Arrays.asList(cards));
    }

}

