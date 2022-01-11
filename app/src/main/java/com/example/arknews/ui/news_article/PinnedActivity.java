package com.example.arknews.ui.news_article;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.arknews.R;
import com.example.arknews.dao.ARKDatabase;
import com.example.arknews.model.History;
import com.example.arknews.model.News;
import com.example.arknews.ui.home.NewsfeedAdapter;
import com.google.android.material.appbar.MaterialToolbar;

import java.util.List;

public class PinnedActivity extends AppCompatActivity {

    MaterialToolbar toolbar;
    RecyclerView recyclerView;

    List<News> pinnedList;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pinned);

        initializeViews();
        setListeners();

        pinnedList = ARKDatabase.getInstance(getApplicationContext()).newsDao().getAllPinned();

        NewsfeedAdapter adapter = new NewsfeedAdapter(this, pinnedList);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(linearLayoutManager);
        recyclerView.setAdapter(adapter);
    }

    void initializeViews() {
        toolbar = findViewById(R.id.pinned_toolbar);
        recyclerView = findViewById(R.id.pinned_rv);
    }

    void setListeners() {
        toolbar.setNavigationOnClickListener(v -> finish());
    }

}
