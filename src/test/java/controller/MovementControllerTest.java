package controller;

import model.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class MovementControllerTest {

    private Board baseBoard;
    private MovementController baseMc;
    private MovementValidator baseValidator;
    private GameLog baseLogger;


    @BeforeEach
    void setupBoards() {
        this.baseBoard = Board.getInitializedBoard();
        this.baseLogger = new GameLog();
        this.baseValidator = new MovementValidator(this.baseBoard);
        this.baseMc = new MovementController(this.baseBoard, this.baseLogger, this.baseValidator);
    }

    // Utility functions
    private MovementAction getAction(String from, String to) {
        return new MovementAction(new FieldId(from), new FieldId(to));
    }

    private void runAction(String from, String to) throws InvalidMoveException {
        this.baseMc.executeMove(this.getAction(from, to));
    }

    private void checkInvalidAction(String from, String to){
        assertThrows(InvalidMoveException.class, () -> this.runAction(from, to));
    }

    private void checkValidAction(String from, String to) throws InvalidMoveException{
        this.runAction(from, to);
        assertNull(this.baseBoard.getField(new FieldId(from)).getFigure());
        assertNotNull(this.baseBoard.getField(new FieldId(to)).getFigure());
    }

    // Test functions

    @Test
    void testBasicWhitePawnMovement() throws InvalidMoveException {
        this.checkValidAction("E2", "E3");
        this.checkValidAction("F2", "F4");
        this.baseMc.executeMoveUnchecked(this.getAction("F8", "F3"));
        this.checkValidAction("G2", "F3");
        assertTrue(this.baseBoard.getField("F3").getFigure().getColor() == Color.WHITE);
    }

    @Test
    void testBasicBlackPawnMovement() throws InvalidMoveException {
        this.checkValidAction("E7", "E6");
        this.checkValidAction("F7", "F5");
        this.baseMc.executeMoveUnchecked(this.getAction("F1", "F6"));
        this.checkValidAction("G7", "F6");
        assertTrue(this.baseBoard.getField("F6").getFigure().getColor() == Color.BLACK);
    }


    @Test
    void testWrongWhitePawnMovement() throws InvalidMoveException {
        this.checkInvalidAction("E3", "F8");
        this.checkInvalidAction("E2", "E2");
        this.checkValidAction("H2", "H3");
        this.checkInvalidAction("H3", "H5");
        this.checkInvalidAction("A2", "B3");
        this.checkInvalidAction("B2", "B5");
        this.checkValidAction("A2", "A3");
        this.checkInvalidAction("B2", "A3");
        this.checkValidAction("D2", "D4");
        this.checkInvalidAction("D4", "D3");
    }

    @Test
    void testWrongBlackPawnMovement() throws InvalidMoveException {
        this.checkInvalidAction("E6", "F1");
        this.checkInvalidAction("E7", "E7");
        this.checkValidAction("H7", "H6");
        this.checkInvalidAction("H6", "H4");
        this.checkInvalidAction("A7", "B6");
        this.checkInvalidAction("B7", "B4");
        this.checkValidAction("A7", "A6");
        this.checkInvalidAction("B7", "A6");
        this.checkValidAction("D7", "D5");
        this.checkInvalidAction("D5", "D6");
    }

    @Test
    void testRookMovement() throws InvalidMoveException {
        this.checkInvalidAction("A1", "A2");
        this.checkInvalidAction("A1", "A3");
        this.checkInvalidAction("A1", "A7");
        this.checkInvalidAction("A1", "B1");
        // put Rook into center
        this.baseMc.executeMoveUnchecked(this.getAction("A1", "E4"));
        this.checkInvalidAction("E4", "D3");
        this.checkInvalidAction("E4", "E8");
        this.checkValidAction("E4", "E7");
        assertEquals(this.baseBoard.getField("E7").getFigure().getName(), FigureName.ROOK);
    }


    @Test
    void testBishopMovement() throws InvalidMoveException {
        this.checkInvalidAction("C1", "B2");
        this.checkInvalidAction("C1", "B1");
        this.checkInvalidAction("C1", "A1");

        // Put Bishop in center
        this.baseMc.executeMoveUnchecked(this.getAction("C1", "E5"));

        this.checkInvalidAction("E5", "H8");
        this.checkInvalidAction("E5", "B2");
        this.checkInvalidAction("E5", "E6");
        this.checkInvalidAction("E5", "B2");

        this.checkValidAction("E5", "D4");
        this.checkValidAction("D4", "G7");
        this.checkValidAction("G7", "H8");
        this.checkValidAction("H8", "E5");
        this.checkValidAction("E5", "C7");
        this.checkValidAction("C7", "B8");

    }

    @Test
    void testQueenMovement() throws InvalidMoveException {
        this.checkInvalidAction("D1", "D2");
        this.checkInvalidAction("D1", "D3");
        this.checkInvalidAction("D1", "C1");
        this.checkInvalidAction("D1", "E2");
        this.checkInvalidAction("D1", "B3");

        // put Queen into center
        this.baseMc.executeMoveUnchecked(this.getAction("D1", "E5"));
        this.checkInvalidAction("E5", "E8");
        this.checkInvalidAction("E5", "C4");
        this.checkInvalidAction("E5", "C8");

        this.checkValidAction("E5", "G7");
        this.checkValidAction("G7", "G8");
        this.checkValidAction("G8", "H8");
        this.checkInvalidAction("H8", "B2");
        this.checkValidAction("H8", "H7");
        this.checkValidAction("H7", "D3");
    }

    @Test
    void testKingMovement() throws InvalidMoveException {
        this.checkInvalidAction("E1", "E2");
        this.checkInvalidAction("E1", "E3");
        this.checkInvalidAction("E1", "D1");
        this.checkInvalidAction("E1", "F2");
        this.checkInvalidAction("E1", "A3");

        // put King into center
        this.baseMc.executeMoveUnchecked(this.getAction("E1", "E5"));
        this.checkInvalidAction("E5", "E8");
        this.checkInvalidAction("E5", "C4");
        this.checkInvalidAction("E5", "G5");
        this.checkInvalidAction("E5", "A3");

        this.checkValidAction("E5", "F5");
        this.checkValidAction("F5", "F4");
        this.checkValidAction("F4", "E3");
        this.checkInvalidAction("E3", "B2");
        this.checkValidAction("E3", "D4");
        this.checkValidAction("D4", "C3");
    }

    @Test
    void testKingShortCastlingMovement() throws InvalidMoveException {
        this.checkInvalidAction("E1", "E2");
        this.baseMc.executeMoveUnchecked(this.getAction("F1", "F3"));
        this.baseMc.executeMoveUnchecked(this.getAction("G1", "G3"));
        this.checkValidAction("E1", "G1");
        assertEquals(this.baseBoard.getField("G1").getFigure().getName(), FigureName.KING);
        assertEquals(this.baseBoard.getField("F1").getFigure().getName(), FigureName.ROOK);

        this.checkInvalidAction("E8", "E7");
        this.baseMc.executeMoveUnchecked(this.getAction("F8", "F6"));
        this.baseMc.executeMoveUnchecked(this.getAction("G8", "G6"));
        this.checkValidAction("E8", "G8");
        assertEquals(this.baseBoard.getField("G8").getFigure().getName(), FigureName.KING);
        assertEquals(this.baseBoard.getField("F8").getFigure().getName(), FigureName.ROOK);
    }

    @Test
    void testKingLongCastlingMovement() throws InvalidMoveException {
        this.checkInvalidAction("E1", "E2");
        this.baseMc.executeMoveUnchecked(this.getAction("B1", "B3"));
        this.baseMc.executeMoveUnchecked(this.getAction("C1", "C3"));
        this.baseMc.executeMoveUnchecked(this.getAction("D1", "D3"));
        this.checkValidAction("E1", "C1");
        assertEquals(this.baseBoard.getField("C1").getFigure().getName(), FigureName.KING);
        assertEquals(this.baseBoard.getField("D1").getFigure().getName(), FigureName.ROOK);

        this.checkInvalidAction("E8", "E7");
        this.baseMc.executeMoveUnchecked(this.getAction("B8", "B6"));
        this.baseMc.executeMoveUnchecked(this.getAction("C8", "C6"));
        this.baseMc.executeMoveUnchecked(this.getAction("D8", "D6"));
        this.checkValidAction("E8", "C8");
        assertEquals(this.baseBoard.getField("C8").getFigure().getName(), FigureName.KING);
        assertEquals(this.baseBoard.getField("D8").getFigure().getName(), FigureName.ROOK);
    }

    @Test
    void testKnightMovement() throws InvalidMoveException {
        this.checkInvalidAction("B1", "D2");
        this.checkValidAction("B1", "C3");
        this.checkInvalidAction("C3", "C4");
        this.checkInvalidAction("C3", "D3");
        this.checkInvalidAction("C3", "E3");
        this.checkInvalidAction("C3", "E2");
        this.checkInvalidAction("C3", "F4");
        this.checkInvalidAction("C3", "F3");
        this.checkValidAction("C3", "D5");
        this.checkValidAction("D5", "C7");
        this.checkValidAction("C7", "A8");
        this.checkValidAction("A8", "B6");
    }
}
