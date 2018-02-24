package model;

public class Field {

    private FieldId fieldId;
    private Color color;
    private Figure figure;

    public Field(FieldId fieldId) {
        this.fieldId = fieldId;
        this.color = (fieldId.getColumn() + fieldId.getRow()) % 2 == 0 ? Color.BLACK : Color.WHITE;
        this.figure = null;
    }

    public Color getColor() {
        return this.color;
    }

    public FieldId getFieldId() {
        return fieldId;
    }


    public Figure getFigure() {
        return figure;
    }

    public void setFigure(Figure figure) {
        this.figure = figure;
    }
}
