package inf112.skeleton.app;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

public class Board  {

    private BoardTile [][] grid;

    public Board(int width, int height) {
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
                batch.draw(new Texture("assets/Cell_Empty.png"), x * 32, y * 32); //Temp version
            }
        }
    }

    public void addObject(IBoardObject object, int x, int y){
        grid[y][x].add(object);
    }

    public IBoardObject removeObject(int x, int y){
        return grid[x][y].remove();
    }

    public IBoardObject removeObject(IBoardObject object){
        int x = object.getTileX();
        int y = object.getTileY();
        return grid[x][y].remove();
    }

    public void moveObject(IBoardObject object){
        //TODO Make a method that easily moves objects
    }

    public BoardTile getTile(int x, int y){
        return grid[x][y];
    }
}
