package com.example.arknews.ui.home;

import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.widget.PopupMenu;
import androidx.recyclerview.widget.RecyclerView;

import com.example.arknews.R;
import com.example.arknews.dao.ARKDatabase;
import com.example.arknews.model.News;
import com.example.arknews.ui.news_article.ArticleActivity;
import com.example.arknews.utility.Constants;
import com.example.arknews.utility.Methods;
import com.google.android.material.textview.MaterialTextView;
import com.squareup.picasso.Picasso;

import java.util.List;

public class NewsfeedAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    Context context;
    List<News> newsList;
    private String URL;

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
        ((NewsfeedViewHolder) holder).bind(news, context);
    }

    @Override
    public int getItemCount() {
        return newsList.size();
    }

    public static class NewsfeedViewHolder extends RecyclerView.ViewHolder {

        Context context;
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

        void bind(News news, Context context) {
            this.context = context;
            titleTextView.setText(news.getTitle());
            publishedTextView.setText(Methods.convertDateToString(news.getPublished()));
            Picasso.get()
                    .load(news.getUrlToImage())
                    .placeholder(R.drawable.ic_twotone_image_128)
                    .into(newsImageView);
            loadChannelIcon(ARKDatabase.getInstance(itemView.getContext())
                    .channelDao().getChannelApiId(news.getChannelId()));
            if (news.getPinned())
                pinnedImageView.setImageResource(R.drawable.ic_baseline_star_24);

            setListeners(news);
        }

        void setListeners(News news) {

            itemView.setOnClickListener(view -> openArticle(news));

            pinnedImageView.setOnClickListener(view -> {
                updatePinned(news, view.getContext());
            });

            shareImageView.setOnClickListener(view -> {
                String url = news.getUrl();
                Intent shareIntent = new Intent();
                shareIntent.setAction(Intent.ACTION_SEND);
                shareIntent.setType("text/plain");
                shareIntent.putExtra(Intent.EXTRA_TEXT, "Check out this news! Send from ArkNews App\n" + Uri.parse(url));
                context.startActivity(Intent.createChooser(shareIntent, "Share with"));

            });

            menuImageView.setOnClickListener(view -> {
                PopupMenu popupMenu = new PopupMenu(view.getContext(), view);
                popupMenu.setOnMenuItemClickListener(item -> {
                    //  TODO menu options
                    switch (item.getItemId()) {
                        case R.id.news_card_menu_open:
                            openArticle(news);
                            break;
                        case R.id.news_card_menu_hide:
                        case R.id.news_card_menu_category:
                        case R.id.news_card_menu_unfavorite:
                            break;

                        case R.id.news_card_menu_copy_link:
                            String url = news.getUrl();
                            ClipboardManager clipboard = (ClipboardManager) context.getSystemService(Context.CLIPBOARD_SERVICE);
                            ClipData clip = ClipData.newPlainText("ARK news", url);
                            clipboard.setPrimaryClip(clip);
                            Toast.makeText(context, "Copied to Clipboard", Toast.LENGTH_SHORT).show();
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

        void openArticle(News news) {
            Intent intent = new Intent(context, ArticleActivity.class);
            intent.putExtra(Constants.NEWSID, news.getUrl());
            context.startActivity(intent);
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

        void loadChannelIcon(String channelName) {
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
            channelImageView.setImageResource(id);
        }
    }

}
