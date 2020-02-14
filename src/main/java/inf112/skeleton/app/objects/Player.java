package inf112.skeleton.app.objects;

public class Player {
    private Robot robot;

    public Player(){
        robot = new Robot();
    }

    public boolean hasWon(){return true;}
    public void deal(){}
    public ICard[] getCards(){return null;}
    public Robot getRobot(){return robot;}
}
