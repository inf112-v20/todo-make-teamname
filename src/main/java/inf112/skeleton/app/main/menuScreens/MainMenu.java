package inf112.skeleton.app.main.menuScreens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import inf112.skeleton.app.main.ScreenHandler;
import inf112.skeleton.app.main.ScreenState;
import inf112.skeleton.app.main.Settings;

public class MainMenu {
    private static int selected = 0;

    public static void render(SpriteBatch batch, BitmapFont font) {
        switch(selected) {
            case 0:
                font.setColor(Color.BLACK);
                font.draw(batch, "Join Game", Settings.SCREEN_WIDTH / 2, Settings.SCREEN_HEIGHT / 2);
                font.draw(batch, "Quit", Settings.SCREEN_WIDTH / 2, (Settings.SCREEN_HEIGHT / 18) * 8);
                font.setColor(Color.YELLOW);
                font.draw(batch, "Host Game", Settings.SCREEN_WIDTH / 2, (Settings.SCREEN_HEIGHT / 18) * 10);
                break;
            case 1:
                font.setColor(Color.BLACK);
                font.draw(batch, "Host Game", Settings.SCREEN_WIDTH / 2, (Settings.SCREEN_HEIGHT / 18) * 10);
                font.draw(batch, "Quit", Settings.SCREEN_WIDTH / 2, (Settings.SCREEN_HEIGHT / 18) * 8);
                font.setColor(Color.YELLOW);
                font.draw(batch, "Join Game", Settings.SCREEN_WIDTH / 2, Settings.SCREEN_HEIGHT / 2);
                break;
            case 2:
                font.setColor(Color.BLACK);
                font.draw(batch, "Host Game", Settings.SCREEN_WIDTH / 2, (Settings.SCREEN_HEIGHT / 18) * 10);
                font.draw(batch, "Join Game", Settings.SCREEN_WIDTH / 2, Settings.SCREEN_HEIGHT / 2);
                font.setColor(Color.YELLOW);
                font.draw(batch, "Quit", Settings.SCREEN_WIDTH / 2, (Settings.SCREEN_HEIGHT / 18) * 8);
                break;
            default:
                font.setColor(Color.BLACK);
                font.draw(batch, "Host Game", Settings.SCREEN_WIDTH / 2, (Settings.SCREEN_HEIGHT / 18) * 10);
                font.draw(batch, "Join Game", Settings.SCREEN_WIDTH / 2, Settings.SCREEN_HEIGHT / 2);
                font.draw(batch, "Quit", Settings.SCREEN_WIDTH / 2, (Settings.SCREEN_HEIGHT / 18) * 8);
        }
    }

    public static void input(int keyCode) {
        switch (keyCode){
            case Input.Keys.UP:
                selected = selected == 0 ? 2 : selected - 1;
                break;
            case Input.Keys.DOWN:
                selected = selected == 2 ? 0 : selected + 1;
                break;
            case Input.Keys.ENTER:
                if (selected == 0) {
                    ScreenHandler.changeScreenState(ScreenState.HOSTGAME);
                }
                else if (selected == 1) {
                    ScreenHandler.changeScreenState(ScreenState.JOINGAME);
                }
                else {
                    Gdx.app.exit();
                }
            default:
                break;
        }
    }
}