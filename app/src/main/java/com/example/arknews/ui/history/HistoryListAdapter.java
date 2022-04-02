package com.example.arknews.ui.history;

import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.arknews.R;
import com.example.arknews.dao.ARKDatabase;
import com.example.arknews.model.History;
import com.example.arknews.utility.Methods;
import com.google.android.material.textview.MaterialTextView;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

public class HistoryListAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    static boolean isChecked;
    static List<Integer> selectedHistoryIds;

    private List<History> historyList;
    private MenuItem menuItem;

    public HistoryListAdapter(List<History> historyList, MenuItem menuItem) {
        this.historyList = historyList;
        isChecked = false;
        selectedHistoryIds = new ArrayList<>();
        this.menuItem = menuItem;
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

        ((HistoryViewHolder) holder).itemView.setOnLongClickListener(v -> {
            selectedHistoryIds.clear();
            ((HistoryViewHolder) holder).checkBox.setVisibility(View.VISIBLE);
            ((HistoryViewHolder) holder).checkBox.setChecked(true);
            isChecked = true;
            menuItem.setIcon(R.drawable.ic_baseline_delete_24);
            notifyDataSetChanged();
            return true;
        });
    }

    @Override
    public int getItemCount() {
        return historyList.size();
    }

    public static class HistoryViewHolder extends RecyclerView.ViewHolder {

        private MaterialTextView title, channel_name, published_time;
        private ImageView news_image;
        private CheckBox checkBox;

        public HistoryViewHolder(@NonNull View itemView) {
            super(itemView);
            title = itemView.findViewById(R.id.history_news_title);
            channel_name = itemView.findViewById(R.id.history_channel_name);
            published_time = itemView.findViewById(R.id.history_news_published);
            news_image = itemView.findViewById(R.id.history_news_image);
            checkBox = itemView.findViewById(R.id.history_card_checkbox);
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

            checkBox.setOnCheckedChangeListener((buttonView, isChecked1) -> {
                if (isChecked1) {
                    selectedHistoryIds.add(history.getId());
                } else {
                    selectedHistoryIds.remove((Integer) history.getId());
                }
            });

            if (HistoryListAdapter.isChecked) {
                checkBox.setVisibility(View.VISIBLE);
                itemView.setOnClickListener(v -> checkBox.setChecked(!checkBox.isChecked()));
            }
        }
    }

}
