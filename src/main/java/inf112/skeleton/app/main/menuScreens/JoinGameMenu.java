package inf112.skeleton.app.main.menuScreens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.TextField;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import inf112.skeleton.app.main.*;

/**
 * The JoinGameMenu class gives a prompt to enter an IP Address and the tries to connect to a server at that IP.
 */
public class JoinGameMenu {
    private Game game;
    private Stage stage;
    private TextField ipAddressTextField;

    /**
     * The constructor sets the game i the parameter to the field variable game.
     * @param game The game that gets played.
     */
    public JoinGameMenu(Game game){
        this.game = game;
    }

    /**
     *
     * @param batch The batch used for the game.
     * @param font The bitmapFont used for the game.
     */
    public void render(SpriteBatch batch, BitmapFont font) {
        font.draw(batch, "Type the Host's IP address", (Settings.SCREEN_WIDTH / 2)-150, (Settings.SCREEN_HEIGHT / 2) + 50);
        ipAddressTextField.draw(batch, 1);
        stage.act();
    }

    /**
     * Creates an TextInput prompt to enter the IP Address of the server.
     */
    public void create() {
        stage = new Stage();
        ipAddressTextField = new TextField("", new Skin(Gdx.files.internal("assets/textFieldTest/uiskin.json")));
        ipAddressTextField.setPosition((Settings.SCREEN_WIDTH/80) * 31,Settings.SCREEN_HEIGHT/60 * 29);
        ipAddressTextField.setSize(150, 25);
        ipAddressTextField.setMessageText("IP Address");
        ipAddressTextField.addListener(new ClickListener(){
            @Override
            public boolean keyUp(InputEvent event, int keycode) {
                String ipAddress = "";
                if(keycode == 66){
                    ipAddress = ipAddressTextField.getText();
                    joinGame(ipAddress);
                }

                return false;
            }
        });
        stage.addActor(ipAddressTextField);
        Gdx.input.setInputProcessor(stage);
    }

    /**
     * Tries to join a server, if failed will give a new text prompt trying to join again
     * @param text The ip of the server
     */
    private void joinGame(String text) {
        if (game.joinGame(text)){
            while(this.game.getBoardName() == null){}
            game.createBoardAndPlayers(game.getBoardName());
            ScreenHandler.changeScreenState(ScreenState.LOBBYMENU);
        }

        else ipAddressTextField.setDisabled(false);

    }
}
