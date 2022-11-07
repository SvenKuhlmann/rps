package de.dataenv.game.move.domain;

public enum Gesture {
    ROCK(2), PAPER(0), SCISSOR(1);

    int winsAgainst;

    Gesture(Integer winsAgainst) {
        this.winsAgainst = winsAgainst;
    }

    Result getResultAgainst(Gesture gesture){
        if(this.equals(gesture))return Result.TIE;
        if (this.winsAgainst == gesture.ordinal())return Result.WIN;
        return Result.LOOSE;
    }
}
