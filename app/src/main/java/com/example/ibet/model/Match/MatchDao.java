package com.example.ibet.model.Match;

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
public interface MatchDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void Insert(Match match);

    @Update
    void Update(Match match);

    @Delete
    void delete(Match match);

    @Query("SELECT * FROM match_table ORDER BY match_id ASC")
    LiveData<List<Match>> getAllMatches();

    /*@Query("SELECT * FROM group_table WHERE group_id =:id")
    LiveData<Group> getGroupById(String id);
*/
}
