package inf112.skeleton.app.objects.boardObjects;

import inf112.skeleton.app.board.Direction;

public class BoardLaser implements IBoardObject {
    private int x,y;

    @Override
    public Direction getDirection() {
        return null;
    }

    @Override
    public void setDirection(Direction d) {

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
    public void setTileX(int x) {
        this.x = x;
    }

    @Override
    public void setTileY(int y) {
        this.y = y;
    }
}
