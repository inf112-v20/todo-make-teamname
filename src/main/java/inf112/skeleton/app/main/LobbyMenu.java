package inf112.skeleton.app.main;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

import java.util.ArrayList;

public class LobbyMenu {
    private boolean host = false;
    private Game game;


    public LobbyMenu(Game game){
        this.game = game;
    }

    public void setHost(boolean host) {
        this.host = host;
    }
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

    public void render(SpriteBatch batch, BitmapFont font) {
        if(host) {
            input();
        }
        String[] names = game.getNames();
        font.setColor(Color.BLACK);
        font.draw(batch, "Players joined:", Settings.SCREEN_WIDTH / 4, (Settings.SCREEN_HEIGHT / 18) * 15);
        for (int i = 0; i < names.length; i++) {
            if(names[i] == null) continue;
            font.draw(batch, names[i], Settings.SCREEN_WIDTH / 4, (Settings.SCREEN_HEIGHT / 18) * (14 - i));
        }

    }
    public void input() {
        if (Gdx.input.isKeyJustPressed(Input.Keys.ENTER)) {
            game.sendStartSignal();
        }
    }
}
