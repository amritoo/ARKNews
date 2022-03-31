package com.example.arknews.ui.home;

import android.app.Dialog;
import android.app.SearchManager;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.view.Menu;
import android.view.View;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SearchView;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.example.arknews.R;
import com.example.arknews.client.NewsApi;
import com.example.arknews.dao.ARKDatabase;
import com.example.arknews.dao.NewsDao;
import com.example.arknews.model.Category;
import com.example.arknews.model.Channel;
import com.example.arknews.model.News;
import com.example.arknews.ui.favourite.FavoriteChannelsActivity;
import com.example.arknews.ui.help.AboutActivity;
import com.example.arknews.ui.news_article.HistoryActivity;
import com.example.arknews.ui.pinned.PinnedActivity;
import com.example.arknews.ui.settings.SettingsActivity;
import com.example.arknews.utility.EditTextDateRangePicker;
import com.google.android.material.appbar.MaterialToolbar;
import com.google.android.material.button.MaterialButton;
import com.google.android.material.chip.Chip;
import com.google.android.material.chip.ChipGroup;
import com.google.android.material.floatingactionbutton.ExtendedFloatingActionButton;
import com.google.android.material.navigation.NavigationView;
import com.google.android.material.textfield.TextInputLayout;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.List;

public class HomeActivity extends AppCompatActivity {

    DrawerLayout drawerLayout;
    MaterialToolbar toolbar;
    NavigationView navigationView;
    RecyclerView mRecyclerView;
    ExtendedFloatingActionButton floatingActionButton;
    SwipeRefreshLayout swipeRefreshLayout;
    ImageView closeFilterIV;
    ChipGroup channelChipGroup, categoryChipGroup;
    TextInputLayout startDateT, endDateT;
    MaterialButton filterResultButton;

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
        loadFilterChips();
        implementSearch(toolbar.getMenu());


        context = this;
        mNewsList = new ArrayList<>();
        defaultSortNewsList();
        adapter = new NewsfeedAdapter(this, mNewsList);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        mRecyclerView.setLayoutManager(linearLayoutManager);
        mRecyclerView.setAdapter(adapter);

        if (mNewsList.isEmpty()) {
            refresh();
        }
    }

    private void initializeViews() {
        drawerLayout = findViewById(R.id.drawer_layout);
        toolbar = findViewById(R.id.home_toolbar);
        navigationView = findViewById(R.id.nav_view);
        mRecyclerView = findViewById(R.id.newsfeed_rv);
        floatingActionButton = findViewById(R.id.home_extended_fab);
        swipeRefreshLayout = findViewById(R.id.swipeRefreshLayout);
        closeFilterIV = findViewById(R.id.filter_close_iv);
        channelChipGroup = findViewById(R.id.filter_channel_cg);
        categoryChipGroup = findViewById(R.id.filter_category_cg);
        startDateT = findViewById(R.id.filter_date_start_tf);
        endDateT = findViewById(R.id.filter_date_end_tf);
        filterResultButton = findViewById(R.id.filter_result_mb);
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
                case R.id.home_menu_filter:
                    findViewById(R.id.home_filter_layout).setVisibility(View.VISIBLE);
                    break;
            }
            return false;
        });

        closeFilterIV.setOnClickListener(view -> findViewById(R.id.home_filter_layout).setVisibility(View.GONE));

        new EditTextDateRangePicker(startDateT.getEditText(), endDateT.getEditText(), getSupportFragmentManager());

        filterResultButton.setOnClickListener(view -> filterNews());

        floatingActionButton.setOnClickListener(view -> {
            mRecyclerView.smoothScrollToPosition(0);
            floatingActionButton.setVisibility(View.GONE);
        });

        mRecyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrolled(@NonNull RecyclerView recyclerView, int dx, int dy) {
                //dy is the change in the vertical scroll position
                if (dy > 50) {
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
                    new AlertDialog.Builder(this)
                            .setTitle("Confirmation")
                            .setMessage("Are you sure you would like to contact us via eMail?")
                            .setPositiveButton(android.R.string.ok, (dialog, which) -> {
                                Intent intent1 = new Intent(Intent.ACTION_SEND);
                                String[] recipients = {"newsArkinfo@gmail.com"};
                                intent1.putExtra(Intent.EXTRA_EMAIL, recipients);
                                intent1.putExtra(Intent.EXTRA_SUBJECT, "");
                                intent1.putExtra(Intent.EXTRA_TEXT, "");
                                intent1.putExtra(Intent.EXTRA_CC, "newsArkinfo@gmail.com");
                                intent1.setType("text/html");
                                intent1.setPackage("com.google.android.gm");
                                startActivity(Intent.createChooser(intent1, "Send mail"));
                            })
                            .setNegativeButton(android.R.string.cancel, null)
                            .setIcon(android.R.drawable.ic_dialog_alert)
                            .show();

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
        List<Channel> channels = ARKDatabase.getInstance(this).channelDao().getAll();
        for (Channel channel : channels) {
            new NewsApi(this).getChannelNews(channel.getApiId());
        }

        defaultSortNewsfeed();
        loadFilterChips();
    }

    private void defaultSortNewsList() {
        // sort by selected category and channels
        List<Integer> selectedChannels = ARKDatabase.getInstance(this).channelDao().getAllSelectedId();
        List<Integer> selectedCategories = ARKDatabase.getInstance(this).categoryDao().getAllSelectedIds();
        List<News> preferredNews = ARKDatabase.getInstance(this).newsDao().getFilteredNewsByChanCat(selectedChannels, selectedCategories);
        mNewsList.addAll(preferredNews);

        List<News> news = ARKDatabase.getInstance(this).newsDao().getAll();
        news.removeAll(preferredNews);
        mNewsList.addAll(news);
    }

    private void defaultSortNewsfeed() {
        mNewsList.clear();
        defaultSortNewsList();
        adapter.notifyDataSetChanged();
    }

    public void onRadioButtonClicked(View view) {
        boolean checked = ((RadioButton) view).isChecked();
        if (checked) {
            switch (view.getId()) {
                case R.id.sort_ascend_alpha:
                    Collections.sort(mNewsList, (o1, o2) -> o1.getTitle().compareToIgnoreCase(o2.getTitle()));
                    break;
                case R.id.sort_descend_alpha:
                    Collections.sort(mNewsList, (o1, o2) -> o2.getTitle().compareToIgnoreCase(o1.getTitle()));
                    break;
                case R.id.sort_ascend_time:
                    Collections.sort(mNewsList, (o1, o2) -> o1.getPublished().compareTo(o2.getPublished()));
                    break;
                case R.id.sort_descend_time:
                    Collections.sort(mNewsList, (o1, o2) -> o2.getPublished().compareTo(o1.getPublished()));
                    break;
            }
        }
        sortDialog.hide();
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
                for (News nNews : news) {
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
        // TODO change neewsfeed after exiting searchview
    }

    void loadFilterChips() {
        List<Channel> channels = ARKDatabase.getInstance(this).channelDao().getAll();
        channelChipGroup.removeAllViews();
        for (int i = 0; i < channels.size(); i++) {
            Channel channel = channels.get(i);
            Chip chip = new Chip(this);
            chip.setId(channel.getId());
            chip.setText(channel.getName());
            chip.setCheckable(true);
            channelChipGroup.addView(chip);
        }

        List<Category> categories = ARKDatabase.getInstance(this).categoryDao().getAll();
        categoryChipGroup.removeAllViews();
        for (int i = 0; i < categories.size(); i++) {
            Category category = categories.get(i);
            Chip chip = new Chip(this);
            chip.setId(category.getId());
            chip.setText(category.getDescription());
            chip.setCheckable(true);
            categoryChipGroup.addView(chip);
        }
    }

    void filterNews() {
        // Get all filter parameters
        List<Integer> filteredChanlIds = channelChipGroup.getCheckedChipIds();
        List<Integer> filteredCatIds = categoryChipGroup.getCheckedChipIds();
        Date startDate = EditTextDateRangePicker.startDate;
        Date endDate = EditTextDateRangePicker.endDate;

        // check if any valid filter parameter is selected
        if (!filteredChanlIds.isEmpty() || !filteredCatIds.isEmpty() || (startDate != null && endDate != null)) {
            mNewsList.clear();
            NewsDao newsDao = ARKDatabase.getInstance(this).newsDao();

            // call appropriate methods from newsDao to get filtered result
            if (filteredChanlIds.isEmpty()) {
                if (filteredCatIds.isEmpty()) {
                    mNewsList.addAll(newsDao.getFilteredNewsByDate(startDate, endDate));
                } else if (startDate == null || endDate == null) {
                    mNewsList.addAll(newsDao.getFilteredNewsByCat(filteredCatIds));
                } else {
                    mNewsList.addAll(newsDao.getFilteredNewsByCatDate(filteredCatIds, startDate, endDate));
                }
            } else {
                if (filteredCatIds.isEmpty()) {
                    if (startDate == null || endDate == null) {
                        mNewsList.addAll(newsDao.getFilteredNewsByChannel(filteredChanlIds));
                    } else {
                        mNewsList.addAll(newsDao.getFilteredNewsByChanDate(filteredChanlIds, startDate, endDate));
                    }
                } else if (startDate == null || endDate == null) {
                    mNewsList.addAll(newsDao.getFilteredNewsByChanCat(filteredChanlIds, filteredCatIds));
                } else {
                    mNewsList.addAll(newsDao.getFilteredNews(filteredChanlIds, filteredCatIds, startDate, endDate));
                }
            }
            adapter.notifyDataSetChanged();
        }

        findViewById(R.id.home_filter_layout).setVisibility(View.GONE);
    }

}