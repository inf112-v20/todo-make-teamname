package inf112.skeleton.app.objects;

import com.badlogic.gdx.graphics.Texture;
import inf112.skeleton.app.Direction;

public class Robot implements IBoardObject {

    private int x, y;
    private Direction dir;
    private Texture sprite;

    public Robot(Direction newDir) {
        dir = newDir;
        sprite = new Texture("assets/Robot_Example.png");
    }

    @Override
    public Texture getTexture() {
        return sprite;
    }

    @Override
    public void setTexture() {

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