package inf112.skeleton.app.main.menuScreens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Button;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;
import com.badlogic.gdx.utils.viewport.ScalingViewport;
import com.badlogic.gdx.utils.viewport.ScreenViewport;
import inf112.skeleton.app.main.Game;
import inf112.skeleton.app.main.ScreenHandler;
import inf112.skeleton.app.main.ScreenState;
import inf112.skeleton.app.main.Settings;

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
        font.setColor(Color.BLACK);
        font.draw(batch, "IP address: " + ipAddress, Settings.SCREEN_WIDTH / 2, Settings.SCREEN_HEIGHT / 2);
        font.setColor(Color.YELLOW);
        font.draw(batch, "Go to lobby", Settings.SCREEN_WIDTH / 6 * 5, Settings.SCREEN_HEIGHT / 18);

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
