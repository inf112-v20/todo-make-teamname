package inf112.skeleton.app;

public class TempBoardObject implements IBoardObject{
    //Temporary BoardObject for testing

    private int size;

    public TempBoardObject(int size){
        this.size = size;
    }

    @Override
    public Direction getDirection() {
        return null;
    }

    @Override
    public void setDirection() {

    }

    @Override
    public int getSize() {
        return size;
    }

    @Override
    public int getTileX() {
        return 0;
    }

    @Override
    public int getTileY() {
        return 0;
    }

    @Override
    public int setTileX(int x) {
        return 0;
    }

    @Override
    public int setTileY(int y) {
        return 0;
    }
}
