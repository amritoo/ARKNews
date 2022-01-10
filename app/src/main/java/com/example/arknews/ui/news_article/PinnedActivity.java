package com.example.arknews.ui.news_article;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.recyclerview.widget.RecyclerView;

import com.example.arknews.R;
import com.google.android.material.appbar.MaterialToolbar;

public class PinnedActivity extends AppCompatActivity {

    DrawerLayout drawerLayout;
    MaterialToolbar toolbar;
    RecyclerView recyclerView;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pinned);

        initializeViews();
        setListeners();


    }

    void initializeViews() {
        drawerLayout = findViewById(R.id.drawer_layout);
        toolbar = findViewById(R.id.pinned_toolbar);
        recyclerView = findViewById(R.id.newsfeed_rv);

    }

    void setListeners() {
        toolbar.setNavigationOnClickListener(v -> finish());


    }


    @Override
    public void onBackPressed() {
        super.onBackPressed();
        overridePendingTransition(R.anim.pull_in_left, R.anim.push_out_right);
    }


}
