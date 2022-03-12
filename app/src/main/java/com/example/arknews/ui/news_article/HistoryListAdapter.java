package com.example.arknews.ui.news_article;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.arknews.R;
import com.example.arknews.dao.ARKDatabase;
import com.example.arknews.model.History;
import com.example.arknews.utility.Methods;
import com.google.android.material.textview.MaterialTextView;
import com.squareup.picasso.Picasso;

import java.util.List;

public class HistoryListAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    List<History> historyList;

    public HistoryListAdapter(HistoryActivity historyActivity, List<History> historyList) {
        this.historyList = historyList;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new HistoryViewHolder(LayoutInflater.from(parent.getContext())
                .inflate(R.layout.history_news_card, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        ((HistoryViewHolder) holder).bind(historyList.get(position));
    }

    @Override
    public int getItemCount() {
        return historyList.size();
    }

    public static class HistoryViewHolder extends RecyclerView.ViewHolder {

        MaterialTextView title, channel_name, published_time;
        ImageView news_image;

        public HistoryViewHolder(@NonNull View itemView) {
            super(itemView);
            title = itemView.findViewById(R.id.history_news_title);
            channel_name = itemView.findViewById(R.id.history_channel_name);
            published_time = itemView.findViewById(R.id.history_news_published);
            news_image = itemView.findViewById(R.id.history_news_image);
        }

        void bind(History history) {
            title.setText(history.getTitle());
            String channel = ARKDatabase.getInstance(itemView.getContext()).channelDao().getChannelName(history.getChannelId());
            channel_name.setText(channel);
            published_time.setText(Methods.convertDateToString(history.getPublished()));
            Picasso.get()
                    .load(history.getUrlToImage())
                    .placeholder(R.drawable.ic_twotone_image_128)
                    .into(news_image);
        }
    }

}
