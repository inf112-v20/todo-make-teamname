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
        name = cardName;
        switch (cardName){
            case "UTurn":
                rotate = true;
                text = "Turns the robot 180 degrees around";
                break;
            case "RotateLeft":
                rotate = true;
                rotateLeft = true;
                text = "Turns the robot 90 degrees to the left";
                break;
            case "RotateRight":
                rotate = true;
                rotateRight = true;
                text = "Turns the robot 90 degrees to the right";
                break;
            case "Move1":
                value = 1;

                text = "Moves the robot 1 space in the direction its facing";
                break;
            case "Move2":
                value = 2;

                text = "Moves the robot 2 spaces in the direction its facing";
                break;
            case "Move3":
                value = 3;
                text = "Moves the robot 3 spaces in the direction its facing";
                break;
            case "BackUp":
                value = -1;
                text = "Moves the robot 1 space backwards";
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
