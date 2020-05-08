package inf112.skeleton.app.main;

import com.badlogic.gdx.ApplicationListener;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import inf112.skeleton.app.main.menuScreens.*;

public class ScreenHandler implements ApplicationListener {
    private SpriteBatch batch;
    private static BitmapFont font;

    private static ScreenState screenState = ScreenState.MAINMENU;

    private Game game;
    private static JoinGameMenu joinGameMenu;
    private static HostGameMenu hostGameMenu;
    private static LobbyMenu lobbyMenu;
    private static LevelSelectMenu levelSelectMenu;
    private Texture background;
    private Texture mainLogo;
    private InputHandler inputHandler;
    private static String winner = "";

    @Override
    public void create() {
        
        batch = new SpriteBatch();
        font = new BitmapFont();
        font.setColor(Color.BLACK);
        font.getData().setScale(1, 1); //Change this to 2 2 for bigger font size
        background = new Texture("assets/grey_background.png");
        mainLogo = new Texture("assets/main_logo.png");
        game = new Game();

        hostGameMenu = new HostGameMenu(game);
        joinGameMenu = new JoinGameMenu(game);
        lobbyMenu = new LobbyMenu(game);
        levelSelectMenu = new LevelSelectMenu(game);
        inputHandler = new InputHandler(game, lobbyMenu, hostGameMenu, joinGameMenu, levelSelectMenu, this);
        lobbyMenu.setInputHandler(inputHandler);
        game.setInputHandler(inputHandler);
        game.create();

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
                batch.draw(mainLogo, 380, 500, 550, 160);
                break;
            case HOSTGAME:
                hostGameMenu.render(batch, font);
                batch.draw(mainLogo, 380, 500, 550, 160);
                break;
            case LOBBYMENU:
                lobbyMenu.render(batch, font);
                batch.draw(mainLogo, 380, 500, 550, 160);
                break;
            case LEVELSELECT:
                levelSelectMenu.render(batch, font);
                break;
            case OPTIONS:
                OptionsMenu.render(batch, font);
                break;
            case WINSCREEN:
                batch.draw(mainLogo, 380, 500, 550, 160);
                font.setColor(Color.YELLOW);
                font.draw(batch, winner + " Has won the game!", Settings.SCREEN_WIDTH/2-250, Settings.SCREEN_HEIGHT/2, 500, 1, true);
                font.draw(batch, "Press any key to exit...", Settings.SCREEN_WIDTH/2-250, Settings.SCREEN_HEIGHT/3, 500, 1, true);

                break;
            default:
                MainMenu.render(batch, font);
                batch.draw(mainLogo, 380, 500, 550, 160);
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
        if(screenState == ScreenState.JOINGAME) joinGameMenu.create();
        if(screenState == ScreenState.LOBBYMENU) lobbyMenu.create();
        if(screenState == ScreenState.GAME){
            hostGameMenu = null;
            joinGameMenu = null;
            lobbyMenu = null;
        }
    }

    public static void changetoHostGameMenu(String boardName) {
        screenState = ScreenState.HOSTGAME;
        hostGameMenu.create(boardName);
        lobbyMenu.setHost(true);
    }
    public static void changetoWinscreen(String w){
        screenState = ScreenState.WINSCREEN;
        winner = w;
    }

    public ScreenState getScreenState(){
        return screenState;
    }

}
