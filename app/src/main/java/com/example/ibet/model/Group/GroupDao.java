package com.example.ibet.model.Group;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import androidx.room.Update;

import java.util.List;

@Dao
public interface GroupDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void Insert(Group group);

    @Update
    void Update(Group group);

    @Delete
    void delete(Group group);

    @Query("SELECT * FROM group_table ORDER BY group_id ASC")
    LiveData<List<Group>> getAllGroups();

    /*@Query("SELECT * FROM group_table WHERE group_id =:id")
    LiveData<Group> getGroupById(String id);
*/
}
