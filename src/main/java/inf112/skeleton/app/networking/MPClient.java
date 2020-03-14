package inf112.skeleton.app.networking;


import com.esotericsoftware.kryo.Kryo;
import com.esotericsoftware.kryonet.Client;
import inf112.skeleton.app.main.Game;
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
}
