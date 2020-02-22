package inf112.skeleton.app.objects;

import com.badlogic.gdx.graphics.Texture;

public class ProgramCard implements ICard{
    private boolean rotate;
    private boolean rotateLeft;
    private boolean rotateRight;
    private int value;
    String name;
    String text;
    Texture image;

    public ProgramCard(int value,boolean rotate, boolean rotateLeft, boolean rotateRight){
        /*Value is for moving, rotate for rotating left, right or u turn, if both left and right is false, then robot
        * will do a U-turn
         */
        this.value = value;
        this.rotate = rotate;
        this.rotateLeft = rotateLeft;
        this.rotateRight = rotateRight;
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
        return this.image;
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
