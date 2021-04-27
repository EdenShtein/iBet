package com.example.ibet.model.Match;

import androidx.annotation.NonNull;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.Ignore;
import androidx.room.PrimaryKey;

import com.example.ibet.model.Bets.Bet;

import java.text.SimpleDateFormat;
import java.util.Date;

@Entity(tableName = "match_table")
public class Match {

    @PrimaryKey()
    @NonNull
    @ColumnInfo(name = "match_id")
    String id;

    @ColumnInfo(name = "match_home")
    String home;

    @ColumnInfo(name = "match_away")
    String away;

    @ColumnInfo(name = "match_date")
    String date;

    @Ignore
    public boolean isBetted;

    @ColumnInfo(name = "match_finished")
    String status;

    @ColumnInfo(name = "match_hScore")
    int hScore;

    @ColumnInfo(name = "match_vScore")
    int vScore;

    @Ignore
    String homeImageUrl;

    @Ignore
    String awayImageUrl;

    @Ignore
    Bet userBet;

    public Match(@NonNull String id, String home, String away, String date,String status,int hScore,int vScore) {
        this.id = id;
        this.home = home;
        this.away = away;
        this.date = date;
        this.isBetted = false;
        this.status = status;
        this.hScore = hScore;
        this.vScore = vScore;
    }

    @Ignore
    public Match(@NonNull String id, String home, String away, String date,String status,int hScore,int vScore, String homeImage, String awayImage) {
        this.id = id;
        this.home = home;
        this.away = away;
        this.date = date;
        this.isBetted = false;
        this.status = status;
        this.hScore = hScore;
        this.vScore = vScore;
        this.homeImageUrl = homeImage;
        this.awayImageUrl = awayImage;
    }

    @NonNull
    public String getId() {
        return id;
    }

    public void setId(@NonNull String id) {
        this.id = id;
    }

    public String getHome() {
        return home;
    }

    public void setHome(String home) {
        this.home = home;
    }

    public String getAway() {
        return away;
    }

    public void setAway(String away) {
        this.away = away;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public boolean isBetted() {
        return isBetted;
    }

    public void setBetted(boolean betted) {
        isBetted = betted;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public int gethScore() {
        return hScore;
    }

    public void sethScore(int hScore) {
        this.hScore = hScore;
    }

    public int getvScore() {
        return vScore;
    }

    public void setvScore(int vScore) {
        this.vScore = vScore;
    }

    public String getHomeImageUrl() {
        return homeImageUrl;
    }

    public String getAwayImageUrl() {
        return awayImageUrl;
    }

    public void setHomeImageUrl(String homeImageUrl) {
        this.homeImageUrl = homeImageUrl;
    }

    public void setAwayImageUrl(String awayImageUrl) {
        this.awayImageUrl = awayImageUrl;
    }

    public Bet getUserBet() {
        return userBet;
    }

    public void setUserBet(Bet userBet) {
        this.userBet = userBet;
    }
}
