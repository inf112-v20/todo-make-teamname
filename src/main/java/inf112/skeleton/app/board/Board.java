package inf112.skeleton.app.board;

import inf112.skeleton.app.objects.boardObjects.IBoardObject;
import inf112.skeleton.app.objects.player.Robot;

import java.util.ArrayList;

/**
 * The Board class is used to represent the board that is being played on,<BR>
 * it uses a double array of {@link BoardTile}'s to represent it.
 */
public class Board  {

    private final int flags;
    private BoardTile [][] grid;
    private int width, height;
    private ArrayList<Robot> robots;
    private Robot selected;

    //Object numbers:
    public final static int CONVEYORBELT = 0; //CONVEYORBELT
    public final static int WALLLAYER = 1; //CONVEYORBELT
    public final static int ROBOT = 2;

    /**
     * Creates the board according to parameters, fills the board with {@link BoardTile}'s.
     * @param width Width of the board.
     * @param height Height of the board.
     * @param flags Number of flags on the board.
     */
    public Board(int width, int height, int flags) {
        this.robots = new ArrayList<Robot>();
        this.width = width;
        this.height = height;
        this.flags = flags;
        grid = new BoardTile [height][width];
        for (int y=0; y < height; y++) {
            for (int x=0; x < width; x++) {
                grid[y][x] = new BoardTile(x, y);
            }
        }
    }

    public BoardTile[][] getGrid(){
        return grid;
    }

    /**
     *
     * @return Returns the height of the board
     */
    public int getHeight(){return this.height;}

    /**
     * @return Returns a specific tile
     */
    public BoardTile getTile(int x, int y){
        return grid[y][x];
    }

    /**
     *
     * @return Returns the width of the board
     */
    public int getWidth(){return this.width;}

    /**
     * Adds a object at x and y coordinate
     */
    public void addObject(IBoardObject object, int x, int y){
        if (object instanceof Robot)robots.add((Robot)object);
        grid[y][x].add(object);
        object.setTileX(x);
        object.setTileY(y);
    }

    /**
     * Moves an object a direction
     */
    public void moveObject(IBoardObject object, Direction direction){
        switch (direction){
            case NORTH:
                moveObject(object, object.getTileX(), object.getTileY()+1);
                break;
            case EAST:
                moveObject(object, object.getTileX()+1, object.getTileY());
                break;
            case SOUTH:
                moveObject(object, object.getTileX(), object.getTileY()-1);
                break;
            case WEST:
                moveObject(object, object.getTileX()-1, object.getTileY());
                break;
            default:
                System.out.println("Error");
                break;
        }
    }

    /**
     * Moves an object to x any coordinate
     */
    public void moveObject(IBoardObject object, int x, int y){
        if (x > getWidth()-1 || y > getHeight()-1 || y < 0 || x < 0){
            System.out.println("You fell off the board, rebooting...");
            removeObject(object);
            Robot robot = (Robot) object;
            robot.destroy();
            selected = null;
            return;
        }
        addObject(removeObject(object), x, y);
    }

    /**
     * Moves selected robot in a direction
     */
    public void moveSelected(Direction dir){
        if (selected == null)return;
        moveObject(selected, dir);
    }

    //moves selected robot to a new location
    public void moveSelected(int x, int y){
        if (selected == null)return;
        moveObject(selected,x,y);
    }

    /**
     * Removes a specific object
     */
    public IBoardObject removeObject(IBoardObject object){
        int x = object.getTileX();
        int y = object.getTileY();
        if(x == -1 || y == -1) return null;
        return grid[y][x].remove(ROBOT);
    }

    /**
     * Removes a object at the x and y coordinate
     */
    public IBoardObject removeObject(int x, int y){
        if(x == -1 || y == -1) return null;
        return grid[y][x].remove(ROBOT);
    }


    //sets a robot as the selected robot
    //Keep for later use when placing robots
    public void setSelected(Robot r){
        this.selected = r;
    }

    /**
     *
     * @return Returns all robots on the board.
     */
    public ArrayList<Robot> getRobots() {
        return robots;
    }

    /**
     *
     * @return Returns the number of flags on the board.
     */
    public int getFlagNr() {
        return flags;
    }
}
