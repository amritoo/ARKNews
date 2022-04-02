package com.example.arknews.ui.pinned_news;

import android.app.Dialog;
import android.os.Bundle;
import android.view.View;
import android.widget.RadioButton;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.arknews.R;
import com.example.arknews.dao.ARKDatabase;
import com.example.arknews.model.News;
import com.example.arknews.ui.home.NewsfeedAdapter;
import com.google.android.material.appbar.MaterialToolbar;

import java.util.Collections;
import java.util.List;

public class PinnedActivity extends AppCompatActivity {

    private MaterialToolbar toolbar;

    private RecyclerView recyclerView;
    private NewsfeedAdapter adapter;
    private List<News> mPinnedList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pinned);

        initializeViews();
        setListeners();

        mPinnedList = ARKDatabase.getInstance(this).newsDao().getAllPinned();

        adapter = new NewsfeedAdapter(this, mPinnedList);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(linearLayoutManager);
        recyclerView.setAdapter(adapter);

    }

    void initializeViews() {
        toolbar = findViewById(R.id.pinned_toolbar);
        recyclerView = findViewById(R.id.pinned_rv);
    }

    void setListeners() {
        toolbar.setNavigationOnClickListener(v -> finish());
        toolbar.setOnMenuItemClickListener(menuItem -> {
            if (menuItem.getItemId() == R.id.pinned_menu_sort) {
                Dialog dialog;
                dialog = new Dialog(this);
                dialog.setContentView(R.layout.layout_dialog_sort);
                dialog.show();
            }
            return false;
        });
    }

    public void onRadioButtonClicked(View view) {
        boolean checked = ((RadioButton) view).isChecked();
        if (checked) {
            switch (view.getId()) {
                case R.id.sort_ascend_alpha:
                    Collections.sort(mPinnedList, (o1, o2) -> o1.getTitle().compareToIgnoreCase(o2.getTitle()));
                    break;
                case R.id.sort_descend_alpha:
                    Collections.sort(mPinnedList, (o1, o2) -> o2.getTitle().compareToIgnoreCase(o1.getTitle()));
                    break;
                case R.id.sort_ascend_time:
                    Collections.sort(mPinnedList, (o1, o2) -> o1.getPublished().compareTo(o2.getPublished()));
                    break;
                case R.id.sort_descend_time:
                    Collections.sort(mPinnedList, (o1, o2) -> o2.getPublished().compareTo(o1.getPublished()));
                    break;
            }
            adapter.notifyDataSetChanged();
        }
    }
}
