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


//    @Query("SELECT id FROM channel where   ")
//    int getChannelId(String source);

     public default int getChannelId(String source){

       int channelId = Integer.parseInt(("SELECT id FROM channel where name =" +source));
       return channelId;

    }

    @Insert
    void insert(Channel channel);

    @Update
    void update(Channel channel);

    @Delete
    void delete(Channel channel);

}
