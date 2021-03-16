package com.example.ibet.model.Group;

import androidx.annotation.NonNull;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.Ignore;
import androidx.room.PrimaryKey;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Entity(tableName = "group_table")
public class Group {

    @PrimaryKey()
    @NonNull
    @ColumnInfo(name = "group_id")
    private String id = UUID.randomUUID().toString();

    @ColumnInfo(name = "group_name")
    private String name;

    @ColumnInfo(name = "group_logo")
    private String groupLogo;

    @ColumnInfo(name = "group_pass")
    private String password;

    @ColumnInfo(name = "league_id")
    private String leagueId;

    @ColumnInfo(name = "league_name")
    private String leagueName;

    @ColumnInfo(name = "points_manager")
    private int pointsManager;

    @ColumnInfo(name = "group_players")
    private String group_players;

    @Ignore
    private long lastUpdated;

    public Group(@NonNull String id, String name, String password) {
        this.id = id;
        this.name = name;
        this.password = password;
    }


    @NonNull
    public String getId() {
        return id;
    }

    public void setId(@NonNull String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getGroupLogo() {
        return groupLogo;
    }

    public void setGroupLogo(String groupLogo) {
        this.groupLogo = groupLogo;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getLeagueId() {
        return leagueId;
    }

    public void setLeagueId(String leagueId) {
        this.leagueId = leagueId;
    }

    public String getLeagueName() {
        return leagueName;
    }

    public void setLeagueName(String leagueName) {
        this.leagueName = leagueName;
    }

    public long getLastUpdated() {
        return lastUpdated;
    }

    public void setLastUpdated(long lastUpdated) {
        this.lastUpdated = lastUpdated;
    }

    public int getPointsManager() {
        return pointsManager;
    }

    public void setPointsManager(int pointsManager) {
        this.pointsManager = pointsManager;
    }

    public String getGroup_players() {
        return group_players;
    }

    public void setGroup_players(String group_players) {
        this.group_players = group_players;
    }

}
