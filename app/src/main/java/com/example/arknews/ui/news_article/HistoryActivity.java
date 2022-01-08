package com.example.arknews.ui.news_article;

import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import com.example.arknews.R;
import com.example.arknews.dao.ARKDatabase;
import com.example.arknews.model.History;
import com.google.android.material.appbar.MaterialToolbar;

import java.util.List;

public class HistoryActivity extends AppCompatActivity {

    MaterialToolbar toolbar;
    TextView title;
    TextView publish_time;
    ImageView news_image;
    RecyclerView recyclerView;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_history);

        initializeViews();
        setListeners();

        List<History> historyList = ARKDatabase.getInstance(getApplicationContext()).historyDao().getAll();
    }

    void initializeViews() {
        toolbar = findViewById(R.id.history_toolbar);
        title = findViewById(R.id.history_news_title);
        publish_time = findViewById(R.id.history_news_published);
        news_image = findViewById(R.id.news_image);
        recyclerView = findViewById(R.id.rv_history);
    }

    void setListeners() {
        toolbar.setNavigationOnClickListener(v -> finish());


    }
}