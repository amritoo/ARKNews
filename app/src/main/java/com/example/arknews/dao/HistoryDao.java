package com.example.arknews.dao;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import androidx.room.Update;

import com.example.arknews.model.History;
import com.example.arknews.model.News;

import java.util.List;

@Dao
public interface HistoryDao {

    /**
     * Search History
     */
    @Query("SELECT * FROM history ORDER BY published DESC")
    List<History> getAll();

    @Query("SELECT * FROM history WHERE title LIKE :query")
    List<History> getBySpecificQueryHist(String query);

    //     ** Insert History **
//    @Query("INSERT INTO history (channel_id, category_id, title, author, published, url, url_image, news_id)")
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insert(History history);

    //     ** Update History **
    @Update
    void update(History history);


    //     ** Delete History **
    @Delete
    void delete(History history);

}
