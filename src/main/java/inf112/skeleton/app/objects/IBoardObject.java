package inf112.skeleton.app.objects;

import inf112.skeleton.app.Direction;

public interface IBoardObject {

    /**
     * The direction of the item, (WEST, NORTH...)
     * @return The direction
     */
    Direction getDirection();

    /**
     * Sets the direction of the object
     * @param d The Direction
     */
    void setDirection(Direction d);

    int getTileX();

    int getTileY();

    void setTileX(int x);

    void setTileY(int y);
}
