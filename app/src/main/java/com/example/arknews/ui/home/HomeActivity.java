package com.example.arknews.ui.home;

import android.app.Dialog;
import android.app.SearchManager;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.View;
import android.widget.RadioButton;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SearchView;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.example.arknews.R;
import com.example.arknews.client.NewsApiD;
import com.example.arknews.dao.ARKDatabase;
import com.example.arknews.model.Channel;
import com.example.arknews.model.News;
import com.example.arknews.ui.favourite.FavoriteChannelsActivity;
import com.example.arknews.ui.help.AboutActivity;
import com.example.arknews.ui.news_article.HistoryActivity;
import com.example.arknews.ui.pinned.PinnedActivity;
import com.example.arknews.ui.settings.SettingsActivity;
import com.google.android.material.appbar.MaterialToolbar;
import com.google.android.material.button.MaterialButton;
import com.google.android.material.floatingactionbutton.ExtendedFloatingActionButton;
import com.google.android.material.navigation.NavigationView;

import java.util.Collections;
import java.util.List;

public class HomeActivity extends AppCompatActivity {

    DrawerLayout drawerLayout;
    MaterialToolbar toolbar;
    NavigationView navigationView;
    RecyclerView mRecyclerView;
    ExtendedFloatingActionButton floatingActionButton;
    SwipeRefreshLayout swipeRefreshLayout;

    Context context;
    Dialog sortDialog;

    List<News> mNewsList;
    NewsfeedAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        initializeViews();
        setListeners();
        implementSearch(toolbar.getMenu());

        context = this;

        mNewsList = ARKDatabase.getInstance(this).newsDao().getAll();
        adapter = new NewsfeedAdapter(this, mNewsList);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        mRecyclerView.setLayoutManager(linearLayoutManager);
        mRecyclerView.setAdapter(adapter);

        refresh();
    }

    private void initializeViews() {
        drawerLayout = findViewById(R.id.drawer_layout);
        toolbar = findViewById(R.id.home_toolbar);
        navigationView = findViewById(R.id.nav_view);
        mRecyclerView = findViewById(R.id.newsfeed_rv);
        floatingActionButton = findViewById(R.id.home_extended_fab);
        swipeRefreshLayout = findViewById(R.id.swipeRefreshLayout);
    }

    // sets listeners to views
    private void setListeners() {

        swipeRefreshLayout.setOnRefreshListener(() -> {
            refresh();
            swipeRefreshLayout.setRefreshing(false);
        });

        toolbar.setNavigationOnClickListener(v -> drawerLayout.openDrawer(GravityCompat.START));

        toolbar.setOnMenuItemClickListener(menuItem -> {
            switch (menuItem.getItemId()) {
                case R.id.home_menu_sort:
                    sortDialog = new Dialog(this);
                    sortDialog.setContentView(R.layout.layout_dialog_sort);
                    sortDialog.show();
                    return true;

                case R.id.home_menu_search:

                case R.id.home_menu_filter:

                    break;
            }
            return false;
        });

        floatingActionButton.setOnClickListener(view -> {
            mRecyclerView.smoothScrollToPosition(0);
            floatingActionButton.setVisibility(View.INVISIBLE);
            // TODO complete + also make visible when recycle view is scrolled
        });

        floatingActionButton.setVisibility(View.GONE);
        mRecyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {

            @Override
            public void onScrolled(@NonNull RecyclerView recyclerView, int dx, int dy) {
                //dy is the change in the vertical scroll position
                if (dy > 0) {
                    //scroll down
                    floatingActionButton.setVisibility(View.VISIBLE);
                } else if (dy < 0) {
                    //scroll up
                    floatingActionButton.setVisibility(View.GONE);
                }
            }
        });


        navigationView.setNavigationItemSelectedListener(item -> {
            Intent intent = null;
            switch (item.getItemId()) {
                case R.id.menu_pinned:
                    intent = new Intent(HomeActivity.this, PinnedActivity.class);
                    break;
                case R.id.menu_favorite:
                    intent = new Intent(HomeActivity.this, FavoriteChannelsActivity.class);
                    break;
                case R.id.menu_history:
                    intent = new Intent(HomeActivity.this, HistoryActivity.class);
                    break;
                case R.id.menu_settings:
                    intent = new Intent(HomeActivity.this, SettingsActivity.class);
                    break;
                case R.id.menu_contact_us:
                    intent = new Intent(Intent.ACTION_SEND);
                    String[] recipients = {"newsArkinfo@gmail.com"};
                    intent.putExtra(Intent.EXTRA_EMAIL, recipients);
                    intent.putExtra(Intent.EXTRA_SUBJECT, "");
                    intent.putExtra(Intent.EXTRA_TEXT, "");
                    intent.putExtra(Intent.EXTRA_CC, "newsArkinfo@gmail.com");
                    intent.setType("text/html");
                    intent.setPackage("com.google.android.gm");
                    startActivity(Intent.createChooser(intent, "Send mail"));
                    return true;
                case R.id.menu_about:
                    intent = new Intent(HomeActivity.this, AboutActivity.class);
                    break;
                case R.id.menu_rating:
                    Dialog dialog;
                    dialog = new Dialog(context);
                    dialog.setContentView(R.layout.layout_rate_us);
                    dialog.show();
                    MaterialButton rateButton = dialog.findViewById(R.id.submit_rate_mb);
                    rateButton.setOnClickListener(view12 -> {
                        Toast.makeText(HomeActivity.this, "Thank you for giving us feedback.", Toast.LENGTH_SHORT).show();
                        dialog.dismiss();
                    });
                    return true;

            }
            startActivity(intent);
            return true;
        });
    }


    private void refresh() {
        mNewsList.clear();
        List<Channel> channels = ARKDatabase.getInstance(this).channelDao().getAllSelected();

        for (Channel channel : channels) {
            new NewsApiD(this).getChannelNews(channel.getApiId());
        }
        mNewsList.addAll(ARKDatabase.getInstance(this).newsDao().getAll());
        adapter.notifyItemChanged(0);
    }

    public void onRadioButtonClicked(View view) {
        boolean checked = ((RadioButton) view).isChecked();
        sortDialog.hide();
        switch (view.getId()) {
            case R.id.sort_ascend_alpha:
                if (checked) {
                    Collections.sort(mNewsList, (o1, o2) -> o1.getTitle().compareToIgnoreCase(o2.getTitle()));
                }
                break;
            case R.id.sort_descend_alpha:
                if (checked) {
                    Collections.sort(mNewsList, (o1, o2) -> o2.getTitle().compareToIgnoreCase(o1.getTitle()));
                }
                break;
            case R.id.sort_ascend_time:
                if (checked) {
                    Collections.sort(mNewsList, (o1, o2) -> o1.getPublished().compareTo(o2.getPublished()));
                }
                break;
            case R.id.sort_descend_time:
                if (checked) {
                    Collections.sort(mNewsList, (o1, o2) -> o2.getPublished().compareTo(o1.getPublished()));
                }
                break;
        }
        adapter.notifyDataSetChanged();
    }

    void implementSearch(Menu menu) {
        SearchManager searchManager = (SearchManager) getSystemService(Context.SEARCH_SERVICE);
        final SearchView searchView = (SearchView) menu.findItem(R.id.home_menu_search).getActionView();

        searchView.setSearchableInfo(searchManager.getSearchableInfo(getComponentName()));
        searchView.setQueryHint("Search News...");
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                List<News> news = ARKDatabase.getInstance(context).newsDao().getBySpecificQuery("%" + query + "%");
                List<Integer> channelIds = ARKDatabase.getInstance(context).channelDao().getAllSelectedId();
                mNewsList.clear();
                System.out.println(news.size());
                for (News nNews : news) {
                    System.out.println(nNews.getTitle());
                    if (channelIds.contains(nNews.getChannelId())) {
                        mNewsList.add(nNews);
                    }
                }
                adapter.notifyDataSetChanged();
                Toast.makeText(context, "Search completed", Toast.LENGTH_SHORT).show();
                return true;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                return false;
            }
        });
    }

}