package inf112.skeleton.app.objects.boardObjects;

import inf112.skeleton.app.board.Direction;

public class Flag implements IBoardObject {
    private int nr;
    private int x,y;

    public Flag(int nr){
        this.nr = nr;
    }

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

    public int getNr() {
        return nr;
    }
}
