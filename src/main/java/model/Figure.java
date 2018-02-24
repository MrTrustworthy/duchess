package model;

import java.util.ArrayList;
import java.util.List;

public class Figure {

    private FigureName name;
    private Color color;
    private FieldId position;
    private boolean isActive;
    private boolean hasMoved;

    public Figure(Color color, FigureName name, FieldId position) {
        this.name = name;
        this.color = color;
        this.position = position;
        this.isActive = true;
        this.hasMoved = false;
    }

    public FigureName getName() {
        return name;
    }

    public String getShortName() {
        return this.name.name().substring(0, 1).toUpperCase();
    }

    public void setName(FigureName name) {
        this.name = name;
    }

    public Color getColor() {
        return color;
    }

    public FieldId getPosition() {
        return position;
    }

    public void setPosition(FieldId position) {
        this.position = position;
    }

    public boolean isActive() {
        return this.isActive;
    }

    public void setBeaten() {
        this.isActive = false;
    }

    public boolean hasMoved() {
        return this.hasMoved;
    }

    public void setMoved() {
        this.hasMoved = true;
    }
}
