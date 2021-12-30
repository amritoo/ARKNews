package com.example.arknews.ui.news_article;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

import com.example.arknews.R;
import com.google.android.material.appbar.MaterialToolbar;

public class HistoryActivity extends AppCompatActivity {

    MaterialToolbar toolbar;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_history);

        toolbar = findViewById(R.id.history_toolbar);
        toolbar.setNavigationOnClickListener(v -> finish());

    }
}