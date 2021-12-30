package com.example.arknews.dao;

import androidx.room.Dao;
import androidx.room.Query;

import com.example.arknews.model.News;

import java.util.List;

@Dao
public interface NewsDao {

    @Query("SELECT * FROM news")
    List<News> getAll();

    /*
     * TODO complete: Priyanka
     * select
     * delete
     * update
     * insert
     *
     */
}
