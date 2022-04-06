package com.example.arknews.ui.favorite_channels;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.arknews.R;
import com.example.arknews.dao.ARKDatabase;
import com.example.arknews.model.Channel;
import com.google.android.material.textview.MaterialTextView;

import java.util.List;

public class FavoriteChannelAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private List<Channel> channelList;
    private Context context;

    public FavoriteChannelAdapter(List<Channel> channelList, Context context) {
        this.channelList = channelList;
        this.context = context;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new FavoriteChannelAdapter.ChannelViewHolder(LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_channel, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        Channel channel = channelList.get(position);
        ((FavoriteChannelAdapter.ChannelViewHolder) holder).bind(channel);
    }

    @Override
    public int getItemCount() {
        return channelList.size();
    }

    public static class ChannelViewHolder extends RecyclerView.ViewHolder {
        private ImageView imageView;
        private MaterialTextView channelName, category, category2, category3;
        private CheckBox checkBox;

        public ChannelViewHolder(@NonNull View itemView) {
            super(itemView);

            // Initialize
            imageView = itemView.findViewById(R.id.channel_image);
            channelName = itemView.findViewById(R.id.channel_name_fav);
            checkBox = itemView.findViewById(R.id.channel_checkbox);
            category = itemView.findViewById(R.id.channel_cat);
        }

        void bind(Channel channel) {
            channelName.setText(channel.getName());
            loadIcon(channel.getApiId());

            String catName = ARKDatabase.getInstance(itemView.getContext())
                    .categoryDao().getCategoryDescriptionById(channel.getCategoryId());
            category.setText(catName);

            checkBox.setChecked(channel.isSelected());
            checkBox.setOnClickListener(v -> {
                channel.setSelected(checkBox.isChecked());
                ARKDatabase.getInstance(itemView.getContext()).channelDao().update(channel);
            });
        }

        void loadIcon(String channelName) {
            int id = 0;
            switch (channelName) {
                case "abc-news":
                    id = R.drawable.logo_abc_news;
                    break;
                case "associated-press":
                    id = R.drawable.logo_ap;
                    break;
                case "bbc-news":
                    id = R.drawable.logo_bbc_news;
                    break;
                case "bbc-sport":
                    id = R.drawable.logo_bbc_sports;
                    break;
                case "business-insider":
                    id = R.drawable.logo_business_insider;
                    break;
                case "cbc-news":
                    id = R.drawable.logo_cbc;
                    break;
                case "cnn":
                    id = R.drawable.logo_cnn;
                    break;
                case "engadget":
                    id = R.drawable.logo_engadget;
                    break;
                case "espn":
                    id = R.drawable.logo_espn;
                    break;
                case "fox-news":
                    id = R.drawable.logo_fox_news;
                    break;
                case "fox-sports":
                    id = R.drawable.logo_fox_sports;
                    break;
                case "google-news":
                    id = R.drawable.logo_google_news;
                    break;
                case "ign":
                    id = R.drawable.logo_ign;
                    break;
                case "independent":
                    id = R.drawable.logo_independent;
                    break;
                case "national-geographic":
                    id = R.drawable.logo_nat_geo;
                    break;
                case "reuters":
                    id = R.drawable.logo_reuters;
                    break;
                case "the-washington-times":
                    id = R.drawable.logo_wtimes;
                    break;
            }
            imageView.setImageResource(id);
        }

    }

}
