package com.paul.beeterecyclerview;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;

import java.util.List;

@Dao
public interface BeetDoa {

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    void insert(Beet beet);

    @Query("DELETE FROM beet")
        //SQL syntax
    void deleteAll();

    @Query("SELECT * from beet ORDER BY description ASC")
        //SQL syntax
    LiveData<List<Beet>> getAllBeete();

}