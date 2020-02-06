package networking;

import com.esotericsoftware.kryo.Kryo;
import com.esotericsoftware.kryonet.Client;
import inf112.skeleton.app.Card;

import java.io.IOException;
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.Scanner;

public class MPClient {
    //Connection Stuff
    int portSocket = 54777;
    InetAddress IPAddress;

    public Client client;
    private ClientNetworkListener cnl;

    private Scanner sc = new Scanner(System.in);

    public MPClient(){
        client = new Client();
        cnl = new ClientNetworkListener();

        cnl.init(client);
        registerPackets();
        client.addListener(cnl);

        String ip;
        Scanner sc = new Scanner(System.in);
        System.out.println("Whats the ip to your host?");
        ip = sc.nextLine();

        client.start();
        try {
            System.out.println(InetAddress.getLocalHost());
        } catch (UnknownHostException e) {
            e.printStackTrace();
        }
        try {
            client.connect(5000, ip, 54555, 54777);
        }catch (IOException e){
            e.printStackTrace();
        }
    }
    private void registerPackets(){
        Kryo kryo = client.getKryo();
        kryo.register(Packets.Packet01Message.class);
        kryo.register(Packets.Packet02Cards.class);
    }

    public void sendMessage(String message){
            cnl.sendInfo(message);
    }

    public void sendCards(Card[] cards, int playerNr){
        cnl.sendCards(cards, playerNr);
    }

    public static void main(String[] args) {
        new MPClient();
    }
}
