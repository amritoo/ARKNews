package com.example.arknews.utility;

import android.content.Context;
import android.util.Log;

import com.example.arknews.client.NewsApi;
import com.example.arknews.dao.ARKDatabase;
import com.example.arknews.model.Category;

public class PopulateDatabase {

    public static final String POPULATED = "is_populated";
    private static final String TAG = "PopulateDB";
    private Context context;

    public PopulateDatabase(Context context) {
        this.context = context;
        populateCategory();
        populateChannel();
        Log.i(TAG, "Database created and populated");
    }

    void populateCategory() {
        Category category = new Category("business", "Business");
        ARKDatabase.getInstance(context).categoryDao().insert(category);
        category = new Category("education", "Education");
        ARKDatabase.getInstance(context).categoryDao().insert(category);
        category = new Category("entertainment", "Entertainment");
        ARKDatabase.getInstance(context).categoryDao().insert(category);
        category = new Category("general", "General");
        ARKDatabase.getInstance(context).categoryDao().insert(category);
        category = new Category("health", "Health");
        ARKDatabase.getInstance(context).categoryDao().insert(category);
        category = new Category("science", "Science");
        ARKDatabase.getInstance(context).categoryDao().insert(category);
        category = new Category("sports", "Sports");
        ARKDatabase.getInstance(context).categoryDao().insert(category);
        category = new Category("politics", "Politics");
        ARKDatabase.getInstance(context).categoryDao().insert(category);
        category = new Category("technology", "Technology");
        ARKDatabase.getInstance(context).categoryDao().insert(category);
    }

    void populateChannel() {
        new NewsApi(context).getChannels();
    }

}
