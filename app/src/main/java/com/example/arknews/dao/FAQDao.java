package com.example.arknews.dao;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import com.example.arknews.model.FAQ;

import java.util.List;

@Dao
public interface FAQDao {

    @Query("SELECT * FROM faq")
    List<FAQ> getAll();

    @Insert
    void insert(FAQ faq);

    @Insert
    void insertAll(FAQ... faqs);

    @Update
    void update(FAQ faq);

    @Delete
    void delete(FAQ faq);

}
