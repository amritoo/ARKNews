package com.example.arknews.utility;

import android.content.Context;

import com.example.arknews.client.NewsApiD;
import com.example.arknews.dao.ARKDatabase;
import com.example.arknews.model.Category;
import com.example.arknews.model.FAQ;

public class PopulateDatabase {

    public static final String POPULATED = "is_populated";
    private static final String TAG = "PopulateDB";
    private Context context;

    public PopulateDatabase(Context context) {
        this.context = context;
        populateCategory();
        populateChannel();
        populateFAQ();
    }

    void populateCategory() {
        Category category = new Category("business", "Business news", "");
        ARKDatabase.getInstance(context).categoryDao().insert(category);
        category = new Category("education", "Education news", "");
        ARKDatabase.getInstance(context).categoryDao().insert(category);
        category = new Category("entertainment", "Entertainment news", "");
        ARKDatabase.getInstance(context).categoryDao().insert(category);
        category = new Category("general", "General news", "");
        ARKDatabase.getInstance(context).categoryDao().insert(category);
        category = new Category("health", "Health news", "");
        ARKDatabase.getInstance(context).categoryDao().insert(category);
        category = new Category("science", "Science news", "");
        ARKDatabase.getInstance(context).categoryDao().insert(category);
        category = new Category("sports", "Sports news", "");
        ARKDatabase.getInstance(context).categoryDao().insert(category);
        category = new Category("politics", "Politics news", "");
        ARKDatabase.getInstance(context).categoryDao().insert(category);
        category = new Category("technology", "Technology news", "");
        ARKDatabase.getInstance(context).categoryDao().insert(category);
    }

    void populateChannel() {
        new NewsApiD(context).getChannels();
    }

    void populateFAQ() {
        FAQ faq = new FAQ("What's the name of this app?", "ARK News.");
        ARKDatabase.getInstance(context).faqDao().insert(faq);
    }

}
