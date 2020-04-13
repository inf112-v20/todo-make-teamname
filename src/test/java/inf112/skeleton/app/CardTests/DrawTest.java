package inf112.skeleton.app.CardTests;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.backends.headless.HeadlessApplication;
import com.badlogic.gdx.backends.headless.HeadlessApplicationConfiguration;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import inf112.skeleton.app.EmptyApplicationListener;
import inf112.skeleton.app.objects.cards.ProgramCard;
import inf112.skeleton.app.objects.player.Player;
import org.junit.After;
import org.junit.BeforeClass;
import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.mock;

public class DrawTest {

    private static Player player;

    @BeforeClass
    public static void setUp() {
        HeadlessApplicationConfiguration conf = new HeadlessApplicationConfiguration();
        new HeadlessApplication(new EmptyApplicationListener(), conf);
        Gdx.gl = mock(GL20.class);
        Texture mockTexture = mock(Texture.class);
        Texture[] mockImages = {mockTexture};
        player = new Player(mockImages);
    }

    @After
    public void cleanHand(){
        player.discard();
        player.getRobot().fullHealth();
        player.clearLockedCards();
    }

    @Test
    public void drawNine(){
        player.deal();
        assertEquals(9, player.getCards().length);
    }

    @Test
    public void drawLessThanFive(){
        player.deal();
        for (int i = 0; i < 5; i++) {
            player.addSelectedCard(i);
        }
        for (int i = 0; i < 5; i++) {
            player.getRobot().takeDamage();
        }
        player.discard();
        assertEquals(1, player.getLockedCards().size());
        player.deal();
        assertEquals(4, player.getCards().length);
        for (int i = 0; i < player.getCards().length; i++) {
            player.addSelectedCard(i);
        }
        ProgramCard[] register = player.getArrayCards();
        assertEquals(5, register.length);
        assertEquals(player.getLockedCards().get(0), register[4]);
    }

    @Test
    public void twoLockedCards(){
        player.deal();
        for (int i = 0; i < 5; i++) {
            player.addSelectedCard(i);
        }
        for (int i = 0; i < 6; i++) {
            player.getRobot().takeDamage();
        }
        player.discard();
        assertEquals(2, player.getLockedCards().size());
        player.deal();
        assertEquals(3, player.getCards().length);
        for (int i = 0; i < player.getCards().length; i++) {
            player.addSelectedCard(i);
        }
        ProgramCard[] register = player.getArrayCards();
        assertEquals(5, register.length);
        assertEquals(player.getLockedCards().get(0), register[4]);
        assertEquals(player.getLockedCards().get(1), register[3]);

    }
}
