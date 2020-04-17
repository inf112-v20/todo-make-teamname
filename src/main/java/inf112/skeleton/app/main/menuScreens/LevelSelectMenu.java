package inf112.skeleton.app.main.menuScreens;

import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import inf112.skeleton.app.main.*;

public class LevelSelectMenu {

    private Game game;
    private static int selected = 0;

    public LevelSelectMenu(Game game) {
        this.game = game;
    }

    public void render(SpriteBatch batch, BitmapFont font) {
        switch(selected) {
            case 0:
                font.setColor(Color.WHITE);
                font.draw(batch, "Checkmate", (Settings.SCREEN_WIDTH / 2)-60, Settings.SCREEN_HEIGHT / 2);
                font.draw(batch, "Go Back", (Settings.SCREEN_WIDTH / 2)-60, (Settings.SCREEN_HEIGHT / 18) * 8);
                font.setColor(Color.YELLOW);
                font.draw(batch, "RiskyExchange", (Settings.SCREEN_WIDTH / 2)-50, (Settings.SCREEN_HEIGHT / 18) * 10);
                break;
            case 1:
                font.setColor(Color.WHITE);
                font.draw(batch, "RiskyExchange", (Settings.SCREEN_WIDTH / 2)-60, (Settings.SCREEN_HEIGHT / 18) * 10);
                font.draw(batch, "Go Back", (Settings.SCREEN_WIDTH / 2)-60, (Settings.SCREEN_HEIGHT / 18) * 8);
                font.setColor(Color.YELLOW);
                font.draw(batch, "Checkmate", (Settings.SCREEN_WIDTH / 2)-50, Settings.SCREEN_HEIGHT / 2);
                break;
            default:
                font.setColor(Color.WHITE);
                font.draw(batch, "RiskyExchange", Settings.SCREEN_WIDTH / 2, (Settings.SCREEN_HEIGHT / 18) * 10);
                font.draw(batch, "Checkmate", Settings.SCREEN_WIDTH / 2, Settings.SCREEN_HEIGHT / 2);
                font.draw(batch, "Go Back", Settings.SCREEN_WIDTH / 2, (Settings.SCREEN_HEIGHT / 18) * 8);
        }
    }

    public void input(int keyCode) {
        switch (keyCode){
            case Input.Keys.UP:
                selected = selected == 0 ? 2 : selected - 1;
                break;
            case Input.Keys.DOWN:
                selected = selected == 2 ? 0 : selected + 1;
                break;
            case Input.Keys.ENTER:
                if (selected == 0) {
                    ScreenHandler.changetoHostGameMenu("riskyexchange");
                    ScreenHandler.changeScreenState(ScreenState.HOSTGAME);
                }
                else if (selected == 1) {
                    ScreenHandler.changetoHostGameMenu("checkmate");
                }
                else {
                    ScreenHandler.changeScreenState(ScreenState.MAINMENU);
                }
            default:
                break;
        }
    }
}
