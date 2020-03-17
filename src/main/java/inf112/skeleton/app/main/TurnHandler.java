package inf112.skeleton.app.main;

import com.jcraft.jogg.Packet;
import com.sun.deploy.cache.BaseLocalApplicationProperties;
import inf112.skeleton.app.board.Board;
import inf112.skeleton.app.board.BoardTile;
import inf112.skeleton.app.main.Game;
import inf112.skeleton.app.main.ScreenHandler;
import inf112.skeleton.app.main.ScreenState;
import inf112.skeleton.app.networking.Packets;
import inf112.skeleton.app.objects.boardObjects.*;
import inf112.skeleton.app.objects.cards.CardTranslator;
import inf112.skeleton.app.objects.cards.NonTextureProgramCard;
import inf112.skeleton.app.objects.player.Player;
import inf112.skeleton.app.objects.player.Robot;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.concurrent.Semaphore;

public class TurnHandler {

    private boolean gameIsDone;
    private Semaphore isReadySem;
    private Game game;
    private HashMap<Integer, Player> idPlayerHash;
    private Thread phase;
    private Board board;

    public void create(Game game1){
        this.game = game1;
        isReadySem = new Semaphore(0);
        gameIsDone = false;
        idPlayerHash = game.getIdPlayerHash();
        board = game.getBoard();
        phase = new Thread(this::doTurn);
        phase.start();
    }
    public void isReady(){
        isReadySem.release();
    }
    private void doTurn() {
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
                    Robot robot = idPlayerHash.get(id).getRobot();
                    pickUpFlag(robot, idPlayerHash.get(id));
                }
                for (int id: idPlayerHash.keySet()) {
                    Robot robot = idPlayerHash.get(id).getRobot();
                    repair(robot, idPlayerHash.get(id));
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
                Robot robot = idPlayerHash.get(id).getRobot();
                cleanUp(robot, idPlayerHash.get(id));
            }

        }
    }

    public void pitFall(Robot robot) {
        if (!robot.isDestroyed()) {
            if (board.getTile(robot.getTileX(), robot.getTileY()).getObjects()[0] instanceof Pit) {
                //Robot falls into pit
                board.removeObject(robot);
                robot.destroy();
            }
        }

    }

    public void cleanUp(Robot robot, Player myPlayer) {
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
        game.clearAllCards();
    }

    public void repair(Robot robot, Player myPlayer) {
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

    public void pickUpFlag(Robot robot, Player myPlayer) {
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

    public void boardLasersShoot(Robot robot) {
        if(robot.isDestroyed())return;
        BoardTile currentTile = board.getTile(robot.getTileX(), robot.getTileY());
        if (currentTile.getObjects()[1] instanceof BoardLaser) {
            robot.takeDamage();
        }
        game.setBoard(board);
    }

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

    public void pushersMove(Robot robot) {
        if(robot.isDestroyed())return;
        BoardTile currentTile = board.getTile(robot.getTileX(), robot.getTileY());
        if (currentTile.getObjects()[0] instanceof Pusher) {
            //Robot gets pushed
            Pusher pusher = (Pusher) currentTile.getObjects()[0];
            board.moveObject(robot, pusher.getDirection());
        }
    }

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

    public void cardMove(NonTextureProgramCard card, Robot robot){
        if (card.getValue() > 0) {
            for (int i = 0; i < card.getValue(); i++) {
                if(robot.isDestroyed()) break;
                //Move robot
                board.moveObject(robot, robot.getDirection());
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

    public void dispose() {
        phase.interrupt();
    }
}
