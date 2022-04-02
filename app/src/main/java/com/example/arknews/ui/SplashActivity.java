package com.example.arknews.ui;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AppCompatDelegate;

import com.example.arknews.R;
import com.example.arknews.ui.home.HomeActivity;
import com.example.arknews.utility.Constants;
import com.example.arknews.utility.Methods;
import com.example.arknews.utility.PopulateDatabase;
import com.example.arknews.utility.Preferences;

public class SplashActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);

        // Setting theme
        String theme = Preferences.getInstance(this).read(Constants.THEME, Constants.LIGHT);
        if (theme.equals(Constants.DARK)) {
            AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES);
        } else {
            AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO);
        }

        // Setting language
        Methods.setLanguagePref(this);

        // Populating new database
        boolean isPopulated = Preferences.getInstance(this).read(PopulateDatabase.POPULATED, false);
        if (!isPopulated) {
            new PopulateDatabase(this);
            Preferences.getInstance(this).write(PopulateDatabase.POPULATED, true);
        }

        // Auto-delete expired news from news table
        Methods.autoDelete(this);

        Handler handler = new Handler(this.getMainLooper());
        handler.postDelayed(() -> {
            Intent intent = new Intent(SplashActivity.this, HomeActivity.class);
            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
            startActivity(intent);
            finish();
        }, 2000);
    }

}