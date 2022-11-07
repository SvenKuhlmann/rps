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
        return this.playerGesture.getResultAgainst(kiGesture);
    }
}
