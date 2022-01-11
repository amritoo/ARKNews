package com.example.arknews.ui.help;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import com.example.arknews.R;
import com.google.android.material.appbar.MaterialToolbar;

public class FAQActivity extends AppCompatActivity {

    MaterialToolbar toolbar;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_faq);

        initializeViews();
        setListeners();
    }

    void initializeViews() {
        toolbar = findViewById(R.id.manual_toolbar);
    }
    void setListeners() {
        toolbar.setNavigationOnClickListener(v -> finish());}
}