package networking;

import inf112.skeleton.app.IBoardObject;

public class Packets {
    public static class Packet01Message{ public String message; public String clientName;}
    public static class Packet02BoardInfo{ public IBoardObject object; public int playerNr;}

}
