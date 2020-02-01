package inf112.skeleton.app;
//commit test
import com.badlogic.gdx.backends.lwjgl.LwjglApplication;
import com.badlogic.gdx.backends.lwjgl.LwjglApplicationConfiguration;

//Commit test
public class Main {
    public static void main(String[] args) {
        LwjglApplicationConfiguration cfg = new LwjglApplicationConfiguration();
        cfg.title = "RoboRally :)";
        cfg.width = 480;
        cfg.height = 320;
        //new LwjglApplication(new HelloWorld());
        new LwjglApplication(new Game(), cfg);
    }
}