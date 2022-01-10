package com.example.arknews.ui.settings;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import com.example.arknews.R;
import com.google.android.material.appbar.MaterialToolbar;

public class NotifyActivity extends AppCompatActivity {

    MaterialToolbar toolbar;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_notify);

        initializeViews();
        setListeners();
    }

    void initializeViews() {
        toolbar = findViewById(R.id.notification_toolbar);
    }
    void setListeners() {
        toolbar.setNavigationOnClickListener(v -> finish());}
}