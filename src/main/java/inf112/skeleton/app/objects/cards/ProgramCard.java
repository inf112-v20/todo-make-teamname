package inf112.skeleton.app.objects.cards;

import com.badlogic.gdx.graphics.Texture;

public class ProgramCard implements ICard {
    private boolean rotate;
    private boolean rotateLeft;
    private boolean rotateRight;
    private int value;
    private boolean selected;
    private boolean backup;
    private boolean move1;
    private boolean move2;
    private boolean move3;
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

    public ProgramCard(int value,boolean rotate, boolean rotateLeft, boolean rotateRight, boolean backup, boolean move1, boolean move2, boolean move3){
        /*Value is for moving, rotate for rotating left, right or u turn, if both left and right is false, then robot
        * will do a U-turn
         */
        this.selected = false;
        this.value = value;
        this.rotate = rotate;
        this.rotateLeft = rotateLeft;
        this.rotateRight = rotateRight;
        this.backup = backup;
        this.move1 = move1;
        this.move2 = move2;
        this.move3 = move3;

        if (value < 70){
            type = 0;
        }
        else if (value < 410 && value % 20 != 0){
            type = 1;
        }
        else if (value < 430 && value % 20 == 0){
            type = 3;
        }
        else if (value < 490){
            type = 4;
        }
        else if (value < 670){
            type = 5;
        }
        else if (value < 790){
            type = 6;
        }
        else{
            type = 7;
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
        switch (type) {
            case 6:
                return images[1];
            case 1:
                return images[2];
            case 2:
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
