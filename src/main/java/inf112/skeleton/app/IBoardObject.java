package inf112.skeleton.app;

public interface IBoardObject {

    Direction getDirection();
    void setDirection(Direction d);

    int getTileX();

    int getTileY();

    void setTileX(int x);

    void setTileY(int y);
}
