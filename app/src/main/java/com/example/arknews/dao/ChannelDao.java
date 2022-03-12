package com.example.arknews.dao;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import com.example.arknews.model.Channel;

import java.util.List;

@Dao
public interface ChannelDao {

    @Query("SELECT * FROM channel ORDER BY name ASC")
    List<Channel> getAll();

    @Query("SELECT id FROM channel where api_id = :apiId")
    int getChannelId(String apiId);

    @Query("SELECT url FROM channel where id = :channelId")
    String getChannelImageUrl(int channelId);

    @Query("SELECT name FROM channel where id = :channelId")
    String getChannelName(int channelId);

    @Insert
    void insert(Channel channel);

    @Update
    void update(Channel channel);

    @Delete
    void delete(Channel channel);

    @Query("SELECT category_id FROM channel where api_id = :apiId")
    int getCategoryId(String apiId);
}
