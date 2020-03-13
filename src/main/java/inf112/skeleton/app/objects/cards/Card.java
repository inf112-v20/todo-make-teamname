package inf112.skeleton.app.objects.cards;

public class Card{
    protected String cardName;
    protected int cardValue;
    public Card(int value, String name){
        cardValue = value;
        cardName = name;
    }

    public String toString(){
        return String.format("%d %s", cardValue, cardName);
    }
}
