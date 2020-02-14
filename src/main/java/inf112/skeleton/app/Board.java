package inf112.skeleton.app;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import inf112.skeleton.app.objects.IBoardObject;
import inf112.skeleton.app.objects.Robot;


public class Board  {

    private BoardTile [][] grid;
    private int width, height;
    private Robot selected;

    //Object numbers:
    public final static int BOTTOMLAYER = 0; //CONVEYORBELT
    public final static int WALLLAYER = 1; //CONVEYORBELT
    public final static int ROBOT = 2;


    public Board(int width, int height) {
        this.width = width;
        this.height = height;
        grid = new BoardTile [height][width];
        for (int y=0; y < height; y++) {
            for (int x=0; x < width; x++) {
                grid[y][x] = new BoardTile();
            }
        }
        addObject(new Robot(),0,0);
    }

    public int getHeight(){return this.height;}

    //Returns a specific tile
    public BoardTile getTile(int x, int y){
        return grid[y][x];
    }

    public int getWidth(){return this.width;}

    //Adds a object at x and y coordinate
    public void addObject(IBoardObject object, int x, int y){
        grid[y][x].add(object);
        object.setTileX(x);
        object.setTileY(y);
    }

    //Moves a object a direction
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

    //Moves a object to x any coordinate
    public void moveObject(IBoardObject object, int x, int y){
        if (x > getWidth()-1 || y > getHeight()-1 || y < 0 || x < 0){
            System.out.println("You fell off the board, rebooting...");
            addObject(removeObject(object),0,0);
            selected = null;
            return;
        }
        addObject(removeObject(object), x, y);
    }

    //moves selected robot in a direction
    public void moveSelected(Direction dir){
        if (selected == null)return;
        moveObject(selected, dir);
    }

    //moves selected robot to a new location
    public void moveSelected(int x, int y){
        if (selected == null)return;
        moveObject(selected,x,y);
    }

    //Removes a specific object
    public IBoardObject removeObject(IBoardObject object){
        int x = object.getTileX();
        int y = object.getTileY();
        return grid[y][x].remove(ROBOT);
    }

    //Removes a object at the x and y coordinate
    public IBoardObject removeObject(int x, int y){
        return grid[y][x].remove(ROBOT);
    }

    public void render (SpriteBatch batch) {
        for (int y=0; y < grid.length; y++) {
            for (int x=0; x < grid[0].length; x++) {
                for (Texture t : grid[y][x].getTextures()){
                    if (t != null)batch.draw(t, x*32, y*32);
                }
            }
        }
    }

    //sets a robot as the selected robot
    public void setSelected(Robot r){
        this.selected = r;
    }





}
