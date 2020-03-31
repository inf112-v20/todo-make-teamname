package inf112.skeleton.app.objects.cards;

import com.badlogic.gdx.graphics.Texture;

public class ProgramCard implements ICard {
    private boolean rotate = false;
    private boolean rotateLeft = false;
    private boolean rotateRight = false;
    private int value = 0;
    private boolean selected;
    private Texture sprite;
    private int priorityValue;
    String name;
    String text;


    Texture [] images;

    public void setImages(){
        images = new Texture[]{
                new Texture("assets/cards/card_move_1.png"),
                new Texture("assets/cards/card_move_2.png"),
                new Texture("assets/cards/card_turn_left.png"),
                new Texture("assets/cards/card_turn_right.png")};
    }

    private int type;

    public ProgramCard(int value, boolean rotate, boolean rotateLeft, boolean rotateRight){
        /*Value is for moving, rotate for rotating left, right or u turn, if both left and right is false, then robot
        * will do a U-turn
         */
        this.selected = false;
        this.value = value;
        this.rotate = rotate;
        this.rotateLeft = rotateLeft;
        this.rotateRight = rotateRight;
    }

    public ProgramCard (Texture sprite, int priorityValue, String cardName) {
        this.sprite = sprite;
        this.priorityValue = priorityValue;

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
            case "BackUp":
                value = -1;
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
}
