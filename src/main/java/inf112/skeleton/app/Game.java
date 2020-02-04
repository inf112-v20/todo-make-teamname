package inf112.skeleton.app;

import com.badlogic.gdx.ApplicationListener;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

public class Game extends com.badlogic.gdx.Game {
    private SpriteBatch batch;
    private BitmapFont font;
    private Board board;

    @Override
    public void create() {
        batch = new SpriteBatch();
        font = new BitmapFont();
        font.setColor(Color.BLACK);

        board = new Board(8, 8);
    }

    @Override
    public void pause() {

    }

    @Override
    public void resize(int i, int i1) {

    }

    //IMPORTANT
    //An object has to be initialized before being rendered
    @Override
    public void render() {
        batch.begin();
        board.render(batch);
        batch.end();
    }

    @Override
    public void resume() {

    }

    /**
     * Is called when application is destroyed
     */
    //TODO Randomly crashes when exiting application
    @Override
    public void dispose() {
        batch.dispose();
        font.dispose();
    }

    public Board getBoard(){
        return board;
    }

}
