package com.example.arknews.ui.favourite;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.arknews.R;
import com.example.arknews.model.Channel;
import com.google.android.material.textview.MaterialTextView;
import com.squareup.picasso.Picasso;

import java.util.List;

public class ChannelAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    List<Channel> channelList;
    Context context;

    public ChannelAdapter(List<Channel> channelList, Context context) {
        this.channelList = channelList;
        this.context = context;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new ChannelAdapter.ChannelViewHolder(LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_channel, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        Channel channel = channelList.get(position);
        ((ChannelAdapter.ChannelViewHolder) holder).bind(channel);
    }

    @Override
    public int getItemCount() {
        return channelList.size();
    }

    public static class ChannelViewHolder extends RecyclerView.ViewHolder {

        ImageView channelImageView;
        MaterialTextView channelName;
        CheckBox channelCheckbox;

        public ChannelViewHolder(@NonNull View itemView) {
            super(itemView);

            //Initialize
            channelImageView = itemView.findViewById(R.id.channel_image);
            channelName = itemView.findViewById(R.id.channel_name_fav);
            channelCheckbox = itemView.findViewById(R.id.channel_checkbox);
        }

        void bind(Channel channel) {
            channelName.setText(channel.getName());
//            Picasso.get()
//                    .load(channel.getUrl())
//                    .placeholder(R.drawable.ic_baseline_source_24)
//                    .into(channelImageView);
        }

    }

}
