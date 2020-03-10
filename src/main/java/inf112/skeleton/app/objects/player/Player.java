package inf112.skeleton.app.objects.player;

import com.badlogic.gdx.utils.Queue;
import inf112.skeleton.app.objects.boardObjects.Flag;
import inf112.skeleton.app.objects.cards.ProgramCard;

import java.util.ArrayList;

public class Player {
    private Robot robot;
    public ProgramCard[] cards;
    private Queue<ProgramCard> selectedCards;
    private ArrayList<Flag> flags;

    public Player(){
        robot = new Robot();
        robot.setTexture();
        robot.setTileX(0);
        robot.setTileY(0);
        selectedCards  = new Queue<ProgramCard>();
    }

    public boolean hasWon(){return true;}
    public void deal(){
        //TODO:: change so that players get random cards
        cards = new ProgramCard[5];
        cards[0] = (new ProgramCard(1,false,false,false));

        cards[1] = (new ProgramCard(1,false,false,false));
        cards[2]= (new ProgramCard(2,false,false,false));
        cards[3] = (new ProgramCard(0,true,true,false));
        cards[4] = (new ProgramCard(0,true,false,true));
    }
    public void setCards(ProgramCard[] cards){this.cards = cards;}
    public ProgramCard[] getCards(){return cards;}
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
        //if it hasnt been unselected then it must be selected
        if(!sel){
            selectedCards.addLast(cards[card]);
            cards[card].setSelected(true);
        }

    }

    public int getLife(){
        return robot.getLife();
    }

    public Queue<ProgramCard> copySelected() {
        Queue<ProgramCard> newHand = new Queue<>();
        for (ProgramCard card : selectedCards) {
            newHand.addLast(card);
        }
        return newHand;
    }

    public ArrayList<Flag> getFlags() {
        return flags;
    }
    public void addFlag(Flag flag){
        flags.add(flag);
    }
}
