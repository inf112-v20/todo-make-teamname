package inf112.skeleton.app.objects.cards;

import java.util.*;

public class Deck {
    private ArrayList<ProgramCard> drawPile = new ArrayList<>();
    private ArrayList<ProgramCard> discardPile = new ArrayList<>();
    private int numOfShuffles = 5; //Default amount of shuffles

    public void deck(){
        ProgramCard[] newCards = CardParser.cards();
        drawPile.addAll(Arrays.asList(newCards));
    }

    public ProgramCard[] draw(){
        ProgramCard[] newHand = new ProgramCard[5];
        for (int i = 0; i < 5; i++) {
            if (drawPile.isEmpty()){
                drawPile = discardPile;
                discardPile.clear();

                randShuffle(drawPile);
            }
            newHand[i] = drawPile.remove(0);
        }
        return newHand;
    }

    public void discard(ProgramCard cards) {
        discardPile.addAll(Arrays.asList(cards));
    }

    public void randShuffle(ArrayList<ProgramCard> card){
        for (int i = 0; i < numOfShuffles; i++) {
            Collections.shuffle(drawPile);
        }
    }

}

