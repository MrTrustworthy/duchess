package controller;

import model.*;

import java.util.ArrayList;
import java.util.List;

public class MovementController {

    private Board board;
    private GameLog log;
    private MovementValidator validator;

    public MovementController(Board board, GameLog log, MovementValidator validator) {
        this.board = board;
        this.log = log;
        this.validator = validator;
    }

    public void executeAndLogMove(MovementAction move) throws InvalidMoveException {
        List<MovementResult> results = this.executeMove(move);
        this.logMovement(move, results);
    }

    public List<MovementResult> executeMove(MovementAction move) throws InvalidMoveException {
        this.validator.checkMoveValidity(move);
        return this.executeMoveUnchecked(move);
    }

    /**
     * This function assumes that all prequisites for a movement have already been checked.
     * If this function is called directly without verification, it may lead to weird game states
     */
    protected List<MovementResult> executeMoveUnchecked(MovementAction move) {
        // prepare valuess & variables
        List<MovementResult> results = new ArrayList<>();
        Field origin = this.board.getField(move.getOriginPosition());
        Field target = this.board.getField(move.getTargetPosition());
        Figure figure = origin.getFigure();

        results.addAll(this.handleBeatingMove(target));
        results.addAll(this.handleCastlingMove(figure, target, move));

        // perform actual changes only after performing the handle* calls
        origin.setFigure(null);
        target.setFigure(figure);
        figure.setMoved();
        return results;
    }

    private List<MovementResult> handleBeatingMove(Field target) {
        List<MovementResult> results = new ArrayList<>();
        if (target.getFigure() != null) {
            target.getFigure().setBeaten();
            results.add(MovementResult.BEAT_FIGURE);
        }
        return results;
    }


  /**
   * If the initial move was a castling move (distance 2 with KING), will move the rook accordingly
   */
    private List<MovementResult> handleCastlingMove(Figure figure, Field target, MovementAction move) {
        List<MovementResult> results = new ArrayList<>();
        // maybe use isValidCastlingMove in second field for more consistency?
        if (figure.getName() == FigureName.KING && this.validator.isValidCastlingMove(move)) {
            Integer column = target.getFieldId().getColumn();
            Integer row = target.getFieldId().getRow();

            MovementResult type = column.equals(7) ? MovementResult.CASTLE_SHORT : MovementResult.CASTLE_LONG;
            String from = type == MovementResult.CASTLE_SHORT ? "H" : "A";
            String to = type == MovementResult.CASTLE_SHORT ? "F" : "D";

            this.executeMoveUnchecked(new MovementAction(new FieldId(from, row), new FieldId(to, row)));
            results.add(type);
        }
        return results;
    }


    private void logMovement(MovementAction move, List<MovementResult> results) {
        Figure originFig = this.board.getField(move.getOriginPosition()).getFigure();
        Figure targetFig = this.board.getField(move.getTargetPosition()).getFigure();
        LogMessage msg = new LogMessage(move, originFig, targetFig, results);
        this.log.addMovementMessage(msg);
    }


}
