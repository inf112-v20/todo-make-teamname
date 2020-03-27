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

import inf112.skeleton.app.objects.boardObjects.Wall;
import inf112.skeleton.app.objects.cards.NonTextureProgramCard;
import inf112.skeleton.app.objects.player.Player;
import inf112.skeleton.app.objects.player.Robot;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.mock;

public class WallCollisionTest {
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
        game.setBoard(new Board(4,1,1));
        game.gamePhasesSetUp();
        Texture mockTexture = mock(Texture.class);
        Texture[] mockImages = {mockTexture};
        player = new Player(mockImages);
        Wall wall = new Wall(Direction.NORTH);
        turnHandler = game.getTurnHandler();
        board = game.getBoard();
        board.addObject(player.getRobot(), 1, 0);
        robot = player.getRobot();
        robot.setDirection(Direction.NORTH);
    }

    @Before
    public void robotSetUp(){
        Texture mockTexture = mock(Texture.class);
        Texture[] mockImages = {mockTexture};
        robot = new Robot(mockImages);
        board.addObject(robot, 1, 0);
    }

    @Test
    public void robotDoesNotWalkThroughNORTHWall() {
        Wall wall = new Wall(Direction.NORTH);
        board.addObject(wall, 1, 0);

        assertTrue(board.getTile(1, 0).getObjects()[2] instanceof Robot);
        assertTrue(board.getTile(1, 0).getObjects()[1] instanceof Wall);
        NonTextureProgramCard card = new NonTextureProgramCard(1,false,false,false);
        robot.setDirection(Direction.NORTH);
        turnHandler.cardMove(card, robot);
        assertEquals(1, robot.getTileX());
        assertEquals(0, robot.getTileY());
    }

    @Test
    public void robotDoesNotWalkThroughSOUTHWall() {
        Wall wall = new Wall(Direction.SOUTH);
        board.addObject(wall, 1, 0);
        assertTrue(board.getTile(1, 0).getObjects()[2] instanceof Robot);
        assertTrue(board.getTile(1, 0).getObjects()[1] instanceof Wall);
        NonTextureProgramCard card = new NonTextureProgramCard(1,false,false,false);
        robot.setDirection(Direction.SOUTH);
        turnHandler.cardMove(card, robot);
        assertEquals(1, robot.getTileX());
        assertEquals(0, robot.getTileY());
    }

    @Test
    public void robotDoesNotWalkThroughSOUTHWESTWall() {
        Wall wall = new Wall(Direction.SOUTHWEST);
        board.addObject(wall, 1, 0);
        assertTrue(board.getTile(1, 0).getObjects()[2] instanceof Robot);
        assertTrue(board.getTile(1, 0).getObjects()[1] instanceof Wall);
        NonTextureProgramCard card = new NonTextureProgramCard(1,false,false,false);
        robot.setDirection(Direction.SOUTH);
        turnHandler.cardMove(card, robot);
        assertEquals(1, robot.getTileX());
        assertEquals(0, robot.getTileY());
    }

    @Test
    public void robotDoesNotWalkThroughWallofNeighbourTile() {
        Wall wall = new Wall(Direction.WEST);
        board.addObject(wall, 2, 0);
        assertTrue(board.getTile(1, 0).getObjects()[2] instanceof Robot);
        assertTrue(board.getTile(2, 0).getObjects()[1] instanceof Wall);
        NonTextureProgramCard card = new NonTextureProgramCard(1,false,false,false);
        robot.setDirection(Direction.EAST);
        turnHandler.cardMove(card, robot);
        assertEquals(1, robot.getTileX());
        assertEquals(0, robot.getTileY());
    }
}
