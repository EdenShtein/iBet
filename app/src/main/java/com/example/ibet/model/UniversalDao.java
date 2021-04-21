package com.example.ibet.model;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;

import com.example.ibet.model.User.User;
import com.example.ibet.model.User.UserLog;

import java.util.List;

@Dao
interface UniversalDao {

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    long insertUser(User user);
    @Insert
    long insertLog(UserLog userLog);
    @Query("SELECT * FROM user_table WHERE user_id=:userId")
    User getUserById(long userId);
    @Query("SELECT * FROM user_log_table WHERE userId=:userId")
    List<UserLog> getUserLogs(long userId);

}
