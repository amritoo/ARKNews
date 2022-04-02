package com.example.arknews.ui.favorite_channels;

import static com.example.arknews.R.id;
import static com.example.arknews.R.layout;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.arknews.R;
import com.example.arknews.dao.ARKDatabase;
import com.example.arknews.model.Channel;
import com.google.android.material.appbar.MaterialToolbar;

import java.util.ArrayList;
import java.util.List;

public class FavoriteChannelActivity extends AppCompatActivity {

    private MaterialToolbar toolbar;
    private RecyclerView recyclerView;

    private List<Channel> channelList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(layout.activity_favorite_channels);

        toolbar = findViewById(R.id.favorite_toolbar);
        toolbar.setNavigationOnClickListener(v -> finish());

        loadChannelList();

        recyclerView = findViewById(id.channel_rv);
        FavoriteChannelAdapter adapter = new FavoriteChannelAdapter(channelList, this);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(linearLayoutManager);
        recyclerView.setAdapter(adapter);
    }

    private void loadChannelList() {
        List<Channel> channels = ARKDatabase.getInstance(this).channelDao().getAll();
        channelList = new ArrayList<>();
        for (Channel ch : channels) {
            if (ch.isSelected()) channelList.add(ch);
        }
        for (Channel ch : channels) {
            if (!ch.isSelected()) channelList.add(ch);
        }
    }

}