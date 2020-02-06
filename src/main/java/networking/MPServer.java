package networking;

import com.esotericsoftware.kryo.Kryo;
import com.esotericsoftware.kryonet.Server;
import com.jcraft.jogg.Packet;

import java.io.IOException;
import java.net.InetAddress;
import java.net.UnknownHostException;

public class MPServer {
    //Connection info
    int ServerPort = 54777;

    //Kryonet server
    Server server;
    ServerNetworkListener snl;

    public MPServer(){
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
            System.out.println(InetAddress.getLocalHost());
        } catch (UnknownHostException e) {
            e.printStackTrace();
        }
    }

    private void registerPackets(){
        Kryo kryo = server.getKryo();
        kryo.register(Packets.Packet01Message.class);
        kryo.register(Packets.Packet02BoardInfo.class);

    }
    public static void main(String[] args) {
        new MPServer();

    }
}
