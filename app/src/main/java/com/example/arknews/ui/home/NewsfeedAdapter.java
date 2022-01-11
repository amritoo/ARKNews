package com.example.arknews.ui.home;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.appcompat.widget.PopupMenu;
import androidx.recyclerview.widget.RecyclerView;

import com.example.arknews.R;
import com.example.arknews.dao.ARKDatabase;
import com.example.arknews.model.News;
import com.example.arknews.ui.news_article.ArticleActivity;
import com.example.arknews.utility.Constants;
import com.google.android.material.textview.MaterialTextView;
import com.squareup.picasso.Picasso;

import java.util.List;

public class NewsfeedAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    Context context;
    List<News> newsList;

    public NewsfeedAdapter(Context context, List<News> newsList) {
        this.context = context;
        this.newsList = newsList;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new NewsfeedViewHolder(LayoutInflater.from(parent.getContext())
                .inflate(R.layout.news_card, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        News news = newsList.get(position);
        String urlToChannelImage = ARKDatabase.getInstance(context).channelDao().getChannelImageUrl(news.getChannelId());
        news.setUrlToChannelImage(urlToChannelImage);
        ((NewsfeedViewHolder) holder).bind(news);
        holder.itemView.setOnClickListener(view -> {
            // start article activity
            Intent intent = new Intent(context, ArticleActivity.class);
            intent.putExtra(Constants.NEWSID, news.getId());
            context.startActivity(intent);
        });
    }

    @Override
    public int getItemCount() {
        return newsList.size();
    }

    public static class NewsfeedViewHolder extends RecyclerView.ViewHolder {

        ImageView newsImageView, channelImageView, pinnedImageView, shareImageView, menuImageView;
        MaterialTextView titleTextView, publishedTextView;

        public NewsfeedViewHolder(@NonNull View itemView) {
            super(itemView);

            // Initialize
            newsImageView = itemView.findViewById(R.id.news_card_image_iv);
            channelImageView = itemView.findViewById(R.id.news_card_channel_iv);
            titleTextView = itemView.findViewById(R.id.news_card_title_tv);
            publishedTextView = itemView.findViewById(R.id.news_card_published_tv);
            pinnedImageView = itemView.findViewById(R.id.news_card_pinned_iv);
            shareImageView = itemView.findViewById(R.id.news_card_share_iv);
            menuImageView = itemView.findViewById(R.id.news_card_menu_iv);
        }

        void bind(News news) {
            titleTextView.setText(news.getTitle());
            publishedTextView.setText(news.getPublished().toString());
            Picasso.get()
                    .load(news.getUrlToImage())
                    .placeholder(R.drawable.ic_twotone_image_128)
                    .into(newsImageView);
            Picasso.get()
                    .load(news.getUrlToChannelImage())
                    .placeholder(R.drawable.ic_baseline_source_24)
                    .into(channelImageView);
            if (news.getPinned())
                pinnedImageView.setImageResource(R.drawable.ic_baseline_star_24);

            setListeners(news);
        }

        void setListeners(News news) {
            pinnedImageView.setOnClickListener(view -> updatePinned(news, view.getContext()));

            shareImageView.setOnClickListener(view -> {
                // TODO Share image work
            });

            menuImageView.setOnClickListener(view -> {
                PopupMenu popupMenu = new PopupMenu(view.getContext(), view);
                popupMenu.setOnMenuItemClickListener(item -> {
                    //  TODO menu options
                    switch (item.getItemId()) {
                        case R.id.news_card_menu_open:
                        case R.id.news_card_menu_hide:
                        case R.id.news_card_menu_category:
                        case R.id.news_card_menu_unfavorite:
                        case R.id.news_card_menu_copy_link:
                            return true;
                        case R.id.news_card_menu_pin:
                            updatePinned(news, view.getContext());
                            return true;
                    }
                    return false;
                });
                popupMenu.inflate(R.menu.news_card_menu);
                popupMenu.show();
            });
        }

        void updatePinned(News news, Context context) {
            if (news.getPinned()) {
                news.setPinned(false);
                pinnedImageView.setImageResource(R.drawable.ic_baseline_star_border_24);
            } else {
                news.setPinned(true);
                pinnedImageView.setImageResource(R.drawable.ic_baseline_star_24);
            }
            // update pinned to database
            ARKDatabase.getInstance(context).newsDao().update(news);
        }
    }

}
