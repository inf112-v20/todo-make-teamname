package inf112.skeleton.app.objects.boardObjects;

import inf112.skeleton.app.board.Direction;

public class Wall implements IBoardObject {
    private Direction direction;
    private int x, y;

    public Wall(Direction dir){
        direction = dir;
    }

    @Override
    public Direction getDirection() {
        return direction;
    }

    @Override
    public void setDirection(Direction d) {
        direction = d;
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
