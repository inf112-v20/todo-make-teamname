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
    private Packets.Packet01Message firstMessage;
    private Packets.Packet02Cards cards;
    private boolean connection = false;

    public void init(Client client, Game game){
        this.client = client;
        this.game = game;
        firstMessage = new Packets.Packet01Message();
        cards = new Packets.Packet02Cards();
    }

    public void connected(Connection c){
        System.out.println("[CLIENT] >> You have connected.");

        firstMessage.message = "Hello, Server. How are you?";
        client.sendTCP(firstMessage);
        connection = true;
    }

    public void sendInfo(String message){
        firstMessage.message = message;
        client.sendTCP(firstMessage);
    }

    public void sendCards(ProgramCard[] programCards){
        Packets.Packet02Cards newCards = new Packets.Packet02Cards();
        newCards.programCards = new int[programCards.length][4];
        for (int i = 0; i < programCards.length; i++) {
            newCards.programCards[i] = CardTranslator.programCardsToInt(programCards[i]);
        }
        newCards.playerId = client.getID();
        client.sendTCP(newCards);
    }
    public void sendStartSignal() {
        Packets.Packet04StartSignal startSignal = new Packets.Packet04StartSignal();
        startSignal.start = true;
        client.sendTCP(startSignal);
    }

    public void disconnected(Connection c){
        connection = false;
        System.out.println("[CLIENT] >> You have disconnected.");
    }

    public void received(Connection c, Object o){
        if(o instanceof Packets.Packet01Message){
            Packets.Packet01Message packet = (Packets.Packet01Message) o;
        }else if(o instanceof Packets.Packet02Cards) {
            Packets.Packet02Cards packet = (Packets.Packet02Cards) o;
            cards = packet;
            game.isReady(packet);
        }else if(o instanceof Packets.Packet03PlayerNr){
            Packets.Packet03PlayerNr packet = (Packets.Packet03PlayerNr) o;
            game.setNrOfPlayers(packet.playerNr);
        }else if(o instanceof Packets.Packet04StartSignal){
            game.receiveStart();
        }
        else if(o instanceof Packets.Packet05Name){
            Packets.Packet05Name name = (Packets.Packet05Name) o;
            game.receiveNames(name);
        }
    }

    public Packets.Packet02Cards getCards() {
        //For testing purposes
        return cards;
    }

    public boolean getConnection(){
        return connection;
    }


    public void sendName(Packets.Packet05Name name) {
        client.sendTCP(name);
    }
}
