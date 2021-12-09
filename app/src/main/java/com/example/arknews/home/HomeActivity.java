package com.example.arknews.home;

import android.content.Intent;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;

import com.example.arknews.R;
import com.example.arknews.settings.SettingsActivity;
import com.google.android.material.appbar.MaterialToolbar;
import com.google.android.material.navigation.NavigationView;

public class HomeActivity extends AppCompatActivity {

    DrawerLayout drawerLayout;
    MaterialToolbar toolbar;
    NavigationView navigationView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        initializeViews();
        setListeners();
    }

    void initializeViews() {
        drawerLayout = findViewById(R.id.drawer);
        toolbar = findViewById(R.id.home_toolbar);
        navigationView = findViewById(R.id.nav_view);
    }

    void setListeners() {
        toolbar.setNavigationOnClickListener(v -> drawerLayout.openDrawer(GravityCompat.START));

        navigationView.setNavigationItemSelectedListener(item -> {
            Intent intent = null;
            switch (item.getItemId()) {
//                case R.id.menu_home:
                case R.id.menu_pinned:
                case R.id.menu_favorite:
                case R.id.menu_history:
                    break;
                case R.id.menu_settings:
                    intent = new Intent(HomeActivity.this, SettingsActivity.class);
                    break;
                case R.id.menu_help:
                case R.id.menu_about:
                case R.id.menu_rating:
                    int i = 0;
                    break;
            }
            startActivity(intent);
            return true;
        });
    }
}