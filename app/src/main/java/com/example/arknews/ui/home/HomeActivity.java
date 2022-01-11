package com.example.arknews.ui.home;

import android.content.Intent;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.arknews.R;
import com.example.arknews.client.NewsApiD;
import com.example.arknews.dao.ARKDatabase;
import com.example.arknews.model.News;
import com.example.arknews.ui.favourite.FavoriteChannels;
import com.example.arknews.ui.help.AboutActivity;
import com.example.arknews.ui.help.FAQActivity;
import com.example.arknews.ui.news_article.HistoryActivity;
import com.example.arknews.ui.news_article.PinnedActivity;
import com.example.arknews.ui.settings.SettingsActivity;
import com.google.android.material.appbar.MaterialToolbar;
import com.google.android.material.floatingactionbutton.ExtendedFloatingActionButton;
import com.google.android.material.navigation.NavigationView;

import java.util.List;

public class HomeActivity extends AppCompatActivity {

    DrawerLayout drawerLayout;
    MaterialToolbar toolbar;
    NavigationView navigationView;
    RecyclerView mRecyclerView;
    ExtendedFloatingActionButton floatingActionButton;

    List<News> mNewsList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        initializeViews();
        setListeners();

        new NewsApiD(this).getChannelNews("bbc-news");

        mNewsList = ARKDatabase.getInstance(getApplicationContext()).newsDao().getAll();
        NewsfeedAdapter adapter = new NewsfeedAdapter(this, mNewsList);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        mRecyclerView.setLayoutManager(linearLayoutManager);
        mRecyclerView.setAdapter(adapter);
    }

    private void initializeViews() {
        drawerLayout = findViewById(R.id.drawer_layout);
        toolbar = findViewById(R.id.favorite_toolbar);
        navigationView = findViewById(R.id.nav_view);
        mRecyclerView = findViewById(R.id.newsfeed_rv);
        floatingActionButton = findViewById(R.id.home_extended_fab);
    }

    // sets listeners to views
    private void setListeners() {
        toolbar.setNavigationOnClickListener(v -> drawerLayout.openDrawer(GravityCompat.START));

        floatingActionButton.setOnClickListener(view -> {
            // TODO complete + also make visible when recycle view is scrolled
        });

        navigationView.setNavigationItemSelectedListener(item -> {
            Intent intent = null;
            switch (item.getItemId()) {
                case R.id.menu_refresh:
                    refresh();
                    break;
                case R.id.menu_pinned:
                    intent = new Intent(HomeActivity.this, PinnedActivity.class);
                    break;
                case R.id.menu_favorite:
                    intent = new Intent(HomeActivity.this, FavoriteChannels.class);
                    break;
                case R.id.menu_history:
                    intent = new Intent(HomeActivity.this, HistoryActivity.class);
                    break;
                case R.id.menu_settings:
                    intent = new Intent(HomeActivity.this, SettingsActivity.class);
                    break;
                case R.id.menu_help:
                    intent = new Intent(HomeActivity.this, FAQActivity.class);
                    break;
                case R.id.menu_about:
                    intent = new Intent(HomeActivity.this, AboutActivity.class);
                    break;
                case R.id.menu_rating:
                    // TODO add rate us view
                    break;
            }
            startActivity(intent);
            return true;
        });
    }

    private void refresh() {
        // TODO
    }

}