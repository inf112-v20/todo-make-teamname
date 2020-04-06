package inf112.skeleton.app.networking;



public class Packets {
    public static class Packet01Message{ public String message;}
    public static class Packet02Cards{ public int[][] programCards; public int playerId;}
    public static class Packet03PlayerNr{ public int playerNr;}
    public static class Packet04StartSignal{public boolean start;}
    public static class Packet05Name{ public String[] name; public int playerId;}
    public static class Packet06ReadySignal{ public boolean signal; public boolean[] allReady;}
    public static class Packet07ShutdownRobot{ public boolean[] playersShutdown;}

}
