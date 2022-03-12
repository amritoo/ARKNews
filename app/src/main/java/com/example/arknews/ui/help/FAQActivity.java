package com.example.arknews.ui.help;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.arknews.R;
import com.example.arknews.model.FAQ;
import com.google.android.material.appbar.MaterialToolbar;

import java.util.ArrayList;

public class FAQActivity extends AppCompatActivity {

    private ArrayList<FAQ> faqArrayList;

    RecyclerView recyclerView;
    FaqAdapter adapter, adapter2;

    MaterialToolbar toolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_faq);

        toolbar = findViewById(R.id.manual_toolbar);
        recyclerView = findViewById(R.id.faq_rv);
        toolbar.setNavigationOnClickListener(v -> finish());

        populateList();
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        adapter = new FaqAdapter(faqArrayList);
        recyclerView.setAdapter(adapter);

    }

    void populateList() {
        faqArrayList = new ArrayList<>();
        faqArrayList.add(new FAQ("Question1", "Answer1"));
        faqArrayList.add(new FAQ("Question2", "Answer2"));
        faqArrayList.add(new FAQ("Question3", "Answer3"));
    }
}
