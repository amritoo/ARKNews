package com.example.arknews.dao;

import androidx.room.Dao;
import androidx.room.Query;

import com.example.arknews.model.Channel;

import java.util.List;

@Dao
public interface ChannelDao {

    @Query("SELECT * FROM channel")
    List<Channel> getAll();

    /*
     * TODO complete: Karma
     * select
     * delete
     * update
     * insert
     *
     */
}
