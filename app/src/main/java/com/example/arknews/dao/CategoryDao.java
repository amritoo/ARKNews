package com.example.arknews.dao;

import androidx.room.Dao;
import androidx.room.Query;

import com.example.arknews.model.Category;

import java.util.List;

@Dao
public interface CategoryDao {

    @Query("SELECT * FROM category")
    List<Category> getAll();

    /*
     * TODO complete: Karma
     * select
     * delete
     * update
     * insert
     *
     */

}
