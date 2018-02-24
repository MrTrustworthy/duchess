package model;

import java.util.ArrayList;
import java.util.List;

public class FieldIdDelta {

    public int col;
    public int row;

    public FieldIdDelta(int col, int row) {
        this.col = col;
        this.row = row;
    }

    public static FieldIdDelta getDelta(FieldId origin, FieldId target) {
        return new FieldIdDelta(
                target.getColumn() - origin.getColumn(),
                target.getRow() - origin.getRow()
        );
    }

    public List<FieldIdDelta> getIndividualStepDeltas() {
        assert !this.isIrregularMove() : "Can't get step deltas for irregular moves";
        List<FieldIdDelta> steps = new ArrayList<>();
        int rowStep = this.row == 0 ? 0 : this.row / Math.abs(this.row);
        int colStep = this.col == 0 ? 0 : this.col / Math.abs(this.col);
        for (int i = 0; i < this.distance(); i++) {
            steps.add(new FieldIdDelta(colStep, rowStep));
        }
        return steps;
    }

    public boolean isColumnOnlyMove() {
        return this.row == 0;
    }

    public boolean isRowOnlyMove() {
        return this.col == 0;
    }

    public boolean isDiagonalMove() {
        return Math.abs(this.col) == Math.abs(this.row);
    }

    public boolean isIrregularMove() {
        return !(this.isRowOnlyMove() || this.isColumnOnlyMove() || this.isDiagonalMove());
    }

    public boolean isKnightMove(){
        return Math.max(Math.abs(this.row), Math.abs(this.col)) == 2
                && Math.min(Math.abs(this.row), Math.abs(this.col)) == 1;
    }

    public int distance() {
        return Math.max(Math.abs(this.row), Math.abs(this.col));
    }

    public Color towardsDirection() {
        if (row > 0) return Color.BLACK;
        if (row < 0) return Color.WHITE;
        return null;
    }

    public String toString(){
        return String.format("FieldIdDelta{column %s : row %s}", this.col, this.row);
    }

}
