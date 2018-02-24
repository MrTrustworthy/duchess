package model;

import java.util.ArrayList;
import java.util.List;

public class FieldId {


    private int column;
    private int row;

    private static final int NUM_ROWS_AND_COLUMNS = 8;
    private static final String[] COLUMN_STRING_MAPPINGS = new String[]{"XX", "A", "B", "C", "D", "E", "F", "G", "H"};

    public FieldId(int column, int row) {
        this.column = column;
        this.row = row;
    }

    public FieldId(String column, int row) {
        this(FieldId.getColumnAsInt(column), row);
    }

    public FieldId(String identifier) {
        this(identifier.substring(0, 1).toUpperCase(), Integer.parseInt(identifier.substring(1, 2)));
    }

    private static int getColumnAsInt(String s) {
        for (int i = 0; i < COLUMN_STRING_MAPPINGS.length; i++) {
            if (s.equals(COLUMN_STRING_MAPPINGS[i])) return i;
        }
        throw new IllegalArgumentException("String " + s + " is not a valid column identifier");
    }

    public Integer getColumn() {
        return column;
    }

    public Integer getRow() {
        return row;
    }

    public String toString() {
        return "model.Field{" + COLUMN_STRING_MAPPINGS[this.column] + this.row + "}";
    }

    public boolean equals(FieldId other) {
        return this.column == other.column && this.row == other.row;
    }

    public static List<FieldId> getAllPermutations() {
        ArrayList<FieldId> fields = new ArrayList<FieldId>();
        for (int s = 1; s <= NUM_ROWS_AND_COLUMNS; s++) {
            for (int i = 1; i <= NUM_ROWS_AND_COLUMNS; i++) {
                fields.add(new FieldId(s, i));
            }
        }
        return fields;
    }

    public List<FieldId> getStepsFromDelta(List<FieldIdDelta> deltas) {
        List<FieldId> steps = new ArrayList<>();
        FieldId current = this;
        for (FieldIdDelta delta : deltas) {
            FieldId newId = new FieldId(current.column + delta.col, current.row + delta.row);
            steps.add(newId);
            current = newId;
        }
        return steps;
    }

    public FieldId getClosestCorner() {
        int col = this.column <= 4 ? 1 : 8;
        int row = this.row <= 4 ? 1 : 8;
        return new FieldId(col, row);
    }


}
