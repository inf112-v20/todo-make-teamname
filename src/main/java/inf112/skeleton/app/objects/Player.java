package inf112.skeleton.app.objects;

import inf112.skeleton.app.Settings;

public class Player {
    private Robot robot;
    public ProgramCard[] cards;

    public Player(){
        robot = new Robot();
        robot.setTexture();
        robot.setTileX(0);
        robot.setTileY(0);
    }

    public boolean hasWon(){return true;}
    public void deal(){
        //TODO:: change so that players get random cards
        cards = new ProgramCard[4];
        cards[0] = (new ProgramCard(1,false,false,false));
        cards[1]= (new ProgramCard(2,false,false,false));
        cards[2] = (new ProgramCard(0,true,true,false));
        cards[3] = (new ProgramCard(1,true,false,true));
    }
    public void setCards(ProgramCard[] cards){this.cards = cards;}
    public ProgramCard[] getCards(){return cards;}
    public Robot getRobot(){return robot;}

    public void giveOptionCard() {
        //Get an option card from the common option card deck
    }
}
