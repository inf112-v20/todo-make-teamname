package inf112.skeleton.app.TurnHandlerTests;

import inf112.skeleton.app.board.Board;
import inf112.skeleton.app.main.Game;
import inf112.skeleton.app.main.TurnHandler;
import inf112.skeleton.app.objects.boardObjects.RepairSite;
import inf112.skeleton.app.objects.player.Player;
import inf112.skeleton.app.objects.player.Robot;
import org.junit.BeforeClass;
import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.mock;

public class RepairSiteTest {
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
        robot = new Robot();
        RepairSite repairSite = new RepairSite(false);
        board.addObject(robot, 1, 0);
        board.addObject(repairSite,1,0);
    }

    @Test
    public void repairSiteTest(){
        robot.takeDamage();
        assertEquals(robot.getHealth(), 8);
        turnHandler.repair(robot, mock(Player.class));
        assertEquals(robot.getHealth(), 9);
    }
}
