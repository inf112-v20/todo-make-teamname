package inf112.skeleton.app;

import com.badlogic.gdx.graphics.Texture;

public interface IBoardObject {

    Texture getTexture();
    void setTexture();
    Direction getDirection();
    void setDirection(Direction d);

    int getTileX();

    int getTileY();

    void setTileX(int x);

    void setTileY(int y);
}
