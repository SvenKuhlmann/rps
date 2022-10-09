package de.dataenv.game.move.rest;

import de.dataenv.game.move.domain.Gesture;

public class MoveRequest {

    private Gesture gesture;

    public MoveRequest() {
    }

    public MoveRequest(Gesture gesture) {
        this.gesture = gesture;
    }

    public Gesture getGesture() {
        return gesture;
    }

    public void setGesture(Gesture gesture) {
        this.gesture = gesture;
    }
}
