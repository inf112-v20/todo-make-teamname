package inf112.skeleton.app.networking;


import com.esotericsoftware.kryo.Kryo;
import com.esotericsoftware.kryonet.Client;
import inf112.skeleton.app.main.Game;
import inf112.skeleton.app.objects.cards.CardTranslator;
import inf112.skeleton.app.objects.cards.NonTextureProgramCard;
import inf112.skeleton.app.objects.cards.ProgramCard;


import java.io.IOException;
import java.net.InetAddress;


public class MPClient {

    public Client client;
    private ClientNetworkListener cnl;



    public MPClient(InetAddress ipAddress, Game game){
        client = new Client();
        cnl = new ClientNetworkListener();

        cnl.init(client, game);
        registerPackets();
        client.addListener(cnl);


        new Thread(client).start();

        try {
            client.connect(5000, ipAddress, 54555, 54777);
        }catch (IOException e){
            e.printStackTrace();
        }
    }
    public MPClient(String ipAddress, Game game){
        client = new Client();
        cnl = new ClientNetworkListener();

        cnl.init(client, game);
        registerPackets();
        client.addListener(cnl);


        new Thread(client).start();

        try {
            client.connect(5000, ipAddress, 54555, 54777);
        }catch (IOException e){
            e.printStackTrace();
        }
    }
    private void registerPackets(){
        Kryo kryo = client.getKryo();
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

    public void sendMessage(String message){
            cnl.sendInfo(message);
    }

    public void sendCards(ProgramCard[] programCards){
        cnl.sendCards(programCards);
    }

    public NonTextureProgramCard[] getLastCardTransfer(){
        NonTextureProgramCard[] cards = new NonTextureProgramCard[cnl.getCards().programCards.length];
        for (int i = 0; i < cnl.getCards().programCards.length; i++) {
            cards[i] = CardTranslator.intToProgramCard(cnl.getCards().programCards[i]);
        }
        return cards;
    }

    public int getId(){
        return client.getID();
    }

    public boolean getConnection(){
        return cnl.getConnection();
    }

    public void sendStartSignal() {
        cnl.sendStartSignal();
    }

    public void sendName(String text) {
        Packets.Packet05Name name = new Packets.Packet05Name();
        name.name = new String[]{text};
        name.playerId = client.getID();
        cnl.sendName(name);
    }
}
