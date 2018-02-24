package model;

public enum Color {
    WHITE,
    BLACK;

    public Color other(){
        if (this == BLACK) return WHITE;
        else return BLACK;
    }
}
