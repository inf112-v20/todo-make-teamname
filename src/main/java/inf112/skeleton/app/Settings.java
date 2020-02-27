package inf112.skeleton.app;

import com.badlogic.gdx.Screen;

public class Settings {
    public static final int SCREEN_WIDTH = 1280;
    public static final int SCREEN_HEIGHT = 720;
    public static final int TILE_WIDTH = SCREEN_WIDTH/40;
    public static final int TILE_HEIGHT = (int) (SCREEN_HEIGHT/22.5);

    public static final int BOARD_WIDTH = 512;
    public static final int BOARD_HEIGHT = 384;
    public static final int BOARD_LOC_X = (SCREEN_WIDTH/2) - (BOARD_WIDTH/2);
    public static final int BOARD_LOC_Y = (SCREEN_HEIGHT-BOARD_HEIGHT);
    public static final int CARD_WIDTH = (int) (SCREEN_WIDTH/10);
    public static final int CARD_HEIGHT = (int)(CARD_WIDTH*1.6);

}
