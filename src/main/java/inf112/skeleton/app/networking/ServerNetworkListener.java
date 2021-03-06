package inf112.skeleton.app.networking;

import com.esotericsoftware.kryonet.Connection;
import com.esotericsoftware.kryonet.Listener;
import com.esotericsoftware.kryonet.Server;
import java.util.ArrayList;

/**
 * This listener class receives data from clients and then sends it to all clients.
 */
public class ServerNetworkListener extends Listener {
    private final String board;
    private Server server;
    private boolean[] allReady;
    private ArrayList<Packets.Packet02Cards> receivedCards;
    private ArrayList<Packets.Packet02Cards> copyReceivedCards;
    private String[] names;
    private int playerNr = 0;
    private boolean[] playersShutdown;

    /**
     * Sets the field variable server to the Kryonet server, then initializes some lists for storing data.
     * @param server The Kryonet server being used
     */
    public ServerNetworkListener(Server server , String board){
        this.server = server;
        this.board = board;
        names = new String[5];
        receivedCards = new ArrayList<>();
        copyReceivedCards = new ArrayList<>();
        allReady = new boolean[5];
        playersShutdown = new boolean[5];
        for (int i = 1; i < 5; i++) {
            allReady[i] = false;
            playersShutdown[i] = false;
        }
        allReady[1] = true;
    }

    /**
     * When a client connects to the Kryonet server the method gets called, and then prints a message to the console.<BR>
     * Afterwards it updates which players are on the server, and sends that number to all clients.
     * @param c
     */
    public void connected(Connection c){
        System.out.println("Player: " + (playerNr + 1) + " has connected");
        playerNr++;
        Packets.Packet03PlayerNr nrOfPlayers = new Packets.Packet03PlayerNr();
        nrOfPlayers.playerNr = playerNr;
        server.sendToAllTCP(nrOfPlayers);
        server.sendToTCP(c.getID(), board);

    }

    /**
     * Whenever a client disconnects from the server this method gets called, prints to console which player
     * disconnected.
     * @param c
     */
    public void disconnected(Connection c){
        System.out.println("Player: " + c.getID() + " has disconnected");
        playerNr--;
        names[c.getID()] = null;
        Packets.Packet03PlayerNr nrOfPlayers = new Packets.Packet03PlayerNr();
        nrOfPlayers.playerNr = playerNr;
        server.sendToAllTCP(nrOfPlayers);
        Packets.Packet05Name name = new Packets.Packet05Name();
        name.name = names;
        server.sendToAllTCP(name);
    }

    /**
     * This method gets called by the Kryonet server whenever something is sent to the server, this method then
     * sort out what type of message was sent and sends it to all clients.
     * @param c
     * @param o
     */
    public void received(Connection c, Object o){
        if(o instanceof Packets.Packet01Message){
            Packets.Packet01Message p = (Packets.Packet01Message) o;
            server.sendToAllTCP(p);
        }else if (o instanceof Packets.Packet02Cards){
            Packets.Packet02Cards cards = (Packets.Packet02Cards) o;
            receivedCards.add(cards);
            copyReceivedCards.add(cards);
            if(receivedCards.size() == playerNr){
                for (Packets.Packet02Cards packet: receivedCards) {
                    server.sendToAllTCP(packet);
                }
                receivedCards.clear();
            }
        }else if(o instanceof Packets.Packet04StartSignal){
            Packets.Packet04StartSignal packet04StartSignal = (Packets.Packet04StartSignal) o;
            server.sendToAllTCP(packet04StartSignal);
        }else if (o instanceof Packets.Packet05Name){
            Packets.Packet05Name name = (Packets.Packet05Name) o;
            names[c.getID()] = name.name[0];
            name.name = names;
            server.sendToAllTCP(name);
        }else if(o instanceof Packets.Packet06ReadySignal){
            Packets.Packet06ReadySignal ready = (Packets.Packet06ReadySignal) o;
            allReady[c.getID()] = ready.signal;
            ready.allReady = allReady;
            server.sendToAllTCP(ready);
        }else if(o instanceof Packets.Packet07ShutdownRobot){
            Packets.Packet07ShutdownRobot shutdownRobot = (Packets.Packet07ShutdownRobot) o;
            playersShutdown[c.getID()] = true;
            shutdownRobot.playersShutdown = playersShutdown;
            server.sendToAllTCP(shutdownRobot);
            playersShutdown[c.getID()] = false;
        }else if(o instanceof Packets.Packet08RemovePlayer){
            playerNr--;
            Packets.Packet03PlayerNr nrOfPlayers = new Packets.Packet03PlayerNr();
            nrOfPlayers.playerNr = playerNr;
            server.sendToAllTCP(nrOfPlayers);
        }
    }

    /**
     * For now only used in testing, to confirm that the right cards where sent, and received.<BR>
     * @return Returns up to the last 5 sets of cards sent.
     */
    public ArrayList<Packets.Packet02Cards> getReceivedCards(){
        if(copyReceivedCards.size() > 4){
            copyReceivedCards.clear();
        }
        return copyReceivedCards;
    }
}
