package com.example.ibet.model.Team;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import androidx.room.Update;

import com.example.ibet.model.Group.Group;

import java.util.List;

@Dao
public interface TeamDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void Insert(Team team);

    @Update
    void Update(Team team);

    @Delete
    void delete(Team team);

    @Query("SELECT * FROM team_table ORDER BY team_id ASC")
    LiveData<List<Team>> getAllTeams();
}
