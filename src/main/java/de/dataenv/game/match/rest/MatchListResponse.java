package de.dataenv.game.match.rest;

import de.dataenv.game.match.domain.MatchEntity;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class MatchListResponse {

    private List<MatchResponse> matches = new ArrayList<>();

    public MatchListResponse() {
    }

    public MatchListResponse(List<MatchEntity> all) {
        this.matches = all.stream().map(MatchResponse::new).collect(Collectors.toList());
    }

    public List<MatchResponse> getMatches() {
        return matches;
    }

    public void setMatches(List<MatchResponse> matches) {
        this.matches = matches;
    }
}
