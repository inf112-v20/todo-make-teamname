package inf112.skeleton.app.CardTests;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.backends.headless.HeadlessApplication;
import com.badlogic.gdx.backends.headless.HeadlessApplicationConfiguration;
import com.badlogic.gdx.graphics.GL20;
import inf112.skeleton.app.EmptyApplicationListener;
import inf112.skeleton.app.board.Board;
import inf112.skeleton.app.main.Game;
import inf112.skeleton.app.main.TurnHandler;
import inf112.skeleton.app.objects.cards.NonTextureProgramCard;
import org.junit.BeforeClass;
import org.junit.Test;

import java.util.HashMap;

import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.mock;

public class PriorityTest {

    private static TurnHandler turnHandler;
    private static Game game;

    @BeforeClass
    public static void setUp() {
        HeadlessApplicationConfiguration conf = new HeadlessApplicationConfiguration();
        new HeadlessApplication(new EmptyApplicationListener(), conf);
        Gdx.gl = mock(GL20.class);
        game = new Game();
        game.setBoard(new Board(3, 1, 3));
        game.gamePhasesSetUp();
        turnHandler = game.getTurnHandler();
    }

    @Test
    public void priorityTest(){
        NonTextureProgramCard[] cards = new NonTextureProgramCard[]
                {new NonTextureProgramCard(0,false,false,false,500),
                new NonTextureProgramCard(0,false,false,false,700),
                new NonTextureProgramCard(0,false,false,false,200),
                new NonTextureProgramCard(0,false,false,false,630)};
        HashMap<NonTextureProgramCard, Integer> hashMap = new HashMap<>();
        for (NonTextureProgramCard card: cards) {
            hashMap.put(card, 1);
        }
        cards = turnHandler.sortByPriority(hashMap);
        for (int i = 1; i < cards.length; i++) {
            assertTrue(cards[i].getPriority() < cards[i-1].getPriority());
        }
    }
}
