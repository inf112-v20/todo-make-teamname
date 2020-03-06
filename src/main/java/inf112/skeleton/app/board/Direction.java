package inf112.skeleton.app.board;

import java.util.Arrays;
import java.util.List;

public enum Direction {
    NORTH,
    WEST,
    EAST,
    SOUTH;

    /**
     * The four directions as a list
     */
    public static final List<Direction> four_directions = Arrays.asList(NORTH, WEST, EAST, SOUTH);

}


