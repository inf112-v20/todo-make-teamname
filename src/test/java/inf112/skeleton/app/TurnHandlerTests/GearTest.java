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
import inf112.skeleton.app.objects.boardObjects.GearClockwise;
import inf112.skeleton.app.objects.boardObjects.GearCounterClockwise;
import inf112.skeleton.app.objects.player.Robot;
import org.junit.BeforeClass;
import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.mock;

public class GearTest {
    private static Game game;
    private static Robot robot1, robot2;
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
        turnHandler = game.getTurnHandler();
        board = game.getBoard();
        Texture mockTexture = mock(Texture.class);
        Texture[] mockImages = {mockTexture};
        robot1 = new Robot(mockImages);
        robot2 = new Robot(mockImages);
        GearClockwise gearClockwise = new GearClockwise();
        GearCounterClockwise gearCounterClockwise = new GearCounterClockwise();
        board.addObject(robot1, 1, 0);
        board.addObject(robot2, 0, 0);
        board.addObject(gearClockwise,1,0);
        board.addObject(gearCounterClockwise, 0, 0);
    }

    @Test
    public void gearClockwiseTest(){
        assertEquals(robot1.getDirection(), Direction.EAST);
        turnHandler.gearsMove(robot1);
        assertEquals(robot1.getDirection(), Direction.SOUTH);
    }

    @Test
    public void gearCounterClockwiseTest(){
        assertEquals(robot2.getDirection(), Direction.EAST);
        turnHandler.gearsMove(robot2);
        assertEquals(robot2.getDirection(), Direction.NORTH);
    }

}
