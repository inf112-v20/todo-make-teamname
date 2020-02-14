package networking;

import com.esotericsoftware.kryonet.Client;
import com.esotericsoftware.kryonet.Connection;
import com.esotericsoftware.kryonet.Listener;
import inf112.skeleton.app.objects.ProgramCard;

public class ClientNetworkListener extends Listener {
    private Client client;
    Packets.Packet01Message firstMessage;

    public void init(Client client){
        this.client = client;
        firstMessage = new Packets.Packet01Message();
        firstMessage.clientName = "Erik";
    }

    public void connected(Connection c){
        System.out.println("[CLIENT] >> You have connected.");

        firstMessage.message = "Hello, Server. How are you?";
        client.sendTCP(firstMessage);
    }

    public void sendInfo(String message){
        firstMessage.message = message;
        client.sendTCP(firstMessage);
        System.out.println("["+ firstMessage.clientName + "] >> Sent : [" + message +"] to server.");
    }

    public void sendCards(ProgramCard[] programCards, int playerNr){
        Packets.Packet02Cards newCards = new Packets.Packet02Cards();
        newCards.programCards = programCards;
        newCards.playerNr = playerNr;
        client.sendTCP(newCards);
    }

    public void disconnected(Connection c){
        System.out.println("[CLIENT] >> You have disconnected.");
    }

    public void received(Connection c, Object o){
        if(o instanceof Packets.Packet01Message){
            Packets.Packet01Message p = (Packets.Packet01Message) o;
            System.out.println("["+ p.clientName +"] << " + p.message);
        }
    }
}
