package inf112.skeleton.app.objects.cards;

import inf112.skeleton.app.objects.player.Robot;
import java.util.*;

/**
 * Deck class contains the group of cards given to the players
 */
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

    /**
     * Gives the player a new hand of random cards based on their robot's health.
     * @return Hand of cards
     */
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

    /**
     * Add the cards to the discard pile.
     * @param cards
     */
    public void discard(ProgramCard[] cards) {
        discardPile.addAll(Arrays.asList(cards));
    }

    /**
     * Shuffles the draw pile of cards
     */
    public void randShuffle() {
        for (int i = 0; i < numOfShuffles; i++) {
            Collections.shuffle(drawPile);
        }
    }
}

