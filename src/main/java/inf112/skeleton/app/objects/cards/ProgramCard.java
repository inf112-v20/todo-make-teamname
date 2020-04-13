package inf112.skeleton.app.objects.cards;

import com.badlogic.gdx.graphics.Texture;

public class ProgramCard implements ICard {
    private boolean rotate = false;
    private boolean rotateLeft = false;
    private boolean rotateRight = false;
    private int value = 0;
    private boolean selected;
    private int priorityValue;
    String name;
    String text;

    private Texture sprite;

    public ProgramCard(String cardName, Texture sprite, int priorityValue){
        this.priorityValue = priorityValue;
        this.sprite = sprite;
        switch (cardName){
            case "UTurn":
                rotate = true;
                break;
            case "RotateLeft":
                rotate = true;
                rotateLeft = true;
                break;
            case "RotateRight":
                rotate = true;
                rotateRight = true;
                break;
            case "Move1":
                value = 1;
                break;
            case "Move2":
                value = 2;
                break;
            case "Move3":
                value = 3;
                break;
            case "BackUp":
                value = -1;
                break;
            default:

        }

    }

    public void setSelected(Boolean b){
        this.selected = b;
    }
    public boolean getSelected(){
        return this.selected;
    }
    @Override
    public String getName() {
        return this.name;
    }

    @Override
    public String getText() {
        return this.text;
    }

    @Override
    public Texture getImage() {
        return sprite;
    }

    @Override
    public Integer getValue() {
        return value;
    }

    // Flips the texture of the card between front and back
    public void flip(){

    }

    public boolean getRotateLeft() {
        return rotateLeft;
    }

    public boolean getRotateRight() {
        return rotateRight;
    }

    public boolean getRotate(){
        return rotate;
    }

    public int getPriorityValue(){
        return priorityValue;
    }
}
