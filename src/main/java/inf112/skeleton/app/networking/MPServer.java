package inf112.skeleton.app.networking;

import com.esotericsoftware.kryo.Kryo;
import com.esotericsoftware.kryonet.Server;

import java.io.IOException;
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.Arrays;

/**
 * The MultiPlayerServer class creates a Kryonet server that can receive and send data from and to clients. <BR>
 * Uses mainly TCP. <BR>
 * Use run() to start the server
 */
public class MPServer implements Runnable{
    private String board = "riskyexchange";
    //Connection info
    int udp;
    int tcp;
    private InetAddress address;

    //Kryonet server
    Server server;
    ServerNetworkListener snl;

    /**
     * An extra constructor if you want to use specific ports
     * @param udp UDP port
     * @param tcp TCP port
     */
    public MPServer(int udp, int tcp){
        this.udp = udp;
        this.tcp = tcp;
    }

    public MPServer(String board){
        this.board = board;
        tcp = 54555;
        udp = 54777;
    }
    /**
     * Register the classes sent over the server.
     */
    private void registerPackets(){
        Kryo kryo = server.getKryo();
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
     * Initializes the server and binds it to an IP Address, then starts and saves the IP Address.
     */
    @Override
    public void run() {
        server = new Server();
        snl = new ServerNetworkListener(server, board);

        server.addListener(snl);
        try {
            server.bind(tcp, udp);
        } catch (IOException e) {
            e.printStackTrace();
        }
        registerPackets();
        server.start();
        try {
            address = InetAddress.getLocalHost();
            System.out.println(address);
        } catch (UnknownHostException e) {
            e.printStackTrace();
        }
    }

    /**
     *
     * @return Returns the IP Address of the server
     */
    public InetAddress getAddress() {
        return address;
    }

    /**
     *
     * @return Returns the last cards received at the server, used in testing
     */
    public ArrayList<int[]> getLastCards(){
        if (snl.getReceivedCards().isEmpty()) return null;
        return new ArrayList<>(Arrays.asList(snl.getReceivedCards().get(snl.getReceivedCards().size() - 1).programCards));
    }

    public void dispose(){
        try {
            server.dispose();
        } catch (Exception e) {
        }
    }

}
