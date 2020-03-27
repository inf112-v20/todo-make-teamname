package inf112.skeleton.app.main;

import com.badlogic.gdx.backends.lwjgl.LwjglApplication;
import com.badlogic.gdx.backends.lwjgl.LwjglApplicationConfiguration;

public class Main {
    public static void main(String[] args) {
        LwjglApplicationConfiguration cfg = new LwjglApplicationConfiguration();
        cfg.title = "RoboRally :)";
        cfg.width = Settings.SCREEN_WIDTH;
        cfg.height = Settings.SCREEN_HEIGHT;
        cfg.resizable = true; //temporarly
        //new LwjglApplication(new HelloWorld());
        new LwjglApplication(new ScreenHandler(), cfg);
    }
}