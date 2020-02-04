package inf112.skeleton.app.objects;

import inf112.skeleton.app.Direction;
import inf112.skeleton.app.IBoardObject;

public class Robot implements IBoardObject {

    private int x, y;
    private Direction dir;

    public Robot(Direction newDir) {
        dir = newDir;
    }

    @Override
    public Direction getDirection() {
        return dir;
    }

    @Override
    public void setDirection(Direction newDir) {
        dir = newDir;
    }

    @Override
    public int getTileX() {
        return x;
    }

    @Override
    public int getTileY() {
        return y;
    }

    @Override
    public void setTileX(int newX) {
        x = newX;
    }

    @Override
    public void setTileY(int newY) {
        y = newY;
    }
}
