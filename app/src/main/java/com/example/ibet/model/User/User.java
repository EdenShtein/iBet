package com.example.ibet.model.User;

import androidx.annotation.NonNull;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.Ignore;
import androidx.room.PrimaryKey;

@Entity(tableName = "user_table")
public class User {

    @PrimaryKey()
    @NonNull
    @ColumnInfo(name = "user_id")
    String id;

    @ColumnInfo(name = "user_email")
    String email;

    @ColumnInfo(name = "user_name")
    String userName;

    @ColumnInfo(name = "user_rank")
    String rank;

    @ColumnInfo(name = "user_score")
    String score;

    public User(@NonNull String id, String email, String userName, String rank, String score) {
        this.id = id;
        this.email = email;
        this.userName = userName;
        this.rank = rank;
        this.score = score;
    }

    @Ignore
    public User(String id, String email,String username) {
        this.id = id;
        this.email = email;
        this.userName = username;
    }

    @Ignore
    public User(String email,String username) {
        this.email = email;
        this.userName = username;
    }

    @Ignore
    public User() {

    }

    @Ignore
    public User(String id) {
        this.id = id;
    }

    @NonNull
    public String getId() {
        return id;
    }

    public void setId(@NonNull String id) {
        this.id = id;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getScore() {
        return score;
    }

    public void setScore(String score) {
        this.score = score;
    }

    public String getRank() {
        return rank;
    }

    public void setRank(String rank) {
        this.rank = rank;
    }


}
