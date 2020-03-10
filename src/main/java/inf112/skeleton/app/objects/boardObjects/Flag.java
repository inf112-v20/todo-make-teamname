package inf112.skeleton.app.objects.boardObjects;

import inf112.skeleton.app.board.Direction;

public class Flag implements IBoardObject {
    private int nr;

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

    public int getNr() {
        return nr;
    }
}