package networking;

import com.esotericsoftware.kryo.Kryo;
import com.esotericsoftware.kryonet.Client;

import java.io.IOException;
import java.net.InetAddress;
import java.util.Scanner;

public class MPClient {
    //Connection Stuff
    int portSocket = 54777;
//    InetAddress IPAddress;
    String IPAddress;
    public Client client;
    private ClientNetworkListener cnl;

    private Scanner sc = new Scanner(System.in);

    public MPClient(){
        client = new Client();
        cnl = new ClientNetworkListener();

        cnl.init(client);
        registerPackets();
        client.addListener(cnl);

        client.start();
        IPAddress = "localhost";
//        IPAddress = client.discoverHost(54777, 5000);
        try {
            client.connect(5000, IPAddress, portSocket);
        }catch (IOException e){
            e.printStackTrace();
        }
        while(true){
            String message = sc.nextLine();
            if(message.equals("Quit")) break;
            cnl.sendInfo(message);
        }
    }
    private void registerPackets(){
        Kryo kryo = client.getKryo();
        kryo.register(Packets.Packet01Message.class);
    }

    public static void main(String[] args) {
        new MPClient();
    }
}
