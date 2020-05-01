package inf112.skeleton.app;

import inf112.skeleton.app.objects.cards.Hitbox;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class HitboxTest {
    Hitbox testBox;
    @Before
    public void setup(){
        testBox = new Hitbox();
        testBox.setBound(0, 0,0);
        testBox.setBound(1, 1,0);
        testBox.setBound(2, 1,1);
        testBox.setBound(3, 0,1);
    }

    @Test
    public void testHitbox(){
        assertEquals(0, testBox.getBound(0)[0]);
        assertEquals(0, testBox.getBound(0)[1]);
        assertEquals(1, testBox.getBound(1)[0]);
        assertEquals(0, testBox.getBound(1)[1]);
        assertEquals(1, testBox.getBound(2)[0]);
        assertEquals(1, testBox.getBound(2)[1]);
        assertEquals(0, testBox.getBound(3)[0]);
        assertEquals(1, testBox.getBound(3)[1]);
    }
}
