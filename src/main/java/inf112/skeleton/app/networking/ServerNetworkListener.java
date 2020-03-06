package inf112.skeleton.app.networking;

import com.esotericsoftware.kryonet.Connection;
import com.esotericsoftware.kryonet.Listener;
import com.esotericsoftware.kryonet.Server;

public class ServerNetworkListener extends Listener {
    private Server server;
    public int[] players;
    public int playerNr = 0;

    public ServerNetworkListener(Server server){
        this.server = server;
        players = new int[8];
    }

    public void connected(Connection c){
        System.out.println("Someone has connected");
        players[playerNr] = c.getID();
        server.sendToTCP(c.getID(), playerNr++);

    }

    public void disconnected(Connection c){
        System.out.println("Someone has disconnected");
    }

    public void received(Connection c, Object o){
        if(o instanceof Packets.Packet01Message){
            Packets.Packet01Message p = (Packets.Packet01Message) o;
            System.out.println("[" + p.clientName +"] >> " + p.message);
            server.sendToAllExceptTCP(c.getID(), p);
        }
    }
}
