package inf112.skeleton.app.main;


import inf112.skeleton.app.board.Board;
import inf112.skeleton.app.board.BoardTile;
import inf112.skeleton.app.board.Direction;
import inf112.skeleton.app.board.DirectionConverter;
import inf112.skeleton.app.networking.Packets;
import inf112.skeleton.app.objects.boardObjects.*;
import inf112.skeleton.app.objects.cards.CardTranslator;
import inf112.skeleton.app.objects.cards.NonTextureProgramCard;
import inf112.skeleton.app.objects.player.Player;
import inf112.skeleton.app.objects.player.Robot;


import java.util.ArrayList;
import java.util.HashMap;
import java.util.concurrent.Semaphore;

import static inf112.skeleton.app.board.DirectionConverter.*;

/**
 * The TurnHandler class handles the turn from after the program registers phase and until the end of the turn. <BR>
 * You wil only need to use one TurnHandler for the program as it will wait on new cards from the {@link Game}  class between
 * each turn.<BR>
 * When it gets the cards from Game it then plays them out on the board according to the value of cards and elements on
 * the board.
 */
public class TurnHandler {

    private boolean gameIsDone;
    private Semaphore isReadySem;
    private Game game;
    private HashMap<Integer, Player> idPlayerHash;
    private Thread phase;
    private Board board;
    private ArrayList<ArrayList<int[]>> allArraysOfCoordinates;


    /**
     * The create method is used for setting up the TurnHandler so that all field variables are initialized,<BR> or
     * referenced from {@link Game}, it then finishes with starting a new Thread so that doTurn runs
     * concurrently with the rest
     * of the program. Making the backend run separately from the frontend.
     * @param game1 This parameter links the TurnHandler class to the Game class so that they are referencing the same
     *              object.
     */
    public void create(Game game1){
        this.game = game1;
        isReadySem = new Semaphore(0);
        gameIsDone = false;
        idPlayerHash = game.getIdPlayerHash();
        board = game.getBoard();
        allArraysOfCoordinates = new ArrayList<>();
        phase = new Thread(this::doTurn);
        phase.start();
    }

    /**
     * This method releases the semaphore that lets {@link TurnHandler#doTurn()}  pass so that the Complete Registers
     * phase can start.
     * Call on this when the new cards have been saved in allCards in Game.
     */
    public void isReady(){
        isReadySem.release();
    }

    /**
     * This method handles all the parts of the phases after the Program Register phase.<BR> It starts when is ready is
     * called, you should not call this method.<BR>
     * First it gets the released and acquires the semaphore again.<BR>
     * Then it gets the new cards form {@link Game#getAllCards()} , <BR>
     * and makes a HashMap for each of the 5 turns of the program cards.<BR>
     * It does the following 5 times, one time for each card per player: <ol>
     *<li>  The method pairs the cards with the {@link TurnHandler#idPlayerHash}'s playerId  so that the correct robot
     *      moves when a card is played.
     *<li>  After that each robot does the move the card indicated.
     *<li>  Then express conveyor belts move once, followed by express conveyor belts and conveyor belts moving once.
     *<li>  Pushers push robots, and gears then rotate them.
     *<li>  Robots gets shot by lasers, first the boards lasers, then the other robots.
     *<li>  Then the robots pick up flags if possible, and win the game if they got all the flags.
     *</ol>  Afterwards repair sites repair robots, and finally if the robot is on a pit it falls into it.
     * After doing this 5 times it clears the register and waits for new cards.
     */
    public void doTurn() {
        while (!gameIsDone) {
            //Does a full register for each iteration of the while loop
            try {
                //Waits until cards have been selected
                isReadySem.acquire();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            if(Thread.interrupted()) return;


            for (int i = 0; i < 5; i++) {
                HashMap<NonTextureProgramCard, Integer> cards = new HashMap<>();
                ArrayList<Packets.Packet02Cards> allCards = game.getAllCards();
                for (Packets.Packet02Cards packet: allCards) {
                    cards.put(CardTranslator.intToProgramCard(packet.programCards[i]), packet.playerId);
                }
                for (NonTextureProgramCard card: cards.keySet()) {
                    if (game.getPlayersShutdown()[cards.get(card)]) continue;
                    cardMove(card, idPlayerHash.get(cards.get(card)).getRobot()); // moves robot
                }
                for (int id: idPlayerHash.keySet()) {
                    Robot robot = idPlayerHash.get(id).getRobot();
                    expressConveyorMove(robot);
                }
                for (int id: idPlayerHash.keySet()) {
                    Robot robot = idPlayerHash.get(id).getRobot();
                    conveyorMove(robot); // conveyorbelt moves
                }
                for (int id: idPlayerHash.keySet()) {
                    Robot robot = idPlayerHash.get(id).getRobot();
                    pushersMove(robot);
                }
                for (int id: idPlayerHash.keySet()) {
                    Robot robot = idPlayerHash.get(id).getRobot();
                    gearsMove(robot);
                }
                for (int id: idPlayerHash.keySet()) {
                    Robot robot = idPlayerHash.get(id).getRobot();
                    boardLasersShoot(robot);
                }
                for (int id: idPlayerHash.keySet()) {
                    Robot robot = idPlayerHash.get(id).getRobot();
                    robotLasersShoot(robot);
                }
                game.setRenderRobotLasers(true);

                for (int id: idPlayerHash.keySet()) {
                    pickUpFlag(idPlayerHash.get(id));
                }
                for (int id: idPlayerHash.keySet()) {
                    Robot robot = idPlayerHash.get(id).getRobot();
                    pitFall(robot);
                }
                try {
                    Thread.sleep(400);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
            for (int id: idPlayerHash.keySet()) {
                repair(idPlayerHash.get(id));
            }
            for (int id: idPlayerHash.keySet()) {
                cleanUp(idPlayerHash.get(id));
            }
            game.clearAllCards();
        }
    }

    /**
     * The pitFall method checks if the robot falls into a pit, and if it does it destroys the robot
     * @param robot This is the robot that gets checked if it is on a pit.
     */
    public void pitFall(Robot robot) {
        if (!robot.isDestroyed()) {
            if (board.getTile(robot.getTileX(), robot.getTileY()).getObjects()[0] instanceof Pit) {
                //Robot falls into pit
                board.removeObject(robot);
                robot.destroy();
            }
        }
    }

    /**
     * The cleanUp method respawn a robot if it is destroyed, or if the player is out of lives, that player is then out
     * of the game.
     * @param myPlayer This is the player and corresponding robot that gets checked.
     */
    public void cleanUp(Player myPlayer) {
        Robot robot = myPlayer.getRobot();
        boolean[] tempPlayersShutdown = game.getPlayersShutdown();
        for (int i = 0; i < game.getPlayersShutdown().length; i++) {
            tempPlayersShutdown[i] = false;
        }
        game.setPlayersShutdown(tempPlayersShutdown);
        if (robot.isDestroyed()) {
            if (myPlayer.getLife() > 0) {
                //Respawn robot if player has more life left
                board.addObject(robot, robot.getRespawnX(), robot.getRespawnY());
                robot.respawn();
                System.out.println("respawn");
            } else {
                //Not the case if there are more players
                gameIsDone = true;
            }
        }
        if (myPlayer.equals(game.getMyPlayer())) {
            game.getMyPlayer().discard();
            game.getMyPlayer().deal();
            myPlayer.setReadyButton(false);
        }
    }

    /**
     * The method repairs a robot and sets a new respawn location for it.
     * If it is a wrench and hammer repair site it gives an option card to the player.
     * @param myPlayer
     */
    public void repair(Player myPlayer) {
        Robot robot = myPlayer.getRobot();
        if(robot.isDestroyed())return;
        BoardTile currentTile = board.getTile(robot.getTileX(), robot.getTileY());
        if (currentTile.getObjects()[0] instanceof RepairSite) {
            //Heals a damage token if robot
            robot.healDamage();
            robot.setRespawn(robot.getTileX(), robot.getTileY());
            RepairSite repairSite = (RepairSite) currentTile.getObjects()[0];
            if (repairSite.getHammer()) {
                //Player gets an option card
                //TODO implement optionCards
                myPlayer.giveOptionCard();
            }
        }
    }

    /**
     * pickUpFlag picks up flags for the player, and if the player has all the flags, wins the game.
     * @param myPlayer This is the player that tries to pick up flags.
     */
    public void pickUpFlag(Player myPlayer) {
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        Robot robot = myPlayer.getRobot();
        if(robot.isDestroyed())return;
        BoardTile currentTile = board.getTile(robot.getTileX(), robot.getTileY());
        if (currentTile.getObjects()[0] instanceof Flag) {
            robot.setRespawn(robot.getTileX(), robot.getTileY());
            Flag flag = (Flag) currentTile.getObjects()[0];
            if (!myPlayer.getFlags().contains(flag) && myPlayer.getFlags().size() + 1 == flag.getNr()) {
                myPlayer.addFlag(flag);
                if (myPlayer.getFlags().size() == board.getFlagNr()) {
                    //TODO endscreen
                    ScreenHandler.changeScreenState(ScreenState.MAINMENU);
                    gameIsDone = true;
                }
            }
        }else if(currentTile.getObjects()[0] instanceof RepairSite){
            robot.setRespawn(robot.getTileX(), robot.getTileY());
        }
    }

    /**
     * Board lasers shoot, if a robot is in the path of a laser it gets hit and takes damage.
     * @param robot This is the robot that gets checked.
     */
    public void boardLasersShoot(Robot robot) {
        if (robot.isDestroyed())return;
        BoardTile currentTile = board.getTile(robot.getTileX(), robot.getTileY());
        if (currentTile.getObjects()[3] instanceof BoardLaser) {
            robot.takeDamage();
        }
    }

    public void robotLasersShoot(Robot robot){
        ArrayList<int[]> arrayOfCoordinates = new ArrayList<>();
        if (robot.getDirection().equals(Direction.NORTH)) {
            if(laserCurrentTileCollision(robot)) return;
            for (int y = robot.getTileY() + 1; y < board.getHeight(); y++) {
                BoardTile boardTile = board.getTile(robot.getTileX(), y);
                if(laserCollision(boardTile, robot.getDirection()))break;
                else {
                    arrayOfCoordinates.add(new int[]{robot.getTileX(), y});
                }
            }
        }else if (robot.getDirection().equals(Direction.SOUTH)) {
            if(laserCurrentTileCollision(robot)) return;
            for (int y = robot.getTileY() - 1; y >= 0; y--) {
                BoardTile boardTile = board.getTile(robot.getTileX(), y);
                if(laserCollision(boardTile, robot.getDirection()))break;
                else {
                    arrayOfCoordinates.add(new int[]{robot.getTileX(), y});
                }
            }
        }else if (robot.getDirection().equals(Direction.EAST)){
            if(laserCurrentTileCollision(robot)) return;
            for (int x = robot.getTileX() + 1; x < board.getWidth(); x++) {
                BoardTile boardTile = board.getTile(x, robot.getTileY());
                if(laserCollision(boardTile, robot.getDirection()))break;
                else {
                    arrayOfCoordinates.add(new int[]{x, robot.getTileY()});
                }
            }
        }else {
            if(laserCurrentTileCollision(robot)) return;
            for (int x = robot.getTileX() - 1; x >= 0; x--) {
                BoardTile boardTile = board.getTile(x, robot.getTileY());
                if(laserCollision(boardTile, robot.getDirection()))break;
                else {
                    arrayOfCoordinates.add(new int[]{x, robot.getTileY()});
                }
            }
        }
        allArraysOfCoordinates.add(arrayOfCoordinates);
    }

    private boolean laserCurrentTileCollision(Robot robot) {
        BoardTile currentTile = board.getTile(robot.getTileX(), robot.getTileY());
        if(currentTile.getObjects()[1] != null){
            if(robot.getDirection().equals(currentTile.getObjects()[1].getDirection())) return true;
        }
        if(currentTile.getObjects()[0] instanceof Pusher){
            return robot.getDirection().equals(oppositeDirection(currentTile.getObjects()[0].getDirection()));
        }
        return false;
    }

    private boolean shootRobot(BoardTile boardTile) {
        if(boardTile.getObjects()[2] instanceof Robot){
            Robot robot = (Robot) boardTile.getObjects()[2];
            robot.takeDamage();
            return true;
        }
        return false;
    }

    /**
     * Gears rotate robots that are on top of them.
     * @param robot This is the robot that gets checked.
     */
    public void gearsMove(Robot robot) {
        if(robot.isDestroyed())return;
        BoardTile currentTile = board.getTile(robot.getTileX(), robot.getTileY());
        if (currentTile.getObjects()[0] instanceof GearClockwise) {
            //Rotate right
            robot.rotateRight();
        }

        if (currentTile.getObjects()[0] instanceof GearCounterClockwise) {
            //Rotate left
            robot.rotateLeft();
        }
    }

    /**
     * Pushers move robots if the are on top of them.
     * @param robot This is the robot that gets checked.
     */
    public void pushersMove(Robot robot) {
        if(robot.isDestroyed())return;
        BoardTile currentTile = board.getTile(robot.getTileX(), robot.getTileY());
        if (currentTile.getObjects()[0] instanceof Pusher) {
            //Robot gets pushed
            Pusher pusher = (Pusher) currentTile.getObjects()[0];
            moveRobot(robot, pusher.getDirection());
        }
    }

    /**
     * Express conveyor belts moves once, if a robot is on top it moves with it.
     * @param robot This is the robot tha may get moved.
     */
    public void expressConveyorMove(Robot robot) {
        if (robot.isDestroyed()) return;
        BoardTile currentTile = board.getTile(robot.getTileX(), robot.getTileY());
        //Board elements do their things
        if (currentTile.getObjects()[0] instanceof ConveyorBelt) {
            ConveyorBelt conveyorBelt = (ConveyorBelt) currentTile.getObjects()[0];
            if(robotCollision(robot, conveyorBelt.getDirection()) || conveyorCollision(conveyorBelt,robot)) return;
            if (conveyorBelt.getExpress()) {
                //Expressconveoyrbelt moves robot
                moveRobot(robot, conveyorBelt.getDirection());
                currentTile = board.getTile(robot.getTileX(), robot.getTileY());
                if(currentTile.getObjects()[0] instanceof ConveyorBelt){// if the new conveyor belt can rotate the robot it does it
                    conveyorBelt = (ConveyorBelt) currentTile.getObjects()[0];
                    if(conveyorBelt.canRotate()){
                        robot.setDirection(conveyorBelt.getDirection());
                    }
                }
                try {
                    Thread.sleep(100);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }

        }
    }

    /**
     * Conveyor belts and express conveyor belts moves once, if a robot is on top it moves with it.
     * @param robot This is the robot tha may get moved.
     */
    public void conveyorMove(Robot robot) {

        if(robot.isDestroyed())return;
        BoardTile currentTile = board.getTile(robot.getTileX(), robot.getTileY());
        if (currentTile.getObjects()[0] instanceof ConveyorBelt) {
            //Conveoyrbelt moves robot
            ConveyorBelt conveyorBelt = (ConveyorBelt) currentTile.getObjects()[0];
            if(robotCollision(robot, conveyorBelt.getDirection()) || conveyorCollision(conveyorBelt, robot)) return;
            moveRobot(robot, conveyorBelt.getDirection());
            currentTile = board.getTile(robot.getTileX(), robot.getTileY());
            if(currentTile.getObjects()[0] instanceof ConveyorBelt){
                conveyorBelt = (ConveyorBelt) currentTile.getObjects()[0];
                if(conveyorBelt.canRotate()){ // if the new conveyor belt can rotate the robot it does it
                    robot.setDirection(conveyorBelt.getDirection());
                }
            }
            try {
                Thread.sleep(100);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    /**
     * See Roborally rulebook page 7
     * @param conveyorBelt
     * @param robot
     * @return Returns true if collision occurs
     */
    public boolean conveyorCollision(ConveyorBelt conveyorBelt, Robot robot){
        int x = (directionToIntCoordinate(conveyorBelt.getDirection())[0] *2) + robot.getTileX();
        int y = (directionToIntCoordinate(conveyorBelt.getDirection())[1] *2) + robot.getTileY();
        if(x >= board.getWidth() || x < 0 || y < 0 || y >= board.getHeight()) return false;
        BoardTile boardTile = board.getTile(x, y);
        return boardTile.getObjects()[0] instanceof ConveyorBelt && boardTile.getObjects()[2] instanceof Robot
                && boardTile.getObjects()[0].getDirection().equals(oppositeDirection(conveyorBelt.getDirection()));
    }

    /**
     * Boolean method that checks if there is a wall or pusher where the robot wants to move
     */
    private boolean wallCollision(Robot robot, Direction direction){
        if(robot.isDestroyed()) return false;
        BoardTile currentTile = board.getTile(robot.getTileX(), robot.getTileY());
        if (currentTile.getObjects()[1] instanceof Wall) {
            Wall wall = (Wall) currentTile.getObjects()[1];
            if (compareWallDirection(direction, wall.getDirection())) {
                System.out.println("hit wall");
                return true;
            }
        }else if(currentTile.getObjects()[0] instanceof Pusher){
            Pusher pusher = (Pusher) currentTile.getObjects()[0];
            if(compareWallDirection(direction, oppositeDirection(pusher.getDirection()))){
                System.out.println("hit pusher");
                return true;
            }
        }
        BoardTile nextTile = nextTile(robot, direction);
        if(nextTile == null) return false;
        if (nextTile.getObjects()[1] instanceof Wall) {
            Wall nextWall = (Wall) nextTile(robot, direction).getObjects()[1];
            if (compareWallDirection(direction, oppositeDirection(nextWall.getDirection()))) {
                System.out.println("hit wall");
                return true;
            }
        }else if(nextTile.getObjects()[0] instanceof Pusher){
            Pusher nextPusher = (Pusher) nextTile.getObjects()[0];
            if(compareWallDirection(direction, nextPusher.getDirection())){
                System.out.println("hit pusher");
                return true;
            }
        }

        return false;
    }

    /**
     * This method checks if two robots crash
     * @param robot The robot that is going to move
     * @param direction The direction the robot is going, not necesseary robot.getDirection
     * @return returns true if there is a robot there
     */
    private boolean robotCollision(Robot robot, Direction direction){
        BoardTile nextTile = nextTile(robot, direction);
        if(nextTile == null) return false;
        return nextTile.getObjects()[2] instanceof Robot;
    }

    private boolean laserCollision(BoardTile boardTile, Direction direction){
        if(boardTile.getObjects()[1] instanceof Wall){
            IBoardObject boardObject = boardTile.getObjects()[1];
            if(direction.equals(oppositeDirection(boardObject.getDirection()))) return true;
            else if(direction.equals(boardObject.getDirection())){
                shootRobot(boardTile);
                return true;
            }
        }else if(boardTile.getObjects()[0] instanceof Pusher){
            IBoardObject boardObject = boardTile.getObjects()[0];
            if(direction.equals(boardObject.getDirection())) return true;
            else if(direction.equals(oppositeDirection(boardObject.getDirection()))){
                shootRobot(boardTile);
                return true;
            }
        }else return shootRobot(boardTile);
        return false;
    }

    /**
     * This method is used to get the next tile the robot is going to enter
     * @param robot The robot that is going to move
     * @param direction The direction it is moving
     * @return Returns a the next BoardTile in the robots direction, or null if it is outside the board
     */
    private BoardTile nextTile(Robot robot, Direction direction) {
        BoardTile result;
        switch (direction){
            case WEST:
                if((robot.getTileX() - 1 < 0 || robot.getTileX() - 1 >= board.getWidth()
                        || robot.getTileY() < 0 || robot.getTileY() >= board.getHeight())) {
                    result = null;
                    break;
                }
                    result =  board.getTile(robot.getTileX() - 1, robot.getTileY());
                break;
            case SOUTH:
                if((robot.getTileX() < 0 || robot.getTileX() >= board.getWidth()
                        || robot.getTileY() - 1 < 0 || robot.getTileY() - 1 >= board.getHeight())) {
                    result = null;
                    break;
                }
                result = board.getTile(robot.getTileX(), robot.getTileY() - 1);
                break;
            case EAST:
                if((robot.getTileX() + 1 < 0 || robot.getTileX() + 1 >= board.getWidth()
                        || robot.getTileY() < 0 || robot.getTileY() >= board.getHeight())) {
                    result = null;
                    break;
                }
                result =  board.getTile(robot.getTileX() + 1, robot.getTileY());
                break;
            case NORTH:
                if((robot.getTileX() < 0 || robot.getTileX() >= board.getWidth()
                        || robot.getTileY() + 1 < 0 || robot.getTileY() + 1 >= board.getHeight())) {
                    result = null;
                    break;
                }
                result = board.getTile(robot.getTileX() , robot.getTileY() + 1);
                break;
            default:
                result = null;
                break;
        }
        return result;
    }


    /**
     * This method moves or rotates robots according to the card given. If a card has value 3, the robot moves 3 tiles
     * in the direction it's facing. If it has rotate value then it rotates.
     * @param card A program card that determines how to move the robot.
     * @param robot Robot that gets moved.
     */
    public void cardMove(NonTextureProgramCard card, Robot robot){
        if(robot.isDestroyed()) return;
        if (card.getValue() > 0) {
            for (int i = 0; i < card.getValue(); i++) {
                if(robot.isDestroyed()) break;
                //Move robot
                moveRobot(robot, robot.getDirection());
            }
        }
        else if (card.getRotate()) {
            if (card.getRotateLeft()) {
                robot.rotateLeft();
            } else if (card.getRotateRight()) {
                robot.rotateRight();
            } else {
                robot.rotateRight();
                robot.rotateRight();
            }
        }
        else if(card.getValue() == -1){
            if (!wallCollision(robot, robot.getDirection()) && !robotCollision(robot, robot.getDirection())) {
                board.moveObject(robot, oppositeDirection(robot.getDirection()));
            }
        }
    }

    /**
     * This method moves a robot one move in a direction
     * @param robot The robot that is going to move
     * @param direction The direction the robot is moving
     * @return Returns true if the robot moved
     */
    public boolean moveRobot(Robot robot, Direction direction) {
        if (!wallCollision(robot, direction) && !robotCollision(robot, direction)) {
            board.moveObject(robot, direction);
            try {
                Thread.sleep(300);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }else if(robotCollision(robot, direction)){
            Robot nextRobot = (Robot) nextTile(robot, direction).getObjects()[2];
            if(moveRobot(nextRobot, direction)){
               board.moveObject(robot, direction);
            }
        }

        if (!robot.isDestroyed()){
            if (board.getTile(robot.getTileX(), robot.getTileY()).getObjects()[0] instanceof Pit) {
                board.removeObject(robot);
                robot.destroy();
            }
        }

        return !wallCollision(robot, direction);
    }

    public boolean getGameIsDone(){
        return gameIsDone;
    }

    public void clearAllArraysOfCoordinates(){
        allArraysOfCoordinates.clear();
    }

    public ArrayList<ArrayList<int[]>> getAllArraysOfCoordinates(){
        return allArraysOfCoordinates;
    }

    /**
     * Interrupts the thread that {@link TurnHandler#doTurn()} runs on, so that the program manages to close.
     */
    public void dispose() {
        phase.interrupt();
    }
}
