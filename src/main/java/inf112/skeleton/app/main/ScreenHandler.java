package inf112.skeleton.app.main;

import com.badlogic.gdx.ApplicationListener;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

public class ScreenHandler implements ApplicationListener {
    private SpriteBatch batch;
    private BitmapFont font;

    private static ScreenState screenState = ScreenState.MAINMENU;

    private Game game;
    private static JoinGameMenu joinGameMenu;
    private static HostGameMenu hostGameMenu;
    private static LobbyMenu lobbyMenu;
    private Texture background;


    @Override
    public void create() {
        batch = new SpriteBatch();
        font = new BitmapFont();
        font.setColor(Color.BLACK);
        background = new Texture("assets/pink_background.png");
        game = new Game();
        game.create();
        hostGameMenu = new HostGameMenu(game);
        joinGameMenu = new JoinGameMenu(game);
        lobbyMenu = new LobbyMenu(game);

    }

    @Override
    public void resize(int i, int i1) {

    }

    @Override
    public void render() {
        batch.begin();
        batch.draw(background, 0, 0, Settings.SCREEN_WIDTH, Settings.SCREEN_HEIGHT);
        //Changes what's getting rendered based on the ScreenState Enum
        switch (screenState) {
            case GAME:
                game.render(batch, font);
                break;
            case JOINGAME:
                joinGameMenu.render(batch, font);
                break;
            case HOSTGAME:
                hostGameMenu.render(batch, font);
                break;
            case LOBBYMENU:
                lobbyMenu.render(batch, font);
                break;
            default:
                MainMenu.render(batch, font);
                break;
        }
        batch.end();
    }

    @Override
    public void pause() {

    }

    @Override
    public void resume() {

    }

    @Override
    public void dispose() {
        game.dispose();
        batch.dispose();
        font.dispose();
    }

    public static void changeScreenState(ScreenState newState) {
        screenState = newState;
        if(screenState == ScreenState.HOSTGAME) {
            hostGameMenu.create();
            lobbyMenu.setHost(true);
        }
        if(screenState == ScreenState.JOINGAME) joinGameMenu.create();
        if(screenState == ScreenState.LOBBYMENU) lobbyMenu.create();
        if(screenState == ScreenState.GAME){
            hostGameMenu = null;
            joinGameMenu = null;
            lobbyMenu = null;
        }
    }

}
