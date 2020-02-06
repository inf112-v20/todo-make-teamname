package inf112.skeleton.app;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import inf112.skeleton.app.objects.Robot;

public class Board  {

    private BoardTile [][] grid;
    private int width, height;
    private Robot selected;

    public Board(int width, int height) {
        this.width = width;
        this.height = height;
        grid = new BoardTile [width][height];
        for (int y=0; y < width; y++) {
            for (int x=0; x < height; x++) {
                grid[y][x] = new BoardTile(x, y);
            }
        }
        addObject(new Robot(Direction.NORTH),0,0);
    }
    public int getWidth(){return this.width;}
    public int getHeight(){return this.height;}

    public void render (SpriteBatch batch) {
        for (int y=0; y < grid.length; y++) {
            for (int x=0; x < grid[0].length; x++) {
                for (Texture t : grid[y][x].getTextures()){
                    if (t != null)batch.draw(t, x*32, y*32);
                }
            }
        }
    }
    public void moveSelectedDir(Direction dir){
        if (selected == null)return;
        moveObjectDir(selected, dir);
    }
    public void moveSelected(int x, int y){
        if (selected == null)return;
        moveObject(selected,x,y);
    }

    public void setSelected(Robot r){
        this.selected = r;
    }

    public void addObject(IBoardObject object, int x, int y){
        if (object == null)return;
        if (x >= height || y >= width || x < 0 || y < 0) return;
        grid[y][x].add(object);
        object.setTileX(x);
        object.setTileY(y);
    }
    public void moveObjectDir(IBoardObject object, Direction direction){
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
        }
    }

    public IBoardObject removeObject(int x, int y){
        return grid[y][x].remove();
    }

    public IBoardObject removeObject(IBoardObject object){
        int x = object.getTileX();
        int y = object.getTileY();
        return grid[y][x].remove();
    }

    public void moveObject(IBoardObject object, int x, int y){
        if (x > getWidth()-1 || y > getHeight()-1 || y < 0 || x < 0){
            System.out.println("You fell off the board, rebooting...");
            addObject(removeObject(object),0,0);
            selected = null;
            return;
        }
        addObject(removeObject(object), x, y);
    }

    public BoardTile getTile(int x, int y){
        return grid[y][x];
    }

}
