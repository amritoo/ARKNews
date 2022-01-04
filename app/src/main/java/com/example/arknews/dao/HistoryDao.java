package com.example.arknews.dao;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import androidx.room.Update;

import com.example.arknews.model.History;

import java.util.List;

@Dao
public interface HistoryDao {

    /**
     * Search History using liveData
     */

//    @Query("SELECT * FROM history")
//    LiveData<List<History>> getAllHistory();
    @Query("SELECT * FROM history")
    List<History> getAll();

    //     ** Insert History **
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insert(History history);

    //     ** Update History **
    @Update
    void update(History history);


    //     ** Delete History **
    @Delete
    void delete(History history);

}
