package inf112.skeleton.app.main.menuScreens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import inf112.skeleton.app.main.Game;
import inf112.skeleton.app.main.ScreenHandler;
import inf112.skeleton.app.main.ScreenState;

public class JoinGameMenu {
    private Game game;

    public JoinGameMenu(Game game){
        this.game = game;

    }

    public void render(SpriteBatch batch, BitmapFont font) {

    }
    public void create() {
        Gdx.input.getTextInput(new Input.TextInputListener() {
            @Override
            public void input (String text) {
                game.joinGame(text);
                ScreenHandler.changeScreenState(ScreenState.LOBBYMENU);
            }

            @Override
            public void canceled () {
            }
        }, "Enter IP:", "", "");
    }
}
