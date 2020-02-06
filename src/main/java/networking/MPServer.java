package networking;

import com.esotericsoftware.kryo.Kryo;
import com.esotericsoftware.kryonet.Server;
import com.jcraft.jogg.Packet;

import java.io.IOException;
import java.net.InetAddress;
import java.net.UnknownHostException;

public class MPServer implements Runnable{
    //Connection info
    int ServerPort = 54777;
    private InetAddress address;

    //Kryonet server
    Server server;
    ServerNetworkListener snl;


    public MPServer(){
    }

    private void registerPackets(){
        Kryo kryo = server.getKryo();
        kryo.register(Packets.Packet01Message.class);
        kryo.register(Packets.Packet02Cards.class);

    }
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
        } catch (UnknownHostException e) {
            e.printStackTrace();
        }
    }

    public InetAddress getAddress() {
        return address;
    }
}
