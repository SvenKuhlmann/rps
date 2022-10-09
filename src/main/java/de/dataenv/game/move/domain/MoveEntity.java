package de.dataenv.game.move.domain;

import de.dataenv.game.match.domain.MatchEntity;

import javax.persistence.*;

@Entity
public class MoveEntity {

    @Id
    @GeneratedValue
    private Integer id;

    private Gesture playerGesture;

    private Gesture kiGesture;

    @ManyToOne
    private MatchEntity match;

    public MoveEntity() {
    }

    public MoveEntity(Gesture playerGesture, Gesture kiGesture) {
        this.playerGesture = playerGesture;
        this.kiGesture = kiGesture;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Gesture getPlayerGesture() {
        return playerGesture;
    }

    public void setPlayerGesture(Gesture playerGesture) {
        this.playerGesture = playerGesture;
    }

    public MatchEntity getMatch() {
        return match;
    }

    public void setMatch(MatchEntity match) {
        this.match = match;
    }

    public Gesture getKiGesture() {
        return kiGesture;
    }

    public void setKiGesture(Gesture kiGesture) {
        this.kiGesture = kiGesture;
    }

    @Transient
    public Result getResult() {
        if (playerGesture.equals(kiGesture)) {
            return Result.TIE;
        } else if (playerGesture.equals(Gesture.SCISSOR) && kiGesture.equals(Gesture.ROCK)) {
            return Result.LOOSE;
        }  else if (playerGesture.equals(Gesture.ROCK) && kiGesture.equals(Gesture.SCISSOR)) {
            return Result.WIN;
        } else if (playerGesture.ordinal() > kiGesture.ordinal()) {
            return Result.WIN;
        }
        return Result.LOOSE;
    }
}
