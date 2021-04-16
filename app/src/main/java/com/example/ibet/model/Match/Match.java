package com.example.ibet.model.Match;

import androidx.annotation.NonNull;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.Ignore;
import androidx.room.PrimaryKey;

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


    public Match(@NonNull String id, String home, String away, String date) {
        this.id = id;
        this.home = home;
        this.away = away;
        this.date = date;
        this.isBetted = false;
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

}
