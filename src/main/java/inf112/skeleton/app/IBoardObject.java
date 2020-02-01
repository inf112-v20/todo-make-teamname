package inf112.skeleton.app;

public interface IBoardObject {
    Direction getDirection();
    void setDirection();

    int getSize();

    int getTileX();

    int getTileY();

    int setTileX(int x);

    int setTileY(int y);
}
