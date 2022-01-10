package com.example.arknews.ui.settings;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import com.example.arknews.R;
import com.google.android.material.appbar.MaterialToolbar;

public class AutoDelActivity extends AppCompatActivity {
    MaterialToolbar toolbar;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_auto_del);

        toolbar = findViewById(R.id.deletion_toolbar);
        toolbar.setNavigationOnClickListener(v -> finish());


    }
}