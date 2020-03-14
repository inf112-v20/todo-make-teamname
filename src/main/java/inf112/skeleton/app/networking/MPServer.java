package inf112.skeleton.app.networking;

import com.badlogic.gdx.Files;
import com.badlogic.gdx.backends.lwjgl.LwjglFileHandle;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.glutils.FileTextureData;
import com.badlogic.gdx.utils.Queue;
import com.esotericsoftware.kryo.Kryo;
import com.esotericsoftware.kryonet.Server;
import inf112.skeleton.app.objects.cards.CardTranslator;
import inf112.skeleton.app.objects.cards.ProgramCard;

import java.io.File;
import java.io.IOException;
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.Arrays;

public class MPServer implements Runnable{
    //Connection info
    int ServerPort = 54777;
    private InetAddress address;

    //Kryonet server
    Server server;
    ServerNetworkListener snl;


    public MPServer(){
    }

    private void registerPackets(){
        Kryo kryo = server.getKryo();
        kryo.register(Packets.Packet01Message.class);
        kryo.register(Packets.Packet02Cards.class);
        kryo.register(Packets.Packet03PlayerNr.class);
        kryo.register(int.class);
        kryo.register(int[].class);
        kryo.register(int[][].class);

    }
    @Override
    public void run() {
        server = new Server();
        snl = new ServerNetworkListener(server);

        server.addListener(snl);
        try {
            server.bind(54555, 54777);
        } catch (IOException e) {
            e.printStackTrace();
        }
        registerPackets();
        server.start();
        try {
            address = InetAddress.getLocalHost();
            System.out.println(address);
        } catch (UnknownHostException e) {
            e.printStackTrace();
        }
    }

    public InetAddress getAddress() {
        return address;
    }

    public ArrayList<int[]> getLastCards(){
        ArrayList<int[]> result = new ArrayList<>();
        for (Packets.Packet02Cards packet: snl.getReceivedCards()) {
            result.addAll(Arrays.asList(packet.programCards));

        }
        return result;
    }

    public void dispose(){
        try {
            server.dispose();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
