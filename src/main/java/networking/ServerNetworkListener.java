package networking;

import com.esotericsoftware.kryonet.Connection;
import com.esotericsoftware.kryonet.Listener;

public class ServerNetworkListener extends Listener {

    public ServerNetworkListener(){

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
        }
    }
}
