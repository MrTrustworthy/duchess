package controller;

import model.*;

import java.util.List;

public class MovementValidator {

    private Board board;

    public MovementValidator(Board board){
        this.board = board;
    }


    protected void checkMoveValidity(MovementAction move) throws InvalidMoveException {
        try {
            this.checkOriginIsValid(move);
            this.checkTargetIsValid(move);
            this.checkMovementPathIsValid(move);
        } catch (InvalidMoveException e) {
            throw e;
        } catch (Exception e) {
            throw new InvalidMoveException("Movement action " + move.toString() + " is invalid");
        }
    }

    private void checkOriginIsValid(MovementAction move) throws InvalidMoveException {
        Field origin = board.getField(move.getOriginPosition());
        Figure figure = origin.getFigure();
        if (figure == null) {
            throw new InvalidMoveException("There is no Figure on field " + origin.toString());
        }

    }

    private void checkTargetIsValid(MovementAction move) throws InvalidMoveException {
        Figure figure = board.getField(move.getOriginPosition()).getFigure();
        Field target = this.board.getField(move.getTargetPosition());
        if (move.getOriginPosition().equals(move.getTargetPosition())) {
            throw new InvalidMoveException("Origin and target can't be the same field");
        }
        if (target.getFigure() != null && target.getFigure().getColor() == figure.getColor()) {
            throw new InvalidMoveException("The target field has a figure with the same color than the moving piece");
        }
    }

    private void checkMovementPathIsValid(MovementAction move) throws InvalidMoveException {
        FigureName figureName = board.getField(move.getOriginPosition()).getFigure().getName();
        switch (figureName) {
            case PAWN:
                this.checkPawnMovementPathIsValid(move);
                break;
            case ROOK:
                this.checkRookMovementPathIsValid(move);
                break;
            case BISHOP:
                this.checkBishopMovementPathIsValid(move);
                break;
            case QUEEN:
                this.checkQueenMovementPathIsValid(move);
                break;
            case KING:
                this.checkKingMovementPathIsValid(move);
                break;
            case KNIGHT:
                this.checkKnightMovementPathIsValid(move);
                break;
        }
    }

    private void checkPawnMovementPathIsValid(MovementAction move) throws InvalidMoveException {
        FieldId originPos = move.getOriginPosition();
        FieldId targetPos = move.getTargetPosition();
        Field target = this.board.getField(targetPos);
        Figure figure = board.getField(move.getOriginPosition()).getFigure();

        FieldIdDelta delta = FieldIdDelta.getDelta(originPos, targetPos);

        boolean movesForward = delta.towardsDirection() == figure.getColor().other();
        boolean targetsFigure = target.getFigure() != null;

        boolean isStandardMove = movesForward && delta.isRowOnlyMove() && delta.distance() == 1 && !targetsFigure;
        boolean isDoubleMove = movesForward && !figure.hasMoved() && delta.isRowOnlyMove() && delta.distance() == 2 && !targetsFigure;
        boolean isBeatingMove = movesForward && delta.isDiagonalMove() && delta.distance() == 1 && targetsFigure;

        // TODO en passant - need logs for that

        if (isStandardMove || isDoubleMove || isBeatingMove) return;

        throw new InvalidMoveException("Pawn can't move from " + originPos.toString() + " to " + targetPos.toString());
    }

    private void checkRookMovementPathIsValid(MovementAction move) throws InvalidMoveException {
        FieldId originPos = move.getOriginPosition();
        FieldId targetPos = move.getTargetPosition();

        FieldIdDelta delta = FieldIdDelta.getDelta(originPos, targetPos);

        boolean isValidDirection = delta.isColumnOnlyMove() || delta.isRowOnlyMove();
        if (!isValidDirection) throw new InvalidMoveException("Rooks can only move in straight lines");

        List<Field> path = this.board.getFieldsOnPath(originPos, delta);
        // last element is the target field - having figures there is OK
        boolean isEmptyPath = path.stream().limit(path.size() - 1).allMatch(fd -> fd.getFigure() == null);
        if (!isEmptyPath) throw new InvalidMoveException("Rooks can't jump over other figures");
    }

    private void checkBishopMovementPathIsValid(MovementAction move) throws InvalidMoveException {
        FieldId originPos = move.getOriginPosition();
        FieldId targetPos = move.getTargetPosition();

        FieldIdDelta delta = FieldIdDelta.getDelta(originPos, targetPos);

        if (!delta.isDiagonalMove()) throw new InvalidMoveException("Bishops can only move diagonally");

        List<Field> path = this.board.getFieldsOnPath(originPos, delta);
        // last element is the target field - having figures there is OK
        boolean isEmptyPath = path.stream().limit(path.size() - 1).allMatch(fd -> fd.getFigure() == null);
        if (!isEmptyPath) throw new InvalidMoveException("Bishops can't jump over other figures");
    }

    private void checkQueenMovementPathIsValid(MovementAction move) throws InvalidMoveException {
        FieldId originPos = move.getOriginPosition();
        FieldId targetPos = move.getTargetPosition();

        FieldIdDelta delta = FieldIdDelta.getDelta(originPos, targetPos);

        if (delta.isIrregularMove()) throw new InvalidMoveException("Queens can only move diagonally or straight");

        List<Field> path = this.board.getFieldsOnPath(originPos, delta);
        // last element is the target field - having figures there is OK
        boolean isEmptyPath = path.stream().limit(path.size() - 1).allMatch(fd -> fd.getFigure() == null);
        if (!isEmptyPath) throw new InvalidMoveException("Queens can't jump over other figures");
    }

    private void checkKingMovementPathIsValid(MovementAction move) throws InvalidMoveException {
        FieldId originPos = move.getOriginPosition();
        FieldId targetPos = move.getTargetPosition();
        FieldIdDelta delta = FieldIdDelta.getDelta(originPos, targetPos);


        // do this first to exclude all _definitely_ invalid movement patterns and avoid querying
        if (delta.isIrregularMove()) throw new InvalidMoveException("Kings can only move diagonally or straight");
        List<Field> path = this.board.getFieldsOnPath(originPos, delta);

        // check castling second as it's a special case that would be thrown out by later steps
        if (this.isValidCastlingMove(move)) return;

        // last element is the target field - having figures there is OK
        boolean isEmptyPath = path.stream().limit(path.size() - 1).allMatch(fd -> fd.getFigure() == null);
        if (!isEmptyPath) throw new InvalidMoveException("Kings can't jump over other figures");

        // TODO FIXME Castling
        if (delta.distance() != 1) throw new InvalidMoveException("Kings can only move by exactly one field");
    }

    protected boolean isValidCastlingMove(MovementAction move) {
        FieldId originPos = move.getOriginPosition();
        FieldId targetPos = move.getTargetPosition();
        FieldIdDelta delta = FieldIdDelta.getDelta(originPos, targetPos);
        Figure king = this.board.getField(originPos).getFigure();

        // some of the most commonly failed checks first to reduce time spend in unneeded evaluations
        if (king.hasMoved() || delta.distance() != 2 || !delta.isColumnOnlyMove()) return false;

        // check if this is a valid rook for castling
        Field rookField = this.board.getField(targetPos.getClosestCorner());
        Figure rook = rookField.getFigure();
        if (rook == null || rook.getColor() != king.getColor()
                || rook.getName() != FigureName.ROOK || rook.hasMoved()) return false;

        // check if the path between king and rook is empty
        FieldIdDelta deltaToRook = FieldIdDelta.getDelta(originPos, rookField.getFieldId());
        List<Field> path = this.board.getFieldsOnPath(originPos, deltaToRook);
        boolean pathIsEmpty = path.stream().limit(path.size() - 1).allMatch(fd -> fd.getFigure() == null);
        if (!pathIsEmpty) return false;

        return true;
        // TODO FIXME prevent castling during & through check
    }


    private void checkKnightMovementPathIsValid(MovementAction move) throws InvalidMoveException {
        FieldId originPos = move.getOriginPosition();
        FieldId targetPos = move.getTargetPosition();

        FieldIdDelta delta = FieldIdDelta.getDelta(originPos, targetPos);

        if (!delta.isKnightMove()) throw new InvalidMoveException("Knights can only move in \"L\" patterns" + delta);
    }
}
