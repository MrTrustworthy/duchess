package model;

import java.util.List;

public class LogMessage {

    private MovementAction move;
    private Figure figure;
    private Figure targetFigure;
    private List<MovementResult> results;

    public LogMessage(MovementAction move, Figure figure, Figure targetFigure, List<MovementResult> results) {
        this.move = move;
        this.figure = figure;
        this.targetFigure = targetFigure;
        this.results = results;
    }


    public MovementAction getMove() {
        return move;
    }

    public Figure getFigure() {
        return figure;
    }

    public Figure getTargetFigure() {
        return targetFigure;
    }

    public List<MovementResult> getResults() {
        return results;
    }
}
