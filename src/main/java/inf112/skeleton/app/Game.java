package inf112.skeleton.app;

import com.badlogic.gdx.ApplicationListener;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.InputAdapter;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import inf112.skeleton.app.objects.Robot;

public class Game extends InputAdapter implements ApplicationListener {
    private SpriteBatch batch;
    private BitmapFont font;
    private Board board;
    private Robot testBot;

    @Override
    public void create() {
        batch = new SpriteBatch();
        font = new BitmapFont();
        font.setColor(Color.BLACK);
        board = new Board(8, 8);
        testBot = new Robot(Direction.NORTH, 0,0);
        board.addObject(testBot, 0,0);
        Gdx.input.setInputProcessor(this);

    }

    @Override
    public boolean keyUp(int keycode){
        System.out.println(keycode);
        switch (keycode){
            case Input.Keys.UP:
                board.moveObjectDir(testBot, Direction.NORTH);
                break;
            case Input.Keys.DOWN:
                board.moveObjectDir(testBot, Direction.SOUTH);
                break;
            case Input.Keys.LEFT:
                board.moveObjectDir(testBot, Direction.WEST);
                break;
            case Input.Keys.RIGHT:
                board.moveObjectDir(testBot, Direction.EAST);
                break;
            default:
                break;
        }
        return true;
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
