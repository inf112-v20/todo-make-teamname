package inf112.skeleton.app.objects.cards;

public class NonTextureProgramCard extends ProgramCard {

    private boolean rotate;
    private boolean rotateLeft;
    private boolean rotateRight;
    private int value;


    public NonTextureProgramCard(int value, boolean rotate, boolean rotateLeft, boolean rotateRight) {
        super(value, rotate, rotateLeft, rotateRight);
    }

    @Override
    public Integer getValue() {
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
}
