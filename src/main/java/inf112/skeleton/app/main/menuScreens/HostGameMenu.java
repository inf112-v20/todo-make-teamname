package inf112.skeleton.app.main.menuScreens;

import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import inf112.skeleton.app.main.*;

import java.net.InetAddress;

/**
 * The HostGameMenu class just shows a simple screen with the IP Address and sets up a server and a client.
 */
public class HostGameMenu {
    private Game game;
    private InetAddress ipAddress;

    /**
     * The constructor sets the game i the parameter to the field variable game.
     * @param game The game that gets played.
     */
    public HostGameMenu(Game game){
        this.game = game;
    }
    /**
     * Calls to game to create a server and a client, then saves the IP Address it gets in return.
     */
    public void create(){
        ipAddress = game.hostGame();
    }

    /**
     * Renders the game with the given SpriteBatch and BitmapFont. Gives the IP Address on the screen to give to friends
     * and the possibility to go to the lobby screen
     * @param batch The batch used for the game.
     * @param font The bitmapFont used for the game.
     */
    public void render(SpriteBatch batch, BitmapFont font) {
        font.setColor(Color.WHITE);
        font.draw(batch, "IP address: " + ipAddress, (Settings.SCREEN_WIDTH / 2)-100, (Settings.SCREEN_HEIGHT / 2)+70);
        font.setColor(Color.YELLOW);
        font.draw(batch, "Press ENTER to go to lobby", (Settings.SCREEN_WIDTH / 2) -100, (Settings.SCREEN_HEIGHT / 2)-20);
    }

    /**
     * Press the Enter key to go to the lobby screen.
     */
    public void input(int keyCode) {
        if (keyCode == Input.Keys.ENTER) {
            exitScreen();
        }
    }
    private void exitScreen() {
        ScreenHandler.changeScreenState(ScreenState.LOBBYMENU);
    }
}
