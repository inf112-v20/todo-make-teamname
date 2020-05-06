package inf112.skeleton.app.objects.player;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.utils.Queue;
import inf112.skeleton.app.objects.boardObjects.Flag;
import inf112.skeleton.app.objects.cards.Deck;
import inf112.skeleton.app.objects.cards.ProgramCard;

import java.util.ArrayList;
import java.util.Arrays;

/**
 * The Player class simulates the player, its robot and its cards.
 */
public class Player {
    private Robot robot;
    private ProgramCard[] cards;
    private Queue<ProgramCard> selectedCards;
    private ArrayList<Flag> flags;
    private Boolean readyButton = false;
    private Deck deck;
    private ArrayList<ProgramCard> lockedCards;
    private boolean dead;
    private int id;

    /**
     * This constructor creates the player's robot and deck, also initializes some lists.
     * @param textures The list of {@link Texture} for the robot.
     */
    public Player(Texture[] textures){
        robot = new Robot(textures);
        selectedCards = new Queue<ProgramCard>();
        lockedCards = new ArrayList<>();
        flags = new ArrayList<>();
        deck = new Deck(robot);
        dead = false;
    }

    public boolean hasWon(){return true;}

    /**
     * Deals a new hand to the player.
     */
    public void deal(){
        cards = deck.draw();
    }


    public void setCards(ProgramCard[] cards){this.cards = cards;}

    /**
     *
     * @return Returns the cards in hand
     */
    public ProgramCard[] getCards(){return cards;}

    /**
     *
     * @return Returns the players robot
     */
    public Robot getRobot(){return robot;}

    public Queue<ProgramCard> getSelectedCards(){
        return selectedCards;
    }
    public void giveOptionCard() {
        //Get an option card from the common option card deck
    }

    public void addSelectedCard(int card) {
        boolean sel = false;
        //checks if the card has been selected and in that case unselects it
        for (ProgramCard selectedCard : selectedCards) {
            if (cards[card].equals(selectedCard)){
                if (selectedCard.getSelected()){
                    cards[card].setSelected(false);
                    sel = true;
                }
            }
        }
        //removes unselected cards from selectedCards
        for (int i = 0; i < selectedCards.size; i++){
            if (!selectedCards.get(i).getSelected()){
                selectedCards.removeIndex(i);
            }
        }

        if (selectedCards.size >= 5)return;
        //if it hasnt been unselected then it must be selected
        if(!sel){
            selectedCards.addLast(cards[card]);
            cards[card].setSelected(true);
        }

    }

    /**
     *
     * @return Returns the life of the player.
     */
    public int getLife(){
        return robot.getLife();
    }

    /**
     *
     * @return Returns an array of the selected cards.
     */
    public ProgramCard[] getArrayCards(){
        ProgramCard[] newHand = new ProgramCard[selectedCards.size];
        for (int i = 0; i < selectedCards.size; i++) {
            newHand[i] = selectedCards.get(i);
        }
        if(newHand.length < 5){
            ProgramCard[] newHand5 = new ProgramCard[5];
            for (int i = 0; i < newHand.length; i++) {
                newHand5[i] = newHand[i];
            }
            for (int i = 0; i < lockedCards.size(); i++) {
                newHand5[newHand.length+i] = lockedCards.get(lockedCards.size()-(i+1));
            }
            newHand = newHand5;
        }
        return newHand;
    }

    /**
     *
     * @return Returns the {@link Flag}'s the player has collected.
     */
    public ArrayList<Flag> getFlags() {
        return flags;
    }

    /**
     * Add a flag to the player.
     * @param flag said flag
     */
    public void addFlag(Flag flag){
        flags.add(flag);
    }

    public void setReadyButton(boolean b){
        this.readyButton = b;
    }
    public boolean getReadyButton(){
        return this.readyButton;
    }

    public ArrayList<ProgramCard> getLockedCards(){
        return lockedCards;
    }

    /**
     * Discard the hand of cards the player has.
     */
    public void discard() {
        for (ProgramCard card: cards)
            card.setSelected(false);
        deck.discard(cards);
        //Fixme
        //Draw directly here maybe to avoid nullpointer
        Arrays.fill(cards, null);
        if(robot.getHealth() >= 5){
            selectedCards.clear();
            lockedCards.clear();
        }else {
            for (int i = 0; i < robot.getHealth(); i++) {
                if(!selectedCards.isEmpty())selectedCards.removeFirst();
            }
            for (int i = 0; i < selectedCards.size; i++) {
                if(!selectedCards.isEmpty())lockedCards.add(selectedCards.get(i));
            }
            selectedCards.clear();
        }
    }

    public void clearLockedCards(){
        lockedCards.clear();
    }

    public void die() {
        this.dead = true;
    }

    public boolean getDead() {
        return dead;
    }

    public void setId(int id) {
        this.id = id;
        robot.setId(id);
    }

    public int getId(){
        return id;
    }
}