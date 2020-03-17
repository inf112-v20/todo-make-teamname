package inf112.skeleton.app.main;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

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
        input();
    }

    public static void input() {
        if (Gdx.input.isKeyJustPressed(Input.Keys.UP)) {
            selected = selected == 0 ? 1 : selected - 1;
        }
        if (Gdx.input.isKeyJustPressed(Input.Keys.DOWN)) {
            selected = selected == 1 ? 0 : selected + 1;
        }
        if (Gdx.input.isKeyJustPressed(Input.Keys.ENTER)) {
            if (selected == 0) {
                ScreenHandler.changeScreenState(ScreenState.HOSTGAME);
            }
            else if (selected == 1) {
                ScreenHandler.changeScreenState(ScreenState.JOINGAME);
            }
            else {
                Gdx.app.exit();
            }
        }
    }

}
