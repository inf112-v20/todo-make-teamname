package inf112.skeleton.app.main.menuScreens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.TextField;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import inf112.skeleton.app.main.Game;
import inf112.skeleton.app.main.InputHandler;
import inf112.skeleton.app.main.Settings;

/**
 * A simple lobby that ask for the user name of the player, then shows all the connected players. When the host presses
 * the Enter key all players go to the game screen.
 */
public class LobbyMenu {
    private boolean host = false;
    private Game game;
    private Texture checkMark;
    private InputHandler inputHandler;
    private Stage stage;
    private TextField usernameTextField;

    /**
     * The constructor sets the game i the parameter to the field variable game.
     * @param game The game that gets played.
     */
    public LobbyMenu(Game game){
        this.game = game;
    }


    public void setInputHandler(InputHandler inputHandler){
        this.inputHandler = inputHandler;
    }

    /**
     * This method sets if a player is a host, it gives that player the ability to start the game.
     * @param host This parameter is true if the player calling it is a host.
     */
    public void setHost(boolean host) {
        this.host = host;
    }

    /**
     * Creates an TextField where the player can enter his/her username. Gets called when the screen changes
     * to the lobby menu.
     */
    public void create(){
        checkMark = new Texture("assets/green_check.png");
        stage = new Stage();
        usernameTextField = new TextField("", new Skin(Gdx.files.internal("assets/textFieldTest/uiskin.json")));
        usernameTextField.setPosition((Settings.SCREEN_WIDTH/80) * 8,Settings.SCREEN_HEIGHT/60 * 40);
        usernameTextField.setSize(150, 25);
        usernameTextField.setMessageText("User name");
        stage.addListener(new ClickListener(){
            @Override
            public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
                float userX = usernameTextField.getX();
                float xPlusWidth = usernameTextField.getWidth() + usernameTextField.getX();
                float userY = usernameTextField.getY();
                float yPlusHeight = (usernameTextField.getHeight() + usernameTextField.getY());
                if (x < userX || x > xPlusWidth || y < userY || y > yPlusHeight) {
                    Gdx.input.setInputProcessor(inputHandler);
                    usernameTextField.setDisabled(true);
                }
                return true;
            }
        });

        usernameTextField.addListener(new ClickListener(){
            @Override
            public boolean keyUp(InputEvent event, int keycode) {
                String userName = "";
                if(keycode == 66){
                    userName = usernameTextField.getText();
                    game.sendName(userName);
                    Gdx.input.setInputProcessor(inputHandler);
                    usernameTextField.setDisabled(true);
                }
                if(keycode == Input.Keys.ESCAPE){
                    Gdx.input.setInputProcessor(inputHandler);
                    usernameTextField.setDisabled(true);
                }
                return false;
            }
        });
        stage.addActor(usernameTextField);
        Gdx.input.setInputProcessor(stage);

    }

    /**
     * Renders the game with the given SpriteBatch and BitmapFont. Also calls input if the player is a host.
     * @param batch The batch used for the game.
     * @param font The bitmapFont used for the game.
     */
    public void render(SpriteBatch batch, BitmapFont font) {

        String[] names = game.getNames();
        font.setColor(Color.WHITE);
        font.draw(batch, "Players joined:", (Settings.SCREEN_WIDTH / 2)-50, (Settings.SCREEN_HEIGHT / 2) + 50);
        for (int i = 0; i < names.length; i++) {
            if(names[i] == null) continue;
            font.draw(batch,  " Player " + i + " : " + names[i], (Settings.SCREEN_WIDTH / 2)-30, (Settings.SCREEN_HEIGHT / 2) - 15*i);
            if (game.getAllReady()[i]){
                batch.draw(checkMark, (Settings.SCREEN_WIDTH / 2)-50, (Settings.SCREEN_HEIGHT / 2) - 15*i - 15 , 20, 20);
            }
        }
        font.setColor(Color.YELLOW);
        font.draw(batch, "Press ENTER to start the game", (Settings.SCREEN_WIDTH / 2) -100, (Settings.SCREEN_HEIGHT / 2)-200);
        usernameTextField.draw(batch, 1);
        stage.act();
    }

    /**
     * Gives the host the ability to start the game.
     */
    public void input(int keyCode) {
        if(host) {
            if (keyCode == Input.Keys.ENTER) {
                if (game.getAllReady() == null){
                    game.sendStartSignal();
                    return;
                }
                for (int i = 2; i <= game.getNrOfPlayers(); i++) {
                    if (!game.getAllReady()[i]) return;
                }
                game.sendStartSignal();
            }
        }
        else {
            if (keyCode == Input.Keys.ENTER) {
                game.sendReadySignal();
            }
        }
    }

    public void touchDown(int screenX, int screenY, int pointer, int button){
        float x = usernameTextField.getX();
        float xPlusWidth = usernameTextField.getWidth() + usernameTextField.getX();
        float y = Settings.SCREEN_HEIGHT - usernameTextField.getY();
        float yPlusHeight = Settings.SCREEN_HEIGHT - (usernameTextField.getHeight() + usernameTextField.getY());
        if(screenX >= x && screenX <= xPlusWidth && screenY <= y && screenY >= yPlusHeight) {
            Gdx.input.setInputProcessor(stage);
            usernameTextField.setDisabled(false);
        }
    }
}
