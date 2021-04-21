package com.example.ibet.model.User;

import androidx.room.Entity;
import androidx.room.Ignore;
import androidx.room.PrimaryKey;

@Entity(tableName = "user_log_table")
public class UserLog {
    @PrimaryKey
    Long id;
    Long timestamp;
    String userId;
    String logData;

    public UserLog(){}

    @Ignore
    public UserLog(User user, String logData) {
        this.id = null;
        this.timestamp = System.currentTimeMillis();
        this.userId = user.getId();
        this.logData = logData;
    }

    public Long getId() {
        return id;
    }
    public String getUserId() {
        return userId;
    }
    public Long getTimestamp() {
        return timestamp;
    }
    public String getLogData() {
        return logData;
    }
    public void setId(Long id) {
        this.id = id;
    }
    public void setUserId(String userId) {
        this.userId = userId;
    }
    public void setTimestamp(Long timestamp) {
        this.timestamp = timestamp;
    }
    public void setLogData(String logData) {
        this.logData = logData;
    }
}