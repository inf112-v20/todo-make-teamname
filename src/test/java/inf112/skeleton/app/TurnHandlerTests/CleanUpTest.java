package inf112.skeleton.app.TurnHandlerTests;


import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.backends.headless.HeadlessApplication;
import com.badlogic.gdx.backends.headless.HeadlessApplicationConfiguration;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import inf112.skeleton.app.EmptyApplicationListener;
import inf112.skeleton.app.board.Board;
import inf112.skeleton.app.main.Game;
import inf112.skeleton.app.main.TurnHandler;
import inf112.skeleton.app.networking.Packets;
import inf112.skeleton.app.objects.cards.ProgramCard;
import inf112.skeleton.app.objects.player.Player;
import inf112.skeleton.app.objects.player.Robot;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import static org.junit.Assert.*;
import static org.mockito.Mockito.mock;

public class CleanUpTest {

    private static Game game;
    private static Player player;
    private static Robot robot;
    private static Board board;
    private static TurnHandler turnHandler;


    @BeforeClass
    public static void setUp(){
        HeadlessApplicationConfiguration conf = new HeadlessApplicationConfiguration();
        new HeadlessApplication(new EmptyApplicationListener(), conf);
        Gdx.gl = mock(GL20.class);
        game = new Game();
        game.textureSetUp();
        game.cardBoxSetUp();
        game.readyButtonSetUp();
        game.logSetUp();
        game.setBoard(new Board(1,1,1));
        game.gamePhasesSetUp();
        turnHandler = game.getTurnHandler();
        game.setPlayersShutdown(new boolean[]{false, false});
        Packets.Packet05Name packet05Name = new Packets.Packet05Name();
        packet05Name.name = new String[]{"a","b"};
        game.receiveNames(packet05Name);
    }

    @Before
    public void resetPlayer(){
        Texture mockTexture = mock(Texture.class);
        Texture[] mockImages = {mockTexture};
        player = new Player(mockImages);
        ProgramCard card = mock(ProgramCard.class);
        ProgramCard[] cards = new ProgramCard[]{card};
        player.setCards(cards);
        game.setMyPlayer(player);
        player.deal();
        board = game.getBoard();
        board.addObject(player.getRobot(), 0, 0);
        robot = player.getRobot();
    }

    @Test
    public void cleanUpTest(){
        robot.destroy();
        assertTrue(robot.isDestroyed());
        assertEquals(2, robot.getLife());
        turnHandler.cleanUp(player);
        assertFalse(robot.isDestroyed());
    }
}
