package inf112.skeleton.app.main;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputAdapter;
import inf112.skeleton.app.main.menuScreens.HostGameMenu;
import inf112.skeleton.app.main.menuScreens.JoinGameMenu;
import inf112.skeleton.app.main.menuScreens.LobbyMenu;
import inf112.skeleton.app.main.menuScreens.MainMenu;

public class InputHandler extends InputAdapter {

    private ScreenHandler screenHandler;
    private HostGameMenu hostGameMenu;
    private JoinGameMenu joinGameMenu;
    private LobbyMenu lobbyMenu;
    private ScreenState screenState;
    private Game game;

    public InputHandler(Game game, LobbyMenu lobbyMenu, HostGameMenu hostGameMenu, JoinGameMenu joinGameMenu, ScreenHandler screenHandler){

        this.game = game;
        this.lobbyMenu = lobbyMenu;
        this.hostGameMenu = hostGameMenu;
        this.joinGameMenu = joinGameMenu;
        this.screenHandler = screenHandler;
        screenState = screenHandler.getScreenState();
        Gdx.input.setInputProcessor(this);
    }

    @Override
    public boolean keyUp(int keycode) {
        screenState = screenHandler.getScreenState();
        if(screenState.equals(ScreenState.GAME))return game.keyUp(keycode);
        if(screenState.equals(ScreenState.MAINMENU)) MainMenu.input(keycode);

        if(screenState.equals(ScreenState.LOBBYMENU)) lobbyMenu.input(keycode);
        if(screenState.equals(ScreenState.HOSTGAME)) hostGameMenu.input(keycode);
        return true;
    }


    @Override
    public boolean touchDown(int screenX, int screenY, int pointer, int button) {
        screenState = screenHandler.getScreenState();
        if(screenState.equals(ScreenState.GAME)) return game.touchDown(screenX, screenY, pointer, button);
        return true;
    }

    @Override
    public boolean touchDragged(int screenX, int screenY, int pointer) {
        screenState = screenHandler.getScreenState();
        if(screenState.equals(ScreenState.GAME)) return game.touchDragged(screenX, screenY, pointer);
        return true;
    }

}
