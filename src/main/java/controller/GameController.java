package controller;

import model.MovementAction;

public class GameController {

    private MovementController moveController;

    public GameController(MovementController moveController){
        this.moveController = moveController;
    }

}
