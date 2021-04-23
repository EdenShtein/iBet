package com.example.ibet.model;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;

import com.example.ibet.model.User.User;
import com.example.ibet.model.User.UserLog;

import java.util.List;

@Dao
interface UserOnlyDao {

    @Insert
    long insertUserLog(UserLog userLog);

    @Query("SELECT * FROM user_log_table")
    List<UserLog> getUserLogs();

}
