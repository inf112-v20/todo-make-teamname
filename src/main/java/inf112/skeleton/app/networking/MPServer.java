package inf112.skeleton.app.networking;

import com.esotericsoftware.kryo.Kryo;
import com.esotericsoftware.kryonet.Server;

import java.io.IOException;
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.Arrays;

/**
 * The MultiPlayerServer class creates a Kryonet server that can receive and send data from and to clients.
 * Use run() to start the server
 */
public class MPServer implements Runnable{
    //Connection info
    int ServerPort = 54777;
    private InetAddress address;

    //Kryonet server
    Server server;
    ServerNetworkListener snl;

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
        kryo.register(String[].class);
        kryo.register(boolean.class);
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
        snl = new ServerNetworkListener(server);

        server.addListener(snl);
        try {
            server.bind(54555, 54777);
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


}
