package com.example.arknews.ui.categories;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

import com.example.arknews.R;
import com.google.android.material.appbar.MaterialToolbar;

public class CategorySelectionActivity extends AppCompatActivity {

    MaterialToolbar toolbar;

     @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_category_selection);

         toolbar = findViewById(R.id.category_toolbar);
         toolbar.setNavigationOnClickListener(v -> finish());
    }
}