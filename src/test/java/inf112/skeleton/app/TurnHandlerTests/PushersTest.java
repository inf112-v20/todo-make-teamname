package inf112.skeleton.app.TurnHandlerTests;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.backends.headless.HeadlessApplication;
import com.badlogic.gdx.backends.headless.HeadlessApplicationConfiguration;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import inf112.skeleton.app.EmptyApplicationListener;
import inf112.skeleton.app.board.Board;
import inf112.skeleton.app.board.Direction;
import inf112.skeleton.app.main.Game;
import inf112.skeleton.app.main.TurnHandler;
import inf112.skeleton.app.networking.Packets;
import inf112.skeleton.app.objects.boardObjects.Pusher;
import inf112.skeleton.app.objects.player.Robot;
import org.junit.BeforeClass;
import org.junit.Test;

import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.mock;

public class PushersTest {
    private static Game game;
    private static Robot robot;
    private static Board board;
    private static TurnHandler turnHandler;


    @BeforeClass
    public static void setUp(){
        HeadlessApplicationConfiguration conf = new HeadlessApplicationConfiguration();
        new HeadlessApplication(new EmptyApplicationListener(), conf);
        Gdx.gl = mock(GL20.class);
        game = new Game();
        game.setBoard(new Board(2,1,1));
        game.gamePhasesSetUp();
        game.gamePhasesSetUp();
        game.gamePhasesSetUp();
        game.textureSetUp();
        game.cardBoxSetUp();
        game.readyButtonSetUp();
        game.logSetUp();
        Packets.Packet05Name packet05Name = new Packets.Packet05Name();
        packet05Name.name = new String[]{"a","b"};
        game.receiveNames(packet05Name);
        turnHandler = game.getTurnHandler();
        board = game.getBoard();
        Texture mockTexture = mock(Texture.class);
        Texture[] mockImages = {mockTexture};
        robot = new Robot(mockImages);
        Pusher pusher = new Pusher(Direction.WEST);
        board.addObject(robot, 1, 0);
        board.addObject(pusher,1,0);
    }

    @Test
    public void pushersTest(){
        assertTrue(board.getTile(1,0).getObjects()[2] instanceof Robot);
        turnHandler.pushersMove(robot);
        assertTrue(board.getTile(0,0).getObjects()[2] instanceof Robot);
    }
}
