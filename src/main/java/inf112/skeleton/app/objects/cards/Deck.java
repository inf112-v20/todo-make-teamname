package inf112.skeleton.app.objects.cards;

import inf112.skeleton.app.objects.player.Robot;
import java.util.*;

public class Deck {
    private ArrayList<ProgramCard> drawPile = new ArrayList<>();
    private ArrayList<ProgramCard> discardPile = new ArrayList<>();
    private int numOfShuffles = 5; //Default amount of shuffles
    private Robot robot;

    public Deck(Robot robot) {
        ProgramCard[] newCards = CardParser.cards();
        drawPile.addAll(Arrays.asList(newCards));
        randShuffle();
        this.robot = robot;
    }

    public ProgramCard[] draw() {
        ProgramCard[] newHand = new ProgramCard[robot.getHealth()];
        for (int i = 0; i < robot.getHealth(); i++) {
            if (drawPile.isEmpty()) {
                drawPile = (ArrayList<ProgramCard>) discardPile.clone();
                discardPile.clear();

                randShuffle();
            }
            newHand[i] = drawPile.remove(0);
        }
        return newHand;
    }

    public void discard(ProgramCard[] cards) {
        discardPile.addAll(Arrays.asList(cards));
    }

    public void randShuffle() {
        for (int i = 0; i < numOfShuffles; i++) {
            Collections.shuffle(drawPile);
        }
    }

}

