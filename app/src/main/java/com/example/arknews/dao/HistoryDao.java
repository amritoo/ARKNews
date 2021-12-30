package com.example.arknews.dao;

import androidx.room.Dao;
import androidx.room.Query;

import com.example.arknews.model.History;

import java.util.List;

@Dao
public interface HistoryDao {

    @Query("SELECT * FROM history")
    List<History> getAll();

    /*
     * TODO complete: Prashant
     * select
     * delete
     * update
     * insert
     *
     */
}
