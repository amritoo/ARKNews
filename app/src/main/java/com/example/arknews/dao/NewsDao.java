package com.example.arknews.dao;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import androidx.room.Update;

import com.example.arknews.model.News;

import java.util.List;

@Dao
public interface NewsDao {

    @Query("SELECT * FROM news")
    LiveData<List<News>> getAllNews();

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insertAll(News news);

    @Delete
    void delete(News news);

    @Update
    void update(News news);

}
