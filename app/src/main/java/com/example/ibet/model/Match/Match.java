package com.example.ibet.model.Match;

import java.text.SimpleDateFormat;
import java.util.Date;

public class Match {

    String id;
    String home;
    String away;
    String date;

    public Match(String id, String home, String away, String date) {
        this.id = id;
        this.home = home;
        this.away = away;
        this.date = date;

    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
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
}
