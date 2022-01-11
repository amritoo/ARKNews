package com.example.arknews.dao;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import com.example.arknews.model.Category;

import java.util.List;

@Dao
public interface CategoryDao {

    @Query("SELECT * FROM category")
    List<Category> getAll();

    @Query("SELECT id FROM category WHERE name = :source")
    int getCategoryId(String source);

    @Query("SELECT name FROM category WHERE id = :id")
    String getCategoryNameById(int id);

    @Insert
    void insert(Category category);

    @Update
    void update(Category category);

    @Delete
    void delete(Category category);

}
