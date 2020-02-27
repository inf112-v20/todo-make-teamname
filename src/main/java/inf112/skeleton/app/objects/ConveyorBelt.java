package inf112.skeleton.app.objects;

import com.badlogic.gdx.graphics.Texture;
import inf112.skeleton.app.Direction;

public class ConveyorBelt implements IBoardObject{
    private boolean express;
    private Direction direction;

    public ConveyorBelt(boolean express, Direction newDir){
        //if its an express conveyor belt...
        this.express = express;
        direction = newDir;
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


    public boolean getExpress(){
        return express;
    }
}
