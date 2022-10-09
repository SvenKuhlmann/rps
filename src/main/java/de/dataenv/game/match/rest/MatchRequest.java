package de.dataenv.game.match.rest;

public class MatchRequest {

    private String name;

    public MatchRequest() {
    }

    public MatchRequest(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
