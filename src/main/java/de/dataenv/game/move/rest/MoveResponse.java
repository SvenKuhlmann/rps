package de.dataenv.game.move.rest;

import de.dataenv.game.move.domain.Gesture;
import de.dataenv.game.move.domain.MoveEntity;
import de.dataenv.game.move.domain.Result;

public class MoveResponse {

    private Gesture playerGesture;

    private Gesture kiGesture;

    private Result result;

    public MoveResponse() {
    }

    public MoveResponse(MoveEntity move) {
        this.playerGesture = move.getPlayerGesture();
        this.kiGesture = move.getKiGesture();
        this.result = move.getResult();
    }

    public Gesture getPlayerGesture() {
        return playerGesture;
    }

    public void setPlayerGesture(Gesture playerGesture) {
        this.playerGesture = playerGesture;
    }

    public Gesture getKiGesture() {
        return kiGesture;
    }

    public void setKiGesture(Gesture kiGesture) {
        this.kiGesture = kiGesture;
    }

    public Result getResult() {
        return result;
    }

    public void setResult(Result result) {
        this.result = result;
    }
}
