package model;

import com.google.common.collect.HashBasedTable;
import com.google.common.collect.Table;

public class GameLog {

    private Table<Integer, Color, LogMessage> messages;
    private int currentTurn;
    private Color currentColor;

    public GameLog() {
        this.messages = HashBasedTable.create();
        this.currentTurn = 1;
        this.currentColor = Color.WHITE;
    }

    public void addMovementMessage(LogMessage message) {
        assert message.getFigure().getColor() == this.currentColor :
                String.format("Something seems off with the turns - should be %s's turn", this.currentColor.name());

        this.messages.put(this.currentTurn, this.currentColor, message);
        this.bumpStatus();
    }


    private void bumpStatus() {
        if (this.currentColor == Color.BLACK) this.currentTurn++;
        this.currentColor = this.currentColor.other();
    }


}
