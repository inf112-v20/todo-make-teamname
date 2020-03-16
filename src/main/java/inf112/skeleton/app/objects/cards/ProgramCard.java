package inf112.skeleton.app.objects.cards;

import com.badlogic.gdx.graphics.Texture;
import inf112.skeleton.app.objects.cards.ICard;

public class ProgramCard implements ICard {
    private boolean rotate;
    private boolean rotateLeft;
    private boolean rotateRight;
    private int value;
    private boolean selected;
    String name;
    String text;


    Texture [] images;

    public void setImages(){
        images = new Texture[]{new Texture("assets/cards/card_move_1.png"),
                new Texture("assets/cards/card_move_2.png"),
                new Texture("assets/cards/card_turn_left.png"),
                new Texture("assets/cards/card_turn_right.png")};
    }

    private int type;

    public ProgramCard(int value,boolean rotate, boolean rotateLeft, boolean rotateRight){
        /*Value is for moving, rotate for rotating left, right or u turn, if both left and right is false, then robot
        * will do a U-turn
         */
        this.selected = false;
        this.value = value;
        this.rotate = rotate;
        this.rotateLeft = rotateLeft;
        this.rotateRight = rotateRight;

        type = value * 1000 + (rotate ? 100 : 0) + (rotateLeft ? 10 : 0) + (rotateRight ? 1 : 0);
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
        switch (type) {
            case 1000:
                return images[0];
            case 2000:
                return images[1];
            case 110:
                return images[2];
            case 101:
                return images[3];
            default:
                return images[0];
        }
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
