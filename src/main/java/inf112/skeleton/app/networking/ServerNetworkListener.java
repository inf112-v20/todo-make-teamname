package inf112.skeleton.app.networking;

import com.esotericsoftware.kryonet.Connection;
import com.esotericsoftware.kryonet.Listener;
import com.esotericsoftware.kryonet.Server;

import java.util.ArrayList;
import java.util.HashMap;

public class ServerNetworkListener extends Listener {
    private Server server;
    private int[] players;
    private ArrayList<Packets.Packet02Cards> receivedCards;
    private ArrayList<Packets.Packet02Cards> copyReceivedCards;
    private String[] names;
    private int playerNr = 0;

    public ServerNetworkListener(Server server){
        this.server = server;
        players = new int[4];
        names = new String[4];
        receivedCards = new ArrayList<>();
        copyReceivedCards = new ArrayList<>();
    }

    public void connected(Connection c){
        System.out.println("Player: " + playerNr + " has connected");
        players[playerNr] = c.getID();
        playerNr++;
        Packets.Packet03PlayerNr nrOfPlayers = new Packets.Packet03PlayerNr();
        nrOfPlayers.playerNr = playerNr;
        server.sendToAllTCP(nrOfPlayers);

    }

    public void disconnected(Connection c){
        System.out.println("Player: " + c.getID() + " has disconnected");
    }

    public void received(Connection c, Object o){
        if(o instanceof Packets.Packet01Message){
            Packets.Packet01Message p = (Packets.Packet01Message) o;
            server.sendToAllExceptTCP(c.getID(), p);
        }else if (o instanceof Packets.Packet02Cards){
            Packets.Packet02Cards cards = (Packets.Packet02Cards) o;
            receivedCards.add(cards);
            copyReceivedCards.add(cards);
            if(receivedCards.size() == playerNr){
                for (Packets.Packet02Cards packet: receivedCards) {
                    server.sendToAllTCP(packet);
                }
                receivedCards.clear();
            }
        }else if(o instanceof Packets.Packet04StartSignal){
            Packets.Packet04StartSignal packet04StartSignal = (Packets.Packet04StartSignal) o;
            server.sendToAllTCP(packet04StartSignal);
        }else if (o instanceof Packets.Packet05Name){
            Packets.Packet05Name name = (Packets.Packet05Name) o;
            names[c.getID()] = name.name[0];
            name.name = names;
            server.sendToAllTCP(name);
        }
    }

    public ArrayList<Packets.Packet02Cards> getReceivedCards(){
        //For testing purposes
        return copyReceivedCards;
    }
}
