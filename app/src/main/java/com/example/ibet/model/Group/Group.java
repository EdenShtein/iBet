package com.example.ibet.model.Group;

import androidx.annotation.NonNull;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.Ignore;
import androidx.room.PrimaryKey;

import com.example.ibet.model.User.User;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Entity(tableName = "group_table")
public class Group {

    @PrimaryKey()
    @NonNull
    @ColumnInfo(name = "group_id")
    private String id ;

    @ColumnInfo(name = "group_name")
    private String name;

    @ColumnInfo(name = "group_logo")
    private String groupLogo;

    @ColumnInfo(name = "group_admin")
    private String admin_id;

    @ColumnInfo(name = "league_id")
    private String leagueId;

    @ColumnInfo(name = "league_name")
    private String leagueName;

    @ColumnInfo(name = "points_total")
    private String pointsTotal;

    @ColumnInfo(name = "points_winner")
    private String pointsWinner;

    @ColumnInfo(name = "points_league_winner")
    private String pointsLeagueWinner;

    @ColumnInfo(name = "group_players")
    private String group_players;

    @ColumnInfo(name = "current_score")
    public static String current_score;

    @ColumnInfo(name = "share_code")
    private String shareCode;

    @Ignore
    User currentUser;


    @Ignore
    private long lastUpdated;

    public Group(@NonNull String id, String name, String admin_id) {
        this.id = id;
        this.name = name;
        this.admin_id = admin_id;
    }
    @Ignore
    public Group(){

    }
    @Ignore
    public Group(@NonNull String id, String name, String admin_id, User user) {
        this.id = id;
        this.name = name;
        this.admin_id = admin_id;
        this.currentUser = user;
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

    public String getAdmin_id() {
        return admin_id;
    }

    public void setAdmin_id(String admin_id) {
        this.admin_id = admin_id;
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

    public String getGroup_players() {
        return group_players;
    }

    public void setGroup_players(String group_players) {
        this.group_players = group_players;
    }

    public String getCurrent_score() {
        return current_score;
    }

    public void setCurrent_score(String current_score) {
        this.current_score = current_score;
    }

    public String getShareCode() {
        return shareCode;
    }

    public void setShareCode(String shareCode) {
        this.shareCode = shareCode;
    }

    public User getCurrentUser() {
        return currentUser;
    }

    public void setCurrentUser(User currentUser) {
        this.currentUser = currentUser;
    }

    public String getPointsTotal() {
        return pointsTotal;
    }

    public void setPointsTotal(String pointsTotal) {
        this.pointsTotal = pointsTotal;
    }

    public String getPointsWinner() {
        return pointsWinner;
    }

    public void setPointsWinner(String pointsWinner) {
        this.pointsWinner = pointsWinner;
    }

    public String getPointsLeagueWinner() {
        return pointsLeagueWinner;
    }

    public void setPointsLeagueWinner(String pointsLeagueWinner) {
        this.pointsLeagueWinner = pointsLeagueWinner;
    }

}
