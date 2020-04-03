package inf112.skeleton.app.main.menuScreens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import inf112.skeleton.app.main.Game;
import inf112.skeleton.app.main.Settings;

import java.util.ArrayList;

/**
 * A simple lobby that ask for the user name of the player, then shows all the connected players. When the host presses
 * the Enter key all players go to the game screen.
 */
public class LobbyMenu {
    private boolean host = false;
    private Game game;

    /**
     * The constructor sets the game i the parameter to the field variable game.
     * @param game The game that gets played.
     */
    public LobbyMenu(Game game){
        this.game = game;
    }

    /**
     * This method sets if a player is a host, it gives that player the ability to start the game.
     * @param host This parameter is true if the player calling it is a host.
     */
    public void setHost(boolean host) {
        this.host = host;
    }

    /**
     * Creates an TextInput prompt where the player can enter his/her username. Gets called when the screen changes
     * to the lobby menu.
     */
    public void create(){
        Gdx.input.getTextInput(new Input.TextInputListener() {
            @Override
            public void input (String text) {
                game.sendName(text);
            }

            @Override
            public void canceled () {
            }
        }, "Enter UserName:", "", "");
    }

    /**
     * Renders the game with the given SpriteBatch and BitmapFont. Also calls input if the player is a host.
     * @param batch The batch used for the game.
     * @param font The bitmapFont used for the game.
     */
    public void render(SpriteBatch batch, BitmapFont font) {
        String[] names = game.getNames();
        font.setColor(Color.WHITE);
        font.draw(batch, "Players joined:", Settings.SCREEN_WIDTH / 8, (Settings.SCREEN_HEIGHT / 18) * 15);
        for (int i = 0; i < names.length; i++) {
            if(names[i] == null) continue;
            font.draw(batch, names[i] + " Player number: " + i, Settings.SCREEN_WIDTH / 8, (Settings.SCREEN_HEIGHT / 18) * (15 - i));
        }

    }

    /**
     * Gives the host the ability to start the game.
     */
    public void input(int keyCode) {
        if(host) {
            if (keyCode == Input.Keys.ENTER) {
                game.sendStartSignal();
            }
        }
    }
}
