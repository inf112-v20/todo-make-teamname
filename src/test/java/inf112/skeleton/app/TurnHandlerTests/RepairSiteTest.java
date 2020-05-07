package inf112.skeleton.app.TurnHandlerTests;

import com.badlogic.gdx.ApplicationListener;
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
import inf112.skeleton.app.objects.boardObjects.RepairSite;
import inf112.skeleton.app.objects.player.Player;
import inf112.skeleton.app.objects.player.Robot;
import org.junit.BeforeClass;
import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.mock;

public class RepairSiteTest {
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
        game.setBoard(new Board(2,1,1));
        game.gamePhasesSetUp();
        game.textureSetUp();
        game.cardBoxSetUp();
        game.readyButtonSetUp();
        game.logSetUp();
        Packets.Packet05Name packet05Name = new Packets.Packet05Name();
        packet05Name.name = new String[]{"a","b"};
        game.receiveNames(packet05Name);
        Texture mockTexture = mock(Texture.class);
        Texture[] mockImages = {mockTexture};
        player = new Player(mockImages);

        turnHandler = game.getTurnHandler();
        board = game.getBoard();
        board.addObject(player.getRobot(), 1, 0);
        robot = player.getRobot();
        RepairSite repairSite = new RepairSite(false);
        board.addObject(repairSite,1,0);
    }

    @Test
    public void repairSiteTest(){
        robot.takeDamage();
        assertEquals(robot.getHealth(), 8);
        turnHandler.repair(player);
        assertEquals(robot.getHealth(), 9);
    }
}
