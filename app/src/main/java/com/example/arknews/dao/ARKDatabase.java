package com.example.arknews.dao;

import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import androidx.room.TypeConverters;

import com.example.arknews.model.Category;
import com.example.arknews.model.Channel;
import com.example.arknews.model.FAQ;
import com.example.arknews.model.History;
import com.example.arknews.model.News;
import com.example.arknews.utility.Converters;

@Database(
        entities = {News.class, Channel.class, Category.class, History.class, FAQ.class},
        version = 1
)
@TypeConverters({Converters.class})
public abstract class ARKDatabase extends RoomDatabase {

    public static final String DB_name = "ark_db";
    private static ARKDatabase instance;

    public static synchronized ARKDatabase getInstance(Context context) {
        if (instance == null) {
            instance = Room.databaseBuilder(
                    context.getApplicationContext(),
                    ARKDatabase.class,
                    DB_name
            ).allowMainThreadQueries().build();
        }
        return instance;
    }

    public abstract NewsDao newsDao();

    public abstract ChannelDao channelDao();

    public abstract CategoryDao categoryDao();

    public abstract HistoryDao historyDao();

    public abstract FAQDao faqDao();

}
