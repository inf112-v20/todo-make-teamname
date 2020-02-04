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
    public void setDirection(Direction newDir) {

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
    public void setTileX(int x) {
    }

    @Override
    public void setTileY(int y) {
    }
}
