package inf112.skeleton.app.main.menuScreens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import inf112.skeleton.app.main.Game;
import inf112.skeleton.app.main.Settings;

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
        font.draw(batch, "Players joined:", (Settings.SCREEN_WIDTH / 2)-50, (Settings.SCREEN_HEIGHT / 2) + 50);
        for (int i = 0; i < names.length; i++) {
            if(names[i] == null) continue;
            font.draw(batch,  " Player " + i + " : " + names[i], (Settings.SCREEN_WIDTH / 2)-30, (Settings.SCREEN_HEIGHT / 2) - 15*i);
        }
        font.setColor(Color.YELLOW);
        font.draw(batch, "Press ENTER to start the game", (Settings.SCREEN_WIDTH / 2) -100, (Settings.SCREEN_HEIGHT / 2)-200);
    }

    /**
     * Gives the host the ability to start the game.
     */
    public void input(int keyCode) {
        if(host) {
            if (keyCode == Input.Keys.ENTER) {
                if (game.getAllReady() == null){
                    game.sendStartSignal();
                    return;
                }
                for (int i = 2; i <= game.getNrOfPlayers(); i++) {
                    if (!game.getAllReady()[i]) return;
                }
                game.sendStartSignal();
            }
        }
        else {
            if (keyCode == Input.Keys.ENTER) {
                game.sendReadySignal();
            }
        }
    }
}
