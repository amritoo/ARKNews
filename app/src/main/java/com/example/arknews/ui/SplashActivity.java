package com.example.arknews.ui;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AppCompatDelegate;

import com.example.arknews.R;
import com.example.arknews.ui.home.HomeActivity;
import com.example.arknews.utility.PopulateDatabase;
import com.example.arknews.utility.Preferences;

public class SplashActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);

        // Setting theme
        boolean isChecked = Preferences.getInstance(this).read("theme", false);
        if (isChecked) {
            AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES);
        } else {
            AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO);
        }
        // Populating new database
        boolean isPopulated = Preferences.getInstance(this).read(PopulateDatabase.POPULATED, false);
        if (!isPopulated) {
            new PopulateDatabase(this);
            Preferences.getInstance(this).write(PopulateDatabase.POPULATED, true);
        }

        Handler handler = new Handler(this.getMainLooper());
        handler.postDelayed(() -> {
            Intent intent = new Intent(SplashActivity.this, HomeActivity.class);
            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
            startActivity(intent);
            finish();
        }, 1000);
    }

}