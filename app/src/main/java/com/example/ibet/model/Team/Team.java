package com.example.ibet.model.Team;

import androidx.annotation.NonNull;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.Ignore;
import androidx.room.PrimaryKey;

@Entity(tableName = "team_table")
public class Team {

    @PrimaryKey()
    @NonNull
    @ColumnInfo(name = "team_id")
    String id;

    @ColumnInfo(name = "team_name")
    String teamName;

    @ColumnInfo(name = "team_wins")
    String wins;

    @ColumnInfo(name = "team_remaining")
    String gamesRemaining;

    @ColumnInfo(name = "team_losses")
    String losses;

    @ColumnInfo(name = "team_eliminated")
    Boolean isEliminated;

    @ColumnInfo(name = "team_url")
    String url;

    @Ignore
    private long lastUpdated;


    @Ignore
    public Team(String teamName, String wins, String losses, Boolean isEliminated) {
        this.teamName = teamName;
        this.wins = wins;
        this.losses = losses;
        this.isEliminated = isEliminated;
    }

    public Team(@NonNull String id,String teamName,String wins,String losses,String gamesRemaining)
    {
        this.id = id;
        this.teamName = teamName;
        this.wins = wins;
        this.losses = losses;
        this.gamesRemaining=gamesRemaining;
    }

    @NonNull
    public String getId() {
        return id;
    }

    public void setId(@NonNull String id) {
        this.id = id;
    }

    public String getGamesRemaining() {
        return gamesRemaining;
    }

    public void setGamesRemaining(String gamesRemaining) {
        this.gamesRemaining = gamesRemaining;
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

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }
}
