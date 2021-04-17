package com.example.ibet.model.User;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import androidx.room.Update;
import java.util.List;

@Dao
public interface UserDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void Insert(User user);

    @Update
    void Update(User user);

    @Delete
    void delete(User user);

    @Query("SELECT * FROM user_table ORDER BY user_id ASC")
    LiveData<List<User>> getAllUsers();

}
