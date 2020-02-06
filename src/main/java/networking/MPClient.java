package networking;

import com.esotericsoftware.kryo.Kryo;
import com.esotericsoftware.kryonet.Client;
import com.esotericsoftware.minlog.Log;

import java.io.IOException;
import java.net.InetAddress;
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
            client.connect(5000, ip, 54555, 54777);
        }catch (IOException e){
            e.printStackTrace();
        }
        while(true){
            String message = sc.nextLine();
            if(message.equals("quit")) break;
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
