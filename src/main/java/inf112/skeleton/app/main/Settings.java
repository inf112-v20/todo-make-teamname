package inf112.skeleton.app.main;

public class Settings {
    public static int SCREEN_WIDTH = 1280;
    public static int SCREEN_HEIGHT = 720;
    public static int TILE_WIDTH = SCREEN_WIDTH/40;
    public static int TILE_HEIGHT = (int) (SCREEN_HEIGHT/22.5);

    public static int BOARD_WIDTH = 512;
    public static int BOARD_HEIGHT = 384;
    public static int BOARD_LOC_X = (SCREEN_WIDTH/2) - (BOARD_WIDTH/2);
    public static int BOARD_LOC_Y = (SCREEN_HEIGHT-BOARD_HEIGHT);
    public static int CARD_WIDTH = (int)(SCREEN_WIDTH/10);
    public static int CARD_HEIGHT = (int)(CARD_WIDTH*1.6);

    public static void resize(int width, int height) {
        SCREEN_WIDTH = width;
        SCREEN_HEIGHT = height;

        TILE_WIDTH = SCREEN_WIDTH/40;
        TILE_HEIGHT = (int) (SCREEN_HEIGHT/22.5);
        BOARD_LOC_X = (SCREEN_WIDTH/2) - (BOARD_WIDTH/2);
        BOARD_LOC_Y = (SCREEN_HEIGHT-BOARD_HEIGHT);
        CARD_WIDTH = (int)(SCREEN_WIDTH/10);
        CARD_HEIGHT = (int)(CARD_WIDTH*1.6);
    }
}
