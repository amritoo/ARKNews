package com.example.arknews.ui.news_article;

import android.os.Bundle;
import android.widget.ImageView;

import androidx.appcompat.app.AppCompatActivity;

import com.example.arknews.R;
import com.example.arknews.dao.ARKDatabase;
import com.example.arknews.model.News;
import com.example.arknews.utility.Constants;
import com.google.android.material.appbar.MaterialToolbar;
import com.google.android.material.textview.MaterialTextView;
import com.squareup.picasso.Picasso;

public class ArticleActivity extends AppCompatActivity {

    MaterialTextView channelMaterialTextView, categoryMaterialTextView, titleMaterialTextView, authorMaterialTextView, publishedMaterialTextView, updateMaterialTextView, contentsMaterialTextView;
    ImageView articleImageView;
    MaterialToolbar toolbar;

    private News mNews;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_article);

        initializeViews();
        setListeners();

        int id = getIntent().getIntExtra(Constants.NEWSID, 0);
        //add condition, if id = 0 show error message
        mNews = ARKDatabase.getInstance(this).newsDao().getById(id);

        setData();
    }

    void initializeViews() {
        titleMaterialTextView = findViewById(R.id.article_headline_tv);
        channelMaterialTextView = findViewById(R.id.article_channel_tv);
        categoryMaterialTextView = findViewById(R.id.article_category_tv);
        authorMaterialTextView = findViewById(R.id.article_author_tv);
        publishedMaterialTextView = findViewById(R.id.article_published_tv);
        updateMaterialTextView = findViewById(R.id.article_update_tv);
        titleMaterialTextView = findViewById(R.id.article_headline_tv);
        contentsMaterialTextView = findViewById(R.id.article_contents_tv);
        articleImageView = findViewById(R.id.article_image_iv);
        toolbar = findViewById(R.id.article_toolbar);
    }

    void setListeners() {
        toolbar.setNavigationOnClickListener(v -> finish());
        toolbar.setOnMenuItemClickListener(item -> {
            switch (item.getItemId()) {
                case R.id.article_menu_share:
                    return true;
                case R.id.article_menu_pin:
                    updatePinned();
                case R.id.article_menu_category:
                case R.id.article_menu_unfavorite:
                    break;
                case R.id.article_menu_copy_link:
                    int i;
                    return true;
            }
            return false;
        });

    }

    void setData() {
        String channelName = ARKDatabase.getInstance(this).channelDao().getChannelName(mNews.getChannelId());
        channelMaterialTextView.setText(channelName);

        String categoryName = ARKDatabase.getInstance(this).categoryDao().getCategoryNameById(mNews.getCategoryId());
        channelMaterialTextView.setText(categoryName);

        Picasso.get()
                .load(mNews.getUrlToImage())
                .placeholder(R.drawable.ic_twotone_image_128)
                .into(articleImageView);

        channelMaterialTextView.setText(mNews.getTitle());
        authorMaterialTextView.setText(mNews.getAuthor());
        publishedMaterialTextView.setText(mNews.getPublished().toString());
//        updateMaterialTextView.setText(mNews.getUpdated().toString());
        contentsMaterialTextView.setText(mNews.getContent());
    }

    void updatePinned() {
        if (mNews.getPinned()) {
            mNews.setPinned(false);
//            pinnedImageView.setImageResource(R.drawable.ic_baseline_star_border_24);
        } else {
            mNews.setPinned(true);
//            pinnedImageView.setImageResource(R.drawable.ic_baseline_star_24);
        }
        // update pinned to database
        ARKDatabase.getInstance(this).newsDao().update(mNews);
    }
}


