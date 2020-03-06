package inf112.skeleton.app.networking;

import inf112.skeleton.app.objects.cards.ProgramCard;

public class Packets {
    public static class Packet01Message{ public String message; public String clientName;}
    public static class Packet02Cards{ public ProgramCard[] programCards; public int playerNr;}
}
