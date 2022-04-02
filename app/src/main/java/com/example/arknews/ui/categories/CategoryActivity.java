package com.example.arknews.ui.categories;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.arknews.R;
import com.example.arknews.dao.ARKDatabase;
import com.example.arknews.model.Category;
import com.google.android.material.appbar.MaterialToolbar;

import java.util.List;

public class CategoryActivity extends AppCompatActivity {

    private MaterialToolbar toolbar;
    private RecyclerView recyclerView;

    private List<Category> categoryList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_category_selection);

        toolbar = findViewById(R.id.category_toolbar);
        toolbar.setNavigationOnClickListener(v -> finish());

        categoryList = ARKDatabase.getInstance(this).categoryDao().getAll();

        recyclerView = findViewById(R.id.category_listview);
        CategoryAdapter adapter = new CategoryAdapter(categoryList, this);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(linearLayoutManager);
        recyclerView.setAdapter(adapter);
    }

}