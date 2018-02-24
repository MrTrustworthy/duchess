package model;

import java.util.List;
import java.util.stream.Collectors;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertSame;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;


class ModelTest {
    
    @Test
    void testBoardInitialization() {
        Board b = Board.getInitializedBoard();
        assertEquals(32, b.getFigures().getAll().size());
        assertEquals(16, b.getFigures().findAll(Color.BLACK).size());
        Figure k1 = b.getField(new FieldId("D8")).getFigure();
        Figure k2 = b.getFigures().findAll(Color.BLACK, FigureName.QUEEN).get(0);
        assertSame(k1, k2);
    }

    @Test
    void testFigureInitialization() {
        FigureList figures = new FigureList(FigureList.getDefaultSetup());
        Figure whiteKing = figures.findAll(Color.WHITE, FigureName.KING).get(0);
        assertEquals(whiteKing.getName(), FigureName.KING);
        assertEquals(whiteKing.getColor(), Color.WHITE);
        assertTrue(whiteKing.getPosition().equals(new FieldId("E1")));
    }

    @Test
    void testFieldIdConversions() {
        FieldId f = new FieldId("G4");
        assertEquals((long) f.getColumn(), 7L);
        assertEquals((long) f.getRow(), 4L);
    }
}