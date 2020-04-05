package inf112.skeleton.app.networking;

import com.esotericsoftware.kryonet.Client;
import com.esotericsoftware.kryonet.Connection;
import com.esotericsoftware.kryonet.Listener;
import inf112.skeleton.app.main.Game;
import inf112.skeleton.app.objects.cards.CardTranslator;
import inf112.skeleton.app.objects.cards.ProgramCard;

/**
 * This Listener class sends and receives data to and from the server.<BR> It also calls on methods in game to send
 * data to game.<BR> For example game.receiveStart() to start the "playing" part of the game.
 */
public class ClientNetworkListener extends Listener {
    private Client client;
    private Game game;
    private Packets.Packet01Message firstMessage;
    private Packets.Packet02Cards cards;
    private boolean connection = false;

    /**
     * Initializes the listener.
     * @param client The Kryonet client connected to the server.
     * @param game The Game class that is being played.
     */
    public void init(Client client, Game game){
        this.client = client;
        this.game = game;
        firstMessage = new Packets.Packet01Message();
        cards = new Packets.Packet02Cards();
    }

    /**
     * Gets called by the Kryonet client, prints a message that you have connected, sends a message to the server
     * and sets connection to true.
     * @param c
     */
    public void connected(Connection c){
        System.out.println("[CLIENT] >> You have connected.");

        firstMessage.message = "Hello, Server. How are you?";
        client.sendTCP(firstMessage);
        connection = true;
    }

    /**
     * Sends a message to the server.
     * @param message Message you want to send to the server.
     */
    public void sendInfo(String message){
        firstMessage.message = message;
        client.sendTCP(firstMessage);
    }

    /**
     * Sends an array of cards to the server to be played.
     * @param programCards The cards the player want to be played.
     */
    public void sendCards(ProgramCard[] programCards){
        Packets.Packet02Cards newCards = new Packets.Packet02Cards();
        newCards.programCards = new int[programCards.length][4];
        for (int i = 0; i < programCards.length; i++) {
            newCards.programCards[i] = CardTranslator.programCardsToInt(programCards[i]);
        }
        newCards.playerId = client.getID();
        client.sendTCP(newCards);
    }

    /**
     * Sends a start signal to the server alerting all clients to start the game.
     */
    public void sendStartSignal() {
        Packets.Packet04StartSignal startSignal = new Packets.Packet04StartSignal();
        startSignal.start = true;
        client.sendTCP(startSignal);
    }

    /**
     * Sends a name to the server
     * @param name A Packet with a single name in a String[]
     */
    public void sendName(Packets.Packet05Name name) {
        client.sendTCP(name);
    }

    /**
     * If the client disconnects the Kryonet client calls this method.<BR> Then sets connection to false and prints a
     * message that you have disconnected.
     * @param c
     */
    public void disconnected(Connection c){
        connection = false;
        System.out.println("[CLIENT] >> You have disconnected.");
    }

    /**
     * The Kryonet client calls this method when it receives something form the server, the this method sorts out
     * what type of information it is and sends it in the right direction in the Game class.
     * @param c
     * @param o
     */
    public void received(Connection c, Object o){
        if(o instanceof Packets.Packet01Message){
            Packets.Packet01Message packet = (Packets.Packet01Message) o;
        }else if(o instanceof Packets.Packet02Cards) {
            Packets.Packet02Cards packet = (Packets.Packet02Cards) o;
            cards = packet;
            game.isReady(packet);
        }else if(o instanceof Packets.Packet03PlayerNr){
            Packets.Packet03PlayerNr packet = (Packets.Packet03PlayerNr) o;
            game.setNrOfPlayers(packet.playerNr);
        }else if(o instanceof Packets.Packet04StartSignal){
            game.receiveStart();
        }
        else if(o instanceof Packets.Packet05Name){
            Packets.Packet05Name name = (Packets.Packet05Name) o;
            game.receiveNames(name);
        }
        else if(o instanceof Packets.Packet06ReadySignal){
            Packets.Packet06ReadySignal ready = (Packets.Packet06ReadySignal) o;
            game.receiveAllReady(ready.allReady);
        }
    }

    /**
     *
     * @return Returns the last cards sent to the server. Used in testing.
     */
    public Packets.Packet02Cards getCards() {
        return cards;
    }

    /**
     *
     * @return Returns true if connected to a server.
     */
    public boolean getConnection(){
        return connection;
    }

    public void sendReady(Packets.Packet06ReadySignal signal) {
        client.sendTCP(signal);
    }
}
