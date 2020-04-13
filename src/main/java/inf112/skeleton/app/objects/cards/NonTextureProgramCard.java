package inf112.skeleton.app.objects.cards;

public class NonTextureProgramCard {

    private int priority;
    private boolean rotate;
    private boolean rotateLeft;
    private boolean rotateRight;
    private int value;


    public NonTextureProgramCard(int value, boolean rotate, boolean rotateLeft, boolean rotateRight, int priority) {
        this.value = value;
        this.rotate = rotate;
        this.rotateLeft = rotateLeft;
        this.rotateRight = rotateRight;
        this.priority = priority;
    }

    public int getValue() {
        return value;
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

    public int getPriority(){
        return priority;
    }
}
