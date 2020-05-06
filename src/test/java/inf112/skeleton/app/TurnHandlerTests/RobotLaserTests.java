package inf112.skeleton.app.TurnHandlerTests;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.backends.headless.HeadlessApplication;
import com.badlogic.gdx.backends.headless.HeadlessApplicationConfiguration;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import inf112.skeleton.app.EmptyApplicationListener;
import inf112.skeleton.app.board.Board;
import inf112.skeleton.app.board.BoardTile;
import inf112.skeleton.app.board.Direction;
import inf112.skeleton.app.main.Game;
import inf112.skeleton.app.main.TurnHandler;
import inf112.skeleton.app.networking.Packets;
import inf112.skeleton.app.objects.boardObjects.Wall;
import inf112.skeleton.app.objects.player.Robot;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.mock;

public class RobotLaserTests {

    private static Game game;
    private static Robot robot,robot0, robot1;
    private static Robot[] robots;
    private static Board board;
    private static TurnHandler turnHandler;
    private static Wall westWall, eastWall, northWall, southWall, northEastWall, northWestWall, southEastWall, southWestWall;
    private static Wall[] walls;

    @BeforeClass
    public static void setUp() {
        HeadlessApplicationConfiguration conf = new HeadlessApplicationConfiguration();
        new HeadlessApplication(new EmptyApplicationListener(), conf);
        Gdx.gl = mock(GL20.class);
        game = new Game();
        game.setBoard(new Board(3, 1, 3));
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
        robot0 = new Robot(mockImages);
        robot1 = new Robot(mockImages);
        robots = new Robot[]{robot, robot0, robot1};
        westWall = new Wall(Direction.WEST);
        eastWall = new Wall(Direction.EAST);
        northWall = new Wall(Direction.NORTH);
        southWall = new Wall(Direction.SOUTH);
        northEastWall = new Wall(Direction.NORTHEAST);
        northWestWall = new Wall(Direction.NORTHWEST);
        southEastWall = new Wall(Direction.SOUTHEAST);
        southWestWall = new Wall(Direction.SOUTHWEST);
        walls = new Wall[]{westWall, eastWall, southWall, northWall, northEastWall, northWestWall, southEastWall, southWestWall};
    }

    @Before
    public void remove(){
        for (Wall wall: walls) {
            BoardTile boardTile = board.getTile(wall.getTileX(), wall.getTileY());
            boardTile.remove(1);
        }
        for (Robot robot: robots) {
            board.removeObject(robot);
            robot.fullHealth();
        }
    }


    @Test
    public void shootThroughRobots() {
        //Checking if it can shoot through multiple robots
        board.addObject(robot, 0, 0);
        board.addObject(robot0, 1, 0);
        board.addObject(robot1, 2, 0);
        assertEquals(9, robot0.getHealth());
        assertEquals(9, robot1.getHealth());
        turnHandler.robotLasersShoot(robot);
        assertEquals(8, robot0.getHealth());
        assertEquals(9, robot1.getHealth());
    }

    @Test
    public void hitEvenWithWallBehind() {
        //Checking that if a robot has a wall behind, it can still be hit in the front
        board.addObject(robot, 0, 0);
        board.addObject(robot0, 1, 0);
        board.addObject(robot1, 2, 0);
        board.addObject(eastWall, 1, 0);
        assertEquals(9, robot0.getHealth());
        assertEquals(9, robot1.getHealth());
        turnHandler.robotLasersShoot(robot);
        assertEquals(8, robot0.getHealth());
        assertEquals(9, robot1.getHealth());
    }

    @Test
    public void cantShootThroughWalls(){
        //Checking if it can shoot through a wall
        board.addObject(robot, 0, 0);
        board.addObject(robot0, 1, 0);
        board.addObject(robot1, 2, 0);
        board.addObject(eastWall, 1, 0);
        board.addObject(westWall, 1, 0);
        assertEquals(9, robot0.getHealth());
        assertEquals(9, robot1.getHealth());
        turnHandler.robotLasersShoot(robot);
        assertEquals(9, robot0.getHealth());
        assertEquals(9, robot1.getHealth());


    }

    @Test
    public void cantShootThroughCornerWallsNorthEast(){
        board.addObject(robot, 0, 0);
        board.addObject(robot0, 1, 0);
        board.addObject(northEastWall, 0, 0);
        assertEquals(9, robot0.getHealth());
        turnHandler.robotLasersShoot(robot);
        assertEquals(9, robot0.getHealth());
    }

    @Test
    public void cantShootThroughCornerWallsNorthWest(){
        board.addObject(robot, 0, 0);
        board.addObject(robot0, 1, 0);
        board.addObject(northWestWall, 1, 0);
        assertEquals(9, robot0.getHealth());
        turnHandler.robotLasersShoot(robot);
        assertEquals(9, robot0.getHealth());
    }

    @Test
    public void cantShootThroughCornerWallsSouthEast(){
        board.addObject(robot, 0, 0);
        board.addObject(robot0, 1, 0);
        board.addObject(southEastWall, 0, 0);
        assertEquals(9, robot0.getHealth());
        turnHandler.robotLasersShoot(robot);
        assertEquals(9, robot0.getHealth());
    }

    @Test
    public void cantShootThroughCornerWallsSouthWest(){
        board.addObject(robot, 0, 0);
        board.addObject(robot0, 1, 0);
        board.addObject(southWestWall, 1, 0);
        assertEquals(9, robot0.getHealth());
        turnHandler.robotLasersShoot(robot);
        assertEquals(9, robot0.getHealth());
    }

    @Test
    public void canShootWhenWallBehindTarget(){

    }

}
