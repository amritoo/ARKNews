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

    @Query("SELECT * FROM history ORDER BY published DESC")
    List<History> getAll();

    @Query("SELECT * FROM history WHERE title LIKE :query ORDER BY published DESC")
    List<History> getBySpecificQueryHist(String query);

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insert(History history);

    @Update
    void update(History history);

    @Delete
    void delete(History history);

    @Query("DELETE FROM history WHERE id IN (:selectedIds)")
    void deleteAllSelected(List<Integer> selectedIds);

}
