package inf112.skeleton.app.objects.boardObjects;

import inf112.skeleton.app.board.Direction;

public class Wall implements IBoardObject {
    private Direction direction;
    String tileX;
    String tileY;

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
