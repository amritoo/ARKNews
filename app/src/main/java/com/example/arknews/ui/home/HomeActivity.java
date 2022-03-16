package com.example.arknews.ui.home;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
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

import java.util.List;

public class HomeActivity extends AppCompatActivity {

    public static final int CHANNEL_CODE = 11;

    DrawerLayout drawerLayout;
    MaterialToolbar toolbar;
    NavigationView navigationView;
    RecyclerView mRecyclerView;
    ExtendedFloatingActionButton floatingActionButton;
    SwipeRefreshLayout swipeRefreshLayout;

    Context context;

    List<News> mNewsList;
    NewsfeedAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        initializeViews();
        setListeners();

        context = this;

        mNewsList = ARKDatabase.getInstance(getApplicationContext()).newsDao().getAll();
        adapter = new NewsfeedAdapter(this, mNewsList);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        mRecyclerView.setLayoutManager(linearLayoutManager);
        mRecyclerView.setAdapter(adapter);

        refresh();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        if (requestCode == CHANNEL_CODE && resultCode == RESULT_OK) {
            refresh();
        } else {
            super.onActivityResult(requestCode, resultCode, data);
        }
    }

    private void initializeViews() {
        drawerLayout = findViewById(R.id.drawer_layout);
        toolbar = findViewById(R.id.favorite_toolbar);
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

        floatingActionButton.setOnClickListener(view -> {
            // TODO complete + also make visible when recycle view is scrolled
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

        if (channels.size() == 0) {
            // TODO show dialog to select channels
            Intent intent = new Intent(HomeActivity.this, FavoriteChannelsActivity.class);
            startActivityForResult(intent, CHANNEL_CODE);
            return;
        }

        for (Channel channel : channels) {
            new NewsApiD(this).getChannelNews(channel.getApiId());
        }
        mNewsList.addAll(ARKDatabase.getInstance(this).newsDao().getAll());
        adapter.notifyItemChanged(0);
    }

}