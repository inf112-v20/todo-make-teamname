package inf112.skeleton.app.TurnHandlerTests;

import inf112.skeleton.app.board.Board;
import inf112.skeleton.app.board.Direction;
import inf112.skeleton.app.main.Game;
import inf112.skeleton.app.main.TurnHandler;

import inf112.skeleton.app.objects.boardObjects.Wall;
import inf112.skeleton.app.objects.player.Robot;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import static org.junit.Assert.assertTrue;

public class WallCollisionTest {
    private static Game game;
    private static Robot robot;
    private static Board board;
    private static TurnHandler turnHandler;

    @BeforeClass
    public static void setUp(){
        game = new Game();
        game.setBoard(new Board(2,1,1));
        game.gamePhasesSetUp();
        turnHandler = game.getTurnHandler();
        board = game.getBoard();
        Wall wall = new Wall(Direction.NORTH);
        board.addObject(wall, 1, 0);
    }

    @Before
    public void robotSetUp(){
        robot = new Robot();
        board.addObject(robot, 1, 0);
    }

    @Test
    public void robotDoesNotWalkThroughWall() {
        assertTrue(board.getTile(1, 0).getObjects()[2] instanceof Robot);
        assertTrue(board.getTile(1, 0).getObjects()[1] instanceof Wall);
        //TODO: make robot move north, then check if robot is still in same tile


    }





}
