package inf112.skeleton.app.objects.boardObjects;

import inf112.skeleton.app.board.Direction;

public class ConveyorBelt implements IBoardObject {
    private boolean express;
    private Direction direction;
    private String rotate;
    private int x,y;

    public ConveyorBelt(boolean express, Direction newDir){
        //if its an express conveyor belt...
        this.express = express;
        direction = newDir;
        rotate = "";
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

    /**
     * Says if the conveyorbelt is an express-conveyorbelt or not
     * @return True if express, else False
     */
    public boolean getExpress(){
        return express;
    }

    public String canRotate() {
        return rotate;
    }
}
