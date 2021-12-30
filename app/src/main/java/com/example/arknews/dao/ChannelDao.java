package com.example.arknews.dao;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import com.example.arknews.model.Category;
import com.example.arknews.model.Channel;

import java.util.List;

@Dao
public interface ChannelDao {

    @Query("SELECT * FROM channel")
    List<Channel> getAll();

    @Insert
    void insert(Channel channel);

    @Update
    void update(Channel channel);

    @Delete
    void delete(Channel channel);

}
