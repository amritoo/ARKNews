package com.example.arknews.ui.favourite;

import static com.example.arknews.R.*;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import com.example.arknews.R;
import com.google.android.material.appbar.MaterialToolbar;

public class FavoriteChannels extends AppCompatActivity {

    MaterialToolbar toolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(layout.activity_favorite_channels);

        toolbar = findViewById(R.id.favorite_toolbar);
        toolbar.setNavigationOnClickListener(v -> finish());
    }
}