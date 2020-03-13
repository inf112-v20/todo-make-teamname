package inf112.skeleton.app.networking;

import com.badlogic.gdx.utils.Queue;
import com.esotericsoftware.kryonet.Client;
import com.esotericsoftware.kryonet.Connection;
import com.esotericsoftware.kryonet.Listener;
import inf112.skeleton.app.main.Game;
import inf112.skeleton.app.objects.cards.CardTranslator;
import inf112.skeleton.app.objects.cards.ProgramCard;

public class ClientNetworkListener extends Listener {
    private Client client;
    private Game game;
    Packets.Packet01Message firstMessage;

    public void init(Client client, Game game){
        this.client = client;
        this.game = game;
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

    public void sendCards(ProgramCard[] programCards){
        Packets.Packet02Cards newCards = new Packets.Packet02Cards();
        newCards.programCards = new int[programCards.length][4];
        for (int i = 0; i < programCards.length; i++) {
            newCards.programCards[i] = CardTranslator.programCardsToInt(programCards[i]);
        }
        newCards.playerNr = client.getID();
        client.sendTCP(newCards);
    }

    public void disconnected(Connection c){
        System.out.println("[CLIENT] >> You have disconnected.");
    }

    public void received(Connection c, Object o){
        if(o instanceof Packets.Packet01Message){
            Packets.Packet01Message p = (Packets.Packet01Message) o;
            System.out.println("["+ p.clientName +"] << " + p.message);
        }else if(o instanceof Packets.Packet02Cards){
            Packets.Packet02Cards p = (Packets.Packet02Cards) o;
            game.isReady(p);
        }
    }
}
