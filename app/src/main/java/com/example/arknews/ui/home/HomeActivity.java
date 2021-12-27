package com.example.arknews.ui.home;

import android.content.Intent;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.recyclerview.widget.RecyclerView;

import com.example.arknews.ui.favourite.FavoriteChannels;
import com.example.arknews.R;
import com.example.arknews.ui.news_article.HistoryActivity;
import com.example.arknews.ui.news_article.PinnedActivity;
import com.example.arknews.ui.settings.SettingsActivity;
import com.example.arknews.ui.settings.UserManualActivity;
import com.google.android.material.appbar.MaterialToolbar;
import com.google.android.material.navigation.NavigationView;

public class HomeActivity extends AppCompatActivity {

    DrawerLayout drawerLayout;
    MaterialToolbar toolbar;
    NavigationView navigationView;
    RecyclerView recyclerView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        initializeViews();
        setListeners();
    }

    void initializeViews() {
        drawerLayout = findViewById(R.id.drawer_layout);
        toolbar = findViewById(R.id.favorite_toolbar);
        navigationView = findViewById(R.id.nav_view);
        recyclerView = findViewById(R.id.rv_newsfeed);
    }

    void setListeners() {
        toolbar.setNavigationOnClickListener(v -> drawerLayout.openDrawer(GravityCompat.START));

        navigationView.setNavigationItemSelectedListener(item -> {
            Intent intent = null;
            switch (item.getItemId()) {
//                case R.id.menu_home:
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
                    intent = new Intent(HomeActivity.this, UserManualActivity.class);
                    break;
                case R.id.menu_about:
//                    intent = new Intent(HomeActivity.this, AboutActivity.class);
                    break;
                case R.id.menu_rating:
                    // TODO add rate us view
                    break;
            }
            startActivity(intent);
            return true;
        });
    }
}