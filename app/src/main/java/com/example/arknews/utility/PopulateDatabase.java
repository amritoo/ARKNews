package com.example.arknews.utility;

import android.content.Context;

import com.example.arknews.client.NewsApi;

public class PopulateDatabase {

    public static final String POPULATED = "is_populated";
    private Context context;

    public PopulateDatabase(Context context) {
        this.context = context;
        populateCategory();
        populateChannel();
        populateFAQ();
    }

    void populateCategory() {
        /*
                - business
                - education
                - entertainment
                - general
                - health
                - science
                - sports
                - politics
                - technology
        */
    }

    void populateChannel() {
        new NewsApi(context).getChannels();
    }

    void populateFAQ() {

    }

}
