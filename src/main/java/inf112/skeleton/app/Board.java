package inf112.skeleton.app;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import inf112.skeleton.app.objects.IBoardObject;

public class Board  {

    private BoardTile [][] grid;
    private int width, height;

    //Object numbers:
    public final static int BOTTOMLAYER = 0; //CONVEYORBELT
    public final static int WALLLAYER = 1; //CONVEYORBELT
    public final static int ROBOT = 2;


    public Board(int width, int height) {
        this.width = width;
        this.height = height;
        grid = new BoardTile [width][height];
        for (int y=0; y < width; y++) {
            for (int x=0; x < height; x++) {
                grid[y][x] = new BoardTile(x, y);
            }
        }
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

    //Adds a object at x and y coordinate
    public void addObject(IBoardObject object, int x, int y){
        if (x >= height || y >= width || x < 0 || y < 0){
            System.out.println("You fell off the board");
            removeObject(object);
            return;
        }
        grid[y][x].add(object);
        object.setTileX(x);
        object.setTileY(y);
    }

    //Moves a object a direction
    public void moveObjectDir(IBoardObject object, Direction direction){
        switch (direction){
            case NORTH:
                addObject(removeObject(object), object.getTileX(), object.getTileY()+1);
                break;
            case EAST:
                addObject(removeObject(object), object.getTileX()+1, object.getTileY());
                break;
            case SOUTH:
                addObject(removeObject(object), object.getTileX(), object.getTileY()-1);
                break;
            case WEST:
                addObject(removeObject(object), object.getTileX()-1, object.getTileY());
                break;
        }
    }

    //Moves a object to x any coordinate
    public void moveObject( int x, int y){
        addObject( grid[y][x].remove(ROBOT), x, y);
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

    //Returns a specific tile
    public BoardTile getTile(int x, int y){
        return grid[x][y];
    }

}
