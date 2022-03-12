package com.example.arknews.dao;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import androidx.room.Update;

import com.example.arknews.model.News;

import java.util.Date;
import java.util.List;

@Dao
public interface NewsDao {

    @Query("SELECT * FROM news ORDER BY published DESC")
    List<News> getAll();

    @Query("SELECT * FROM news WHERE channel_id = :channelId ORDER BY published DESC")
    List<News> getAllChannelNews(int channelId);

    @Query("SELECT * FROM news WHERE pinned = 1")
    List<News> getAllPinned();

    @Query("SELECT * FROM news WHERE id = :id")
    News getById(int id);

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insert(News news);

    @Delete
    void delete(News news);

    @Update
    void update(News news);

    @Query("DELETE FROM news WHERE published <= :date")
    void deleteBefore(Date date);
}
