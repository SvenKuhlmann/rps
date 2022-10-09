package de.dataenv.game.match.rest;

import de.dataenv.game.match.domain.MatchEntity;
import de.dataenv.game.move.rest.MoveResponse;

import java.util.List;
import java.util.stream.Collectors;

public class MatchResponse {

    private List<MoveResponse> moves;
    private Integer id;
    private String name;

    public MatchResponse(MatchEntity entity) {
        this.id = entity.getId();
        this.moves = entity.getMoves()
                           .stream()
                           .map(MoveResponse::new)
                           .collect(Collectors.toList());
        this.name = entity.getName();
    }

    public MatchResponse() {
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public List<MoveResponse> getMoves() {
        return moves;
    }

    public void setMoves(List<MoveResponse> moves) {
        this.moves = moves;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
