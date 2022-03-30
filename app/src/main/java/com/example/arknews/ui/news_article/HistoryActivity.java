package com.example.arknews.ui.news_article;

import android.app.SearchManager;
import android.content.Context;
import android.os.Bundle;
import android.view.Menu;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SearchView;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.arknews.R;
import com.example.arknews.dao.ARKDatabase;
import com.example.arknews.model.History;
import com.example.arknews.utility.Preferences;
import com.google.android.material.appbar.MaterialToolbar;

import java.util.List;

public class HistoryActivity extends AppCompatActivity {

    MaterialToolbar toolbar;
    TextView title;
    TextView publish_time;
    ImageView news_image;
    RecyclerView recyclerView;
    public static final String HISTORY_PREF = "history_pause";

    List<History> historyList;
    HistoryListAdapter adapter;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_history);

        initializeViews();
        setListeners();
        implementHistorySearch(toolbar.getMenu());

        historyList = ARKDatabase.getInstance(getApplicationContext()).historyDao().getAll();
        adapter = new HistoryListAdapter(this, historyList);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(linearLayoutManager);
        recyclerView.setAdapter(adapter);

        // swipe to delete
        ItemTouchHelper.SimpleCallback simpleItemTouchCallback = new ItemTouchHelper.SimpleCallback(0, ItemTouchHelper.LEFT) {

            @Override
            public boolean onMove(RecyclerView recyclerView, RecyclerView.ViewHolder viewHolder, RecyclerView.ViewHolder target) {
                return false;
            }

            @Override
            public void onSwiped(RecyclerView.ViewHolder viewHolder, int swipeDir) {
                int position = viewHolder.getAdapterPosition();
                deleteItem(position);
                adapter.notifyDataSetChanged();
            }
        };
        ItemTouchHelper itemTouchHelper = new ItemTouchHelper(simpleItemTouchCallback);
        itemTouchHelper.attachToRecyclerView(recyclerView);
        // ends here
    }

    private void deleteItem(int position) {
        History hist = historyList.get(position);
        ARKDatabase.getInstance(this).historyDao().delete(hist);
        historyList.remove(position);
    }

    void initializeViews() {
        toolbar = findViewById(R.id.history_toolbar);
        title = findViewById(R.id.history_news_title);
        publish_time = findViewById(R.id.history_news_published);
        news_image = findViewById(R.id.history_news_image);
        recyclerView = findViewById(R.id.rv_history);
        if (Preferences.getInstance(this).read(HISTORY_PREF, false)) {
            toolbar.getMenu().findItem(R.id.history_pause).setTitle(getString(R.string.resume));
        }
    }

    void setListeners() {
        toolbar.setNavigationOnClickListener(v -> finish());
        toolbar.setOnMenuItemClickListener(item -> {
            switch (item.getItemId()) {
                case R.id.history_pause:
                    if (item.getTitle().equals(getString(R.string.pause))) {
                        item.setTitle(getString(R.string.resume));
                        Preferences.getInstance(this).write(HISTORY_PREF, true);
                        Toast.makeText(this, "History is paused", Toast.LENGTH_SHORT).show();
                    } else {
                        item.setTitle(getString(R.string.pause));
                        Preferences.getInstance(this).write(HISTORY_PREF, false);
                        Toast.makeText(this, "Resumed history", Toast.LENGTH_SHORT).show();
                    }
                    return true;
                case R.id.history_delete:
                    new AlertDialog.Builder(this)
                            .setTitle("Delete All Histories")
                            .setMessage("Are you sure you want to delete all your reading history?\nOnce deleted, you can't recover them")
                            .setPositiveButton(android.R.string.yes, (dialog, which) -> {
                                for (int i = historyList.size() - 1; i >= 0; i--) {
                                    deleteItem(i);
                                }
                                adapter.notifyDataSetChanged();
                                Toast.makeText(this, "Deleted all histories", Toast.LENGTH_SHORT).show();
                            })
                            .setNegativeButton(android.R.string.no, null)
                            .setIcon(android.R.drawable.ic_dialog_alert)
                            .show();
                    return true;
                default:
                    return super.onOptionsItemSelected(item);
            }
        });
    }

    void implementHistorySearch(Menu menu) {
        SearchManager searchManager = (SearchManager) getSystemService(Context.SEARCH_SERVICE);
        final SearchView searchViewHistory = (SearchView) menu.findItem(R.id.history_menu_search).getActionView();

        searchViewHistory.setSearchableInfo(searchManager.getSearchableInfo(getComponentName()));
        searchViewHistory.setQueryHint("Search history...");
        searchViewHistory.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                System.out.println("ABC");
                List<History> histories = ARKDatabase.getInstance(getApplicationContext()).historyDao().getBySpecificQueryHist("%" + query + "%");
                List<Integer> channelIds = ARKDatabase.getInstance(getApplicationContext()).channelDao().getAllSelectedId();
                historyList.clear();
                System.out.println(histories.size());
                for (History history : histories) {
                    System.out.println(history.getTitle());
                    if (channelIds.contains(history.getChannelId())) {
                        historyList.add(history);
                    }
                }
                adapter.notifyDataSetChanged();
                Toast.makeText(getApplicationContext(), "Search completed", Toast.LENGTH_SHORT).show();
                return true;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                return false;
            }
        });
    }

}