package com.example.ibet.model.Team;

public class Team {

    String teamName;
    String wins;
    String losses;
    Boolean isEliminated;

    public Team(String teamName, String wins, String losses, Boolean isEliminated) {
        this.teamName = teamName;
        this.wins = wins;
        this.losses = losses;
        this.isEliminated = isEliminated;
    }

    public String getTeamName() {
        return teamName;
    }

    public void setTeamName(String teamName) {
        this.teamName = teamName;
    }

    public String getWins() {
        return wins;
    }

    public void setWins(String wins) {
        this.wins = wins;
    }

    public String getLosses() {
        return losses;
    }

    public void setLosses(String losses) {
        this.losses = losses;
    }

    public Boolean getEliminated() {
        return isEliminated;
    }

    public void setEliminated(Boolean eliminated) {
        isEliminated = eliminated;
    }
}
