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

    @Query("SELECT * FROM news WHERE title LIKE :query OR content LIKE :query")
    List<News> getBySpecificQuery(String query);

    @Query("SELECT * FROM news WHERE channel_id = :channelId ORDER BY published DESC")
    List<News> getAllChannelNews(int channelId);

    @Query("SELECT * FROM news WHERE pinned = 1")
    List<News> getAllPinned();

    @Query("SELECT * FROM news WHERE id = :id")
    News getById(int id);

    @Query("SELECT * FROM news WHERE url = :url")
    News getByUrl(String url);

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    void insert(News news);

    @Delete
    void delete(News news);

    @Update
    void update(News news);

    @Query("DELETE FROM news WHERE published <= :date")
    void deleteBefore(Date date);
}
