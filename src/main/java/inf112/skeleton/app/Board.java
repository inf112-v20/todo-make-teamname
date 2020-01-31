package inf112.skeleton.app;

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
                batch.draw(grid[y][x].getSprite(), x * 32, y * 32);
            }
        }
    }
}
