package networking;

import inf112.skeleton.app.objects.ProgramCard;

public class Packets {
    public static class Packet01Message{ public String message; public String clientName;}
    public static class Packet02Cards{ public ProgramCard[] programCards; public int playerNr;}

}
