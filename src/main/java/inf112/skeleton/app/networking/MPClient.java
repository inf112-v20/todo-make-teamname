package inf112.skeleton.app.networking;


import com.esotericsoftware.kryo.Kryo;
import com.esotericsoftware.kryonet.Client;
import inf112.skeleton.app.main.Game;
import inf112.skeleton.app.objects.cards.CardTranslator;
import inf112.skeleton.app.objects.cards.NonTextureProgramCard;
import inf112.skeleton.app.objects.cards.ProgramCard;


import java.io.IOException;
import java.net.InetAddress;

/**
 * A MultiPlayerClient that connects to a server, then is able to send and receive data from the server.
 */
public class MPClient {

    public Client client;
    private ClientNetworkListener cnl;
    int udp;
    int tcp;


    /**
     * The constructor initializes a Kryonet client and a ClientNetworkListener, registers the classes sent over the
     * server, starts the client and finally tries to connect to the server.
     * @param ipAddress The server address
     * @param game The game it wants to send data from to the server, and send data to from the server.
     */
    public MPClient(InetAddress ipAddress, Game game){
        client = new Client();
        cnl = new ClientNetworkListener();
        tcp = 54555;
        udp = 54777;

        cnl.init(client, game);
        registerPackets();
        client.addListener(cnl);


        new Thread(client).start();

        try {
            client.connect(5000, ipAddress, tcp, udp);
        }catch (IOException e){
            e.printStackTrace();
        }
    }

    public MPClient(InetAddress ipAddress, Game game, int udp, int tcp){
        client = new Client();
        cnl = new ClientNetworkListener();
        this.udp = udp;
        this.tcp = tcp;

        cnl.init(client, game);
        registerPackets();
        client.addListener(cnl);


        new Thread(client).start();

        try {
            client.connect(5000, ipAddress, tcp, udp);
        }catch (IOException e){
            e.printStackTrace();
        }
    }
    /**
     * The constructor initializes a Kryonet client and a ClientNetworkListener, registers the classes sent over the
     * server, starts the client and finally tries to connect to the server.
     * @param game The game it wants to send data from to the server, and send data to from the server.
     */
    public MPClient(Game game){
        client = new Client();
        cnl = new ClientNetworkListener();
        tcp = 54555;
        udp = 54777;

        cnl.init(client, game);
        registerPackets();
        client.addListener(cnl);

    }

    public boolean connect(String ipAddress) {
        new Thread(client).start();
        try {
            client.connect(5000, ipAddress, tcp, udp);
            return true;
        }catch (Exception e){
            e.printStackTrace();
            return false;
        }
    }

    /**
     * Register the classes sent over the server.
     */
    private void registerPackets(){
        Kryo kryo = client.getKryo();
        kryo.register(Packets.Packet01Message.class);
        kryo.register(Packets.Packet02Cards.class);
        kryo.register(Packets.Packet03PlayerNr.class);
        kryo.register(Packets.Packet04StartSignal.class);
        kryo.register(Packets.Packet05Name.class);
        kryo.register(Packets.Packet06ReadySignal.class);
        kryo.register(Packets.Packet07ShutdownRobot.class);
        kryo.register(Packets.Packet08RemovePlayer.class);
        kryo.register(String[].class);
        kryo.register(boolean.class);
        kryo.register(boolean[].class);
        kryo.register(int.class);
        kryo.register(int[].class);
        kryo.register(int[][].class);
    }

    /**
     * Sends a message to the server
     * @param message String message that will be sent to server and broadcast to everybody.
     */
    public void sendMessage(String message){
            cnl.sendInfo(message);
    }

    /**
     * Sends an array of ProgramCards to the server.
     * @param programCards The cards that the player wants to play.
     */
    public void sendCards(ProgramCard[] programCards){
        cnl.sendCards(programCards);
    }

    /**
     *
     * @return returns the last cards sent to the server.
     */
    public NonTextureProgramCard[] getLastCardTransfer(){
        NonTextureProgramCard[] cards = new NonTextureProgramCard[cnl.getCards().programCards.length];
        for (int i = 0; i < cnl.getCards().programCards.length; i++) {
            cards[i] = CardTranslator.intToProgramCard(cnl.getCards().programCards[i]);
        }
        return cards;
    }

    /**
     *
     * @return Returns the client id
     */
    public int getId(){
        return client.getID();
    }

    /**
     *
     * @return Returns true if client is connected to a server, else false
     */
    public boolean getConnection(){
        return cnl.getConnection();
    }

    /**
     * Sends a start signal to the server alerting all clients to start the game.
     */
    public void sendStartSignal() {
        cnl.sendStartSignal();
    }

    /**
     * Sends the username of the player to the server
     * @param text username of the player
     */
    public void sendName(String text) {
        Packets.Packet05Name name = new Packets.Packet05Name();
        name.name = new String[]{text};
        name.playerId = client.getID();
        cnl.sendName(name);
    }

    public void dispose(){
        try {
            client.dispose();
        } catch (IOException e) {
        }
    }

    /**
     * Sends a boolean to the server telling everybody that this player is ready
     * @param signal A packet with booleans
     */
    public void sendReady(Packets.Packet06ReadySignal signal) {
        cnl.sendReady(signal);
    }

    public void sendShutdownRobot() {
        cnl.sendShutdownRobot();
    }

    public void removeOnePlayerFromServer() {
        cnl.removeOnePlayerFromServer();
    }
}
