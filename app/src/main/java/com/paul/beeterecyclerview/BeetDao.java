package com.paul.beeterecyclerview;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import androidx.room.Update;

import java.util.List;

@Dao
public interface BeetDao {

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    void insert(Beet beet);

    @Query("DELETE FROM beet")
        //SQL syntax
    void deleteAll();

    @Delete
    void deleteWord(Beet beet);

    @Query("SELECT * from beet ORDER BY description ASC")
        //SQL syntax
    LiveData<List<Beet>> getAllBeete();

    @Query("SELECT * from beet WHERE description = :name")
    LiveData<Beet> getBeetByName(String name);

    @Query("SELECT * from beet WHERE id = :id")
    LiveData<Beet> getBeetByID(String id);

    @Update(onConflict = OnConflictStrategy.REPLACE)
    void update(Beet beet);

}