package inf112.skeleton.app.main;


import inf112.skeleton.app.board.Board;
import inf112.skeleton.app.board.BoardTile;
import inf112.skeleton.app.networking.MPClient;
import inf112.skeleton.app.networking.Packets;
import inf112.skeleton.app.objects.boardObjects.*;
import inf112.skeleton.app.objects.cards.CardTranslator;
import inf112.skeleton.app.objects.cards.NonTextureProgramCard;
import inf112.skeleton.app.objects.player.Player;
import inf112.skeleton.app.objects.player.Robot;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.concurrent.Semaphore;

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
                //TODO Robots hit each other
                for (int id: idPlayerHash.keySet()) {
                    pickUpFlag(idPlayerHash.get(id));
                }
                for (int id: idPlayerHash.keySet()) {

                    repair(idPlayerHash.get(id));
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
            //TODO remove register etc...
            for (int id: idPlayerHash.keySet()) {
                cleanUp(idPlayerHash.get(id));
            }
            game.clearAllCards();
        }
    }

    /**
     * The pitFall method checks if the robot falls into a pit, and if it does it destroys the robot
     * @param robot This is the robot that gets check if it is on a pit.
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
        Robot robot = myPlayer.getRobot();
        if(robot.isDestroyed())return;
        BoardTile currentTile = board.getTile(robot.getTileX(), robot.getTileY());
        if (currentTile.getObjects()[0] instanceof Flag) {
            Flag flag = (Flag) currentTile.getObjects()[0];
            if (!myPlayer.getFlags().contains(flag) && myPlayer.getFlags().size() + 1 == flag.getNr()) {
                myPlayer.addFlag(flag);
                if (myPlayer.getFlags().size() == board.getFlagNr()) {
                    //TODO endscreen
                    ScreenHandler.changeScreenState(ScreenState.MAINMENU);
                    gameIsDone = true;
                }
            }
        }
    }

    /**
     * Board lasers shoot, if a robot is in the path of a laser it gets hit and takes damage.
     * @param robot This is the robot that gets checked.
     */
    public void boardLasersShoot(Robot robot) {
        if(robot.isDestroyed())return;
        BoardTile currentTile = board.getTile(robot.getTileX(), robot.getTileY());
        if (currentTile.getObjects()[1] instanceof BoardLaser) {
            robot.takeDamage();
        }
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
            board.moveObject(robot, pusher.getDirection());
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
            if (conveyorBelt.getExpress()) {
                //Expressconveoyrbelt moves robot
                board.moveObject(robot, conveyorBelt.getDirection());
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
            board.moveObject(robot, conveyorBelt.getDirection());
            try {
                Thread.sleep(100);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    /**
     * Boolean method that checks if there is a wall where the robot wants to move
     */
    private boolean wallCollision(Robot robot){
        BoardTile currentTile = board.getTile(robot.getTileX(), robot.getTileY());
        if (currentTile.getObjects()[0] instanceof Wall) {
            Wall wall = (Wall) currentTile.getObjects()[0];
            if (wall.getDirection() == robot.getDirection()){
                return true;
            }
            return false;
        }
        return false;
    }


    /**
     * This method moves or rotates robots according to the card given. If a card has value 3, the robot moves 3 tiles
     * in the direction it's facing. If it has rotate value then it rotates.
     * @param card A program card that determines how to move the robot.
     * @param robot Robot that gets moved.
     */
    public void cardMove(NonTextureProgramCard card, Robot robot){
        if (card.getValue() > 0) {
            for (int i = 0; i < card.getValue(); i++) {
                if(robot.isDestroyed()) break;
                //Move robot
                if (!wallCollision(robot)) board.moveObject(robot, robot.getDirection());
                if (!robot.isDestroyed()){
                    if (board.getTile(robot.getTileX(), robot.getTileY()).getObjects()[0] instanceof Pit) {
                        board.removeObject(robot);
                        robot.destroy();
                    }
                }
                try {
                    Thread.sleep(300);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
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
    }
    public boolean getGameIsDone(){
        return gameIsDone;
    }
    /**
     * Interrupts the thread that {@link TurnHandler#doTurn()} runs on, so that the program manages to close.
     */
    public void dispose() {
        phase.interrupt();
    }
}
