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

    @Query("SELECT id FROM category WHERE is_selected = 1")
    List<Integer> getAllSelectedIds();

    @Query("SELECT id FROM category WHERE name = :source")
    int getCategoryId(String source);

    @Query("SELECT name FROM category WHERE id = :id")
    String getCategoryNameById(int id);

    @Query("SELECT description FROM category WHERE id = :id")
    String getCategoryDescriptionById(int id);

    @Query("SELECT description FROM category WHERE id = :id")
    String getCategoryDescById(int id);

    @Query("SELECT * FROM category where id = :id")
    Category getCategoryById(int id);

    @Insert
    void insert(Category category);

    @Update
    void update(Category category);

    @Delete
    void delete(Category category);

}
