package inf112.skeleton.app.objects.boardObjects;

import inf112.skeleton.app.board.Direction;

public class RepairSite implements IBoardObject {
    private boolean hammer;
    private int x ,y;

    public RepairSite(boolean hammer){
        //if it is a repair site with wrench and hammer, hammer = true, if not hammer = false.
        this.hammer = hammer;
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

    public boolean getHammer() {
        return hammer;
    }
}
