package model;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class FigureList{

    private List<Figure> figures;

    public FigureList(List<Figure> figures){
        this.figures = figures;
    }

    public List<Figure> getAll() {
        return figures;
    }

    public List<Figure> findAll(Color color, FigureName name){
        return this.figures.stream()
                .filter(el -> el.getColor() == color && el.getName() == name)
                .collect(Collectors.toList());
    }

    public List<Figure> findAll(Color color){
        return this.figures.stream()
                .filter(el -> el.getColor() == color)
                .collect(Collectors.toList());
    }

    public List<Figure> findAll(FigureName name){
        return this.figures.stream()
                .filter(el -> el.getName() == name)
                .collect(Collectors.toList());
    }

    public static List<Figure> getDefaultSetup(){
        List<Figure> defaultPositions = new ArrayList<Figure>();
        defaultPositions.add(new Figure(Color.WHITE, FigureName.PAWN, new FieldId("A2")));
        defaultPositions.add(new Figure(Color.WHITE, FigureName.PAWN, new FieldId("B2")));
        defaultPositions.add(new Figure(Color.WHITE, FigureName.PAWN, new FieldId("C2")));
        defaultPositions.add(new Figure(Color.WHITE, FigureName.PAWN, new FieldId("D2")));
        defaultPositions.add(new Figure(Color.WHITE, FigureName.PAWN, new FieldId("E2")));
        defaultPositions.add(new Figure(Color.WHITE, FigureName.PAWN, new FieldId("F2")));
        defaultPositions.add(new Figure(Color.WHITE, FigureName.PAWN, new FieldId("G2")));
        defaultPositions.add(new Figure(Color.WHITE, FigureName.PAWN, new FieldId("H2")));
        defaultPositions.add(new Figure(Color.WHITE, FigureName.ROOK, new FieldId("A1")));
        defaultPositions.add(new Figure(Color.WHITE, FigureName.KNIGHT, new FieldId("B1")));
        defaultPositions.add(new Figure(Color.WHITE, FigureName.BISHOP, new FieldId("C1")));
        defaultPositions.add(new Figure(Color.WHITE, FigureName.QUEEN, new FieldId("D1")));
        defaultPositions.add(new Figure(Color.WHITE, FigureName.KING, new FieldId("E1")));
        defaultPositions.add(new Figure(Color.WHITE, FigureName.BISHOP, new FieldId("F1")));
        defaultPositions.add(new Figure(Color.WHITE, FigureName.KNIGHT, new FieldId("G1")));
        defaultPositions.add(new Figure(Color.WHITE, FigureName.ROOK, new FieldId("H1")));

        defaultPositions.add(new Figure(Color.BLACK, FigureName.PAWN, new FieldId("A7")));
        defaultPositions.add(new Figure(Color.BLACK, FigureName.PAWN, new FieldId("B7")));
        defaultPositions.add(new Figure(Color.BLACK, FigureName.PAWN, new FieldId("C7")));
        defaultPositions.add(new Figure(Color.BLACK, FigureName.PAWN, new FieldId("D7")));
        defaultPositions.add(new Figure(Color.BLACK, FigureName.PAWN, new FieldId("E7")));
        defaultPositions.add(new Figure(Color.BLACK, FigureName.PAWN, new FieldId("F7")));
        defaultPositions.add(new Figure(Color.BLACK, FigureName.PAWN, new FieldId("G7")));
        defaultPositions.add(new Figure(Color.BLACK, FigureName.PAWN, new FieldId("H7")));
        defaultPositions.add(new Figure(Color.BLACK, FigureName.ROOK, new FieldId("A8")));
        defaultPositions.add(new Figure(Color.BLACK, FigureName.KNIGHT, new FieldId("B8")));
        defaultPositions.add(new Figure(Color.BLACK, FigureName.BISHOP, new FieldId("C8")));
        defaultPositions.add(new Figure(Color.BLACK, FigureName.QUEEN, new FieldId("D8")));
        defaultPositions.add(new Figure(Color.BLACK, FigureName.KING, new FieldId("E8")));
        defaultPositions.add(new Figure(Color.BLACK, FigureName.BISHOP, new FieldId("F8")));
        defaultPositions.add(new Figure(Color.BLACK, FigureName.KNIGHT, new FieldId("G8")));
        defaultPositions.add(new Figure(Color.BLACK, FigureName.ROOK, new FieldId("H8")));
        return defaultPositions;
    }


}
