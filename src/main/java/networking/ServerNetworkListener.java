package networking;

import com.esotericsoftware.kryonet.Connection;
import com.esotericsoftware.kryonet.Listener;
import com.esotericsoftware.kryonet.Server;

public class ServerNetworkListener extends Listener {
    private Server server;

    public ServerNetworkListener(Server server){
        this.server = server;
    }

    public void connected(Connection c){
        System.out.println("Someone has connected");
    }

    public void disconnected(Connection c){
        System.out.println("Someone has disconnected");
    }

    public void received(Connection c, Object o){
        if(o instanceof Packets.Packet01Message){
            Packets.Packet01Message p = (Packets.Packet01Message) o;
            System.out.println("[" + p.clientName +"] >> " + p.message);
            server.sendToAllTCP(p);
        }
    }
}
