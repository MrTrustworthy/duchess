package model;

import com.google.common.collect.HashBasedTable;
import com.google.common.collect.Table;

import java.util.List;
import java.util.stream.Collectors;

public class Board {

    private Table<Integer, Integer, Field> grid;
    private FigureList figures;

    public Board() {
    }

    public static Board getInitializedBoard() {
        Board board = new Board();
        FigureList allFigures = new FigureList(FigureList.getDefaultSetup());
        Table<Integer, Integer, Field> grid = Board.getEmptyGrid();
        board.setGrid(grid);
        board.setFigures(allFigures);
        return board;
    }

    private static Table<Integer, Integer, Field> getEmptyGrid() {
        Table<Integer, Integer, Field> board = HashBasedTable.create();
        for (FieldId fieldId : FieldId.getAllPermutations()) {
            board.put(fieldId.getRow(), fieldId.getColumn(), new Field(fieldId));
        }
        return board;
    }

    public Table<Integer, Integer, Field> getGrid() {
        return grid;
    }

    public void setGrid(Table<Integer, Integer, Field> grid) {
        this.grid = grid;
    }

    public Field getField(FieldId fieldId) {
        return this.grid.get(fieldId.getRow(), fieldId.getColumn());
    }

    public Field getField(String s){
        return this.getField(new FieldId(s));
    }

    public FigureList getFigures() {
        return figures;
    }

    public void setFigures(FigureList figures) {
        for (Figure f : figures.getAll()) {
            FieldId position = f.getPosition();
            this.getField(position).setFigure(f);
        }
        this.figures = figures;
    }

    public List<Field> getFieldsOnPath (FieldId origin, FieldIdDelta delta){
        return origin.getStepsFromDelta(delta.getIndividualStepDeltas())
                .stream()
                .map(this::getField)
                .collect(Collectors.toList());
    }
}
