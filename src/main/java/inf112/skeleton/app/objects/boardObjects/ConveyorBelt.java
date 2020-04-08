package inf112.skeleton.app.objects.boardObjects;

import inf112.skeleton.app.board.Direction;

public class ConveyorBelt implements IBoardObject {
    private boolean express;
    private Direction direction;
    private boolean rotate;

    public ConveyorBelt(boolean express, Direction newDir){
        //if its an express conveyor belt...
        this.express = express;
        direction = newDir;
        rotate = false;
    }
    public ConveyorBelt(boolean express, Direction newDir, boolean rotate){
        this.express = express;
        this.direction = newDir;
        this.rotate = rotate;
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

    /**
     * Says if the conveyorbelt is an express-conveyorbelt or not
     * @return True if express, else False
     */
    public boolean getExpress(){
        return express;
    }

    public boolean canRotate() {
        return rotate;
    }
}
