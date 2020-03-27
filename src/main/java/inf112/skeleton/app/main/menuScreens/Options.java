package inf112.skeleton.app.main.menuScreens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import inf112.skeleton.app.main.ScreenHandler;
import inf112.skeleton.app.main.ScreenState;
import inf112.skeleton.app.main.Settings;

import java.util.Set;

public class Options {
    private static int selected = 0;

    public static void render(SpriteBatch batch, BitmapFont font) {
        switch(selected) {
            case 0:
                font.setColor(Color.BLACK);
                font.draw(batch, "1920x1080", Settings.SCREEN_WIDTH / 2, Settings.SCREEN_HEIGHT / 2);
                font.draw(batch, "1600x900", Settings.SCREEN_WIDTH / 2, (Settings.SCREEN_HEIGHT / 18) * 6);
                font.setColor(Color.YELLOW);
                font.draw(batch, "1280x720", Settings.SCREEN_WIDTH / 2, (Settings.SCREEN_HEIGHT / 18) * 10);
                break;
            case 1:
                font.setColor(Color.BLACK);
                font.draw(batch, "1280x720", Settings.SCREEN_WIDTH / 2, (Settings.SCREEN_HEIGHT / 18) * 10);
                font.draw(batch, "1600x900", Settings.SCREEN_WIDTH / 2, (Settings.SCREEN_HEIGHT / 18) * 6);
                font.setColor(Color.YELLOW);
                font.draw(batch, "1920x1080", Settings.SCREEN_WIDTH / 2, Settings.SCREEN_HEIGHT / 2);
                break;
            case 2:
                font.setColor(Color.BLACK);
                font.draw(batch, "1280x720", Settings.SCREEN_WIDTH / 2, (Settings.SCREEN_HEIGHT / 18) * 10);
                font.draw(batch, "1920x1080", Settings.SCREEN_WIDTH / 2, Settings.SCREEN_HEIGHT / 2);
                font.setColor(Color.YELLOW);
                font.draw(batch, "1600x900", Settings.SCREEN_WIDTH / 2, (Settings.SCREEN_HEIGHT / 18) * 6);
                break;
            default:
                font.setColor(Color.BLACK);
                font.draw(batch, "1280x720", Settings.SCREEN_WIDTH / 2, (Settings.SCREEN_HEIGHT / 18) * 10);
                font.draw(batch, "1920x1080", Settings.SCREEN_WIDTH / 2, Settings.SCREEN_HEIGHT / 2);
                font.draw(batch, "1600x900", Settings.SCREEN_WIDTH / 2, (Settings.SCREEN_HEIGHT / 18) * 6);
        }
        input();
    }

    public static void input() {
        if (Gdx.input.isKeyJustPressed(Input.Keys.UP)) {
            selected = selected == 0 ? 3 : selected - 1;
        }
        if (Gdx.input.isKeyJustPressed(Input.Keys.DOWN)) {
            selected = selected == 3 ? 0 : selected + 1;
        }
        if (Gdx.input.isKeyJustPressed(Input.Keys.ENTER)) {
            if (selected == 0) {
                Settings.setScreenSize(1280, 720);
                ScreenHandler.changeScreenState(ScreenState.MAINMENU);
            }
            else if (selected == 1) {
                Settings.setScreenSize(1920, 1080);
                ScreenHandler.changeScreenState(ScreenState.MAINMENU);
            }
            else if (selected == 2){
                Settings.setScreenSize(1600, 900);
                ScreenHandler.changeScreenState(ScreenState.MAINMENU);
            }
            else {

                ScreenHandler.changeScreenState(ScreenState.MAINMENU);
            }
        }
    }
}
