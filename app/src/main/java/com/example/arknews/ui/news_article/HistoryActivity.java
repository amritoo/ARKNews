package com.example.arknews.ui.news_article;

import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
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

    List<History> historyList;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_history);

        initializeViews();
        setListeners();


        historyList = ARKDatabase.getInstance(getApplicationContext()).historyDao().getAll();
        HistoryListAdapter adapter = new HistoryListAdapter(this, historyList);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(linearLayoutManager);
        recyclerView.setAdapter(adapter);


    }

    void initializeViews() {
        toolbar = findViewById(R.id.history_toolbar);
        title = findViewById(R.id.history_news_title);
        publish_time = findViewById(R.id.history_news_published);
        news_image = findViewById(R.id.history_news_image);
        recyclerView = findViewById(R.id.rv_history);
    }

    void setListeners() {
        toolbar.setNavigationOnClickListener(v -> finish());


    }
}