package inf112.skeleton.app;

import static org.junit.Assert.*;

import inf112.skeleton.app.board.BoardParser;
import inf112.skeleton.app.objects.boardObjects.ConveyorBelt;
import inf112.skeleton.app.objects.boardObjects.GearClockwise;
import inf112.skeleton.app.objects.boardObjects.GearCounterClockwise;
import inf112.skeleton.app.objects.boardObjects.IBoardObject;
import org.junit.Test;

public class BoardParserTest {

    @Test
    public void testFactoryProducesConveyorbelt() {
        IBoardObject object = BoardParser.factory('w');
        assertTrue(object instanceof ConveyorBelt);
    }

    @Test
    public void testFactoryProducesExpressConveyorbelt() {
        IBoardObject object = BoardParser.factory('W');
        assertTrue(object instanceof ConveyorBelt);
        assertTrue(((ConveyorBelt) object).getExpress());
    }

    @Test
    public void testFactoryProducesGearClockwise() {
        IBoardObject object = BoardParser.factory('C');
        assertTrue(object instanceof GearClockwise);
    }

    @Test
    public void testFactoryProducesGearCounterClockwise() {
        IBoardObject object = BoardParser.factory('c');
        assertTrue(object instanceof GearCounterClockwise);
    }

}
