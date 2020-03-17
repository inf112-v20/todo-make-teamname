package inf112.skeleton.app.main.menuScreens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import inf112.skeleton.app.main.Game;
import inf112.skeleton.app.main.ScreenHandler;
import inf112.skeleton.app.main.ScreenState;
import inf112.skeleton.app.main.Settings;

import java.net.InetAddress;

public class HostGameMenu {
    private Game game;
    private InetAddress ipAddress;

    public HostGameMenu(Game game){
        this.game = game;
    }
    public void create(){
        ipAddress = game.hostGame();
    }
    public void render(SpriteBatch batch, BitmapFont font) {
        font.setColor(Color.BLACK);
        font.draw(batch, "IP address: " + ipAddress, Settings.SCREEN_WIDTH / 2, Settings.SCREEN_HEIGHT / 2);
        font.setColor(Color.YELLOW);
        font.draw(batch, "Go to lobby", Settings.SCREEN_WIDTH / 6 * 5, Settings.SCREEN_HEIGHT / 18);
        input();
    }
    public void input() {
        if (Gdx.input.isKeyJustPressed(Input.Keys.ENTER)) {
            ScreenHandler.changeScreenState(ScreenState.LOBBYMENU);
        }
    }
}
