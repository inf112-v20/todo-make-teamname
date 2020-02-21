package inf112.skeleton.app.objects;

public class Player {
    private Robot robot;
    public ProgramCard[] cards;

    public Player(){
        robot = new Robot();
    }

    public boolean hasWon(){return true;}
    public void deal(){}
    public void setCards(ProgramCard[] cards){this.cards = cards;}
    public ProgramCard[] getCards(){return cards;}
    public Robot getRobot(){return robot;}
}
