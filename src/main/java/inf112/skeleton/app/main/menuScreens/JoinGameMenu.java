package inf112.skeleton.app.main.menuScreens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import inf112.skeleton.app.main.Game;
import inf112.skeleton.app.main.ScreenHandler;
import inf112.skeleton.app.main.ScreenState;

/**
 * The JoinGameMenu class gives a prompt to enter an IP Address and the tries to connect to a server at that IP.
 */
public class JoinGameMenu {
    private Game game;

    /**
     * The constructor sets the game i the parameter to the field variable game.
     * @param game The game that gets played.
     */
    public JoinGameMenu(Game game){
        this.game = game;

    }

    /**
     * Just gives a pink background screen for now
     * @param batch The batch used for the game.
     * @param font The bitmapFont used for the game.
     */
    public void render(SpriteBatch batch, BitmapFont font) {

    }

    /**
     * Creates an TextInput prompt to enter the IP Address of the server.
     */
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
