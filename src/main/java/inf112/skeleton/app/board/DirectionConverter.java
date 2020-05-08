package inf112.skeleton.app.board;

public class DirectionConverter {

    /**
     * This method is used to compare two directions so that a robot going south will crash into a southwest wall
     * @param roboDirection the robots direction
     * @param wallDirection the walls direction
     * @return Returns true if the wall and the robot has an overlapping direction
     */
    public static boolean compareWallDirection(Direction roboDirection, Direction wallDirection) {
        boolean result = false;
        switch (roboDirection){
            case SOUTH:
                result = wallDirection == Direction.SOUTH || wallDirection == Direction.SOUTHWEST ||
                        wallDirection == Direction.SOUTHEAST;
                break;
            case WEST:
                result = wallDirection == Direction.WEST || wallDirection == Direction.SOUTHWEST ||
                        wallDirection == Direction.NORTHWEST;
                break;
            case NORTH:
                result = wallDirection == Direction.NORTH || wallDirection == Direction.NORTHWEST ||
                        wallDirection == Direction.NORTHEAST;
                break;
            case EAST:
                result = wallDirection == Direction.EAST || wallDirection == Direction.NORTHEAST ||
                        wallDirection == Direction.SOUTHEAST;
                break;
        }
        return result;
    }

    /**
     * Returns the opposite direction
     * @param direction A direction
     * @return The opposite direction
     */
    public static Direction oppositeDirection(Direction direction){
        switch (direction){
            case WEST:
                return Direction.EAST;
            case SOUTH:
                return Direction.NORTH;
            case EAST:
                return Direction.WEST;
            case NORTH:
                return Direction.SOUTH;
            case SOUTHWEST:
                return Direction.NORTHEAST;
            case NORTHEAST:
                return Direction.SOUTHWEST;
            case NORTHWEST:
                return Direction.SOUTHEAST;
            case SOUTHEAST:
                return Direction.NORTHWEST;
            default:
                return null;
        }
    }

    public static int[] directionToIntCoordinate(Direction direction){
        switch (direction){
            case EAST:
                return new int[]{1, 0};
            case WEST:
                return new int[]{-1, 0};
            case NORTH:
                return new int[]{0, 1};
            case SOUTH:
                return new int[]{0, -1};
            default:
                return new int[]{0, 0};
        }
    }
}
