package inf112.skeleton.app;

import com.badlogic.gdx.graphics.Texture;

public class BoardTile implements IBoardObject {
    private int x, y;

    private Texture sprite;

    private IBoardObject [] items = {null, null, null, null};

    public BoardTile(int x, int y) {
        this.x = x;
        this.y = y;
        sprite = new Texture("assets/Cell_Empty.png");
    }

    public Texture getSprite() {
        return sprite;
    }

    @Override
    public Direction getDirection() {
        return Direction.NORTH;
    }

    @Override
    public void setDirection() {
    }
}
