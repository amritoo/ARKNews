package com.example.arknews.ui.news_article;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.widget.ImageView;

import androidx.appcompat.app.AppCompatActivity;

import com.example.arknews.R;
import com.example.arknews.dao.ARKDatabase;
import com.example.arknews.model.History;
import com.example.arknews.model.News;
import com.example.arknews.utility.Constants;
import com.example.arknews.utility.Methods;
import com.google.android.material.appbar.MaterialToolbar;
import com.google.android.material.textview.MaterialTextView;
import com.squareup.picasso.Picasso;

import java.util.List;

public class ArticleActivity extends AppCompatActivity {

    MaterialTextView channelMaterialTextView, categoryMaterialTextView, titleMaterialTextView, authorMaterialTextView, publishedMaterialTextView, updateMaterialTextView, contentsMaterialTextView;
    ImageView articleImageView;
    MaterialToolbar toolbar;

    List<News> newsList;

    private News mNews;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_article);

        initializeViews();
        setListeners();

//        int id = getIntent().getIntExtra(Constants.NEWSID, 0);
        String url = getIntent().getStringExtra(Constants.NEWSID);
        //add condition, if id = 0 show error message
        System.out.println(url);
        mNews = ARKDatabase.getInstance(this).newsDao().getByUrl(url);

        setData();

        // add to history
        History history = new History(
                mNews.getChannelId(), mNews.getCategoryId(),
                mNews.getTitle(), mNews.getAuthor(),
                mNews.getPublished(), mNews.getUrl(),
                mNews.getUrlToImage(), mNews.getId());
        ARKDatabase.getInstance(this).historyDao().insert(history);
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
                    String url = mNews.getUrl();
                    Intent shareIntent = new Intent();
                    shareIntent.setAction(Intent.ACTION_SEND);
                    shareIntent.setType("text/plain");
                    shareIntent.putExtra(Intent.EXTRA_TEXT, "Check out this news! Send from ArkNews App\n" + Uri.parse(url));
                    startActivity(Intent.createChooser(shareIntent, "Share with"));
                    return true;

                case R.id.article_menu_pin:
                    updatePinned();

                case R.id.article_menu_category:
                case R.id.article_menu_unfavorite:
                    break;
                case R.id.article_menu_copy_link:

                    // TODO Share image work

            }
            return false;
        });

    }

    void setData() {
        String channelName = ARKDatabase.getInstance(this).channelDao().getChannelName(mNews.getChannelId());
        channelMaterialTextView.setText(channelName);

        String categoryName = ARKDatabase.getInstance(this).categoryDao().getCategoryNameById(mNews.getCategoryId());
        categoryMaterialTextView.setText(categoryName);

        Picasso.get()
                .load(mNews.getUrlToImage())
                .placeholder(R.drawable.ic_twotone_image_128)
                .into(articleImageView);

        titleMaterialTextView.setText(mNews.getTitle());
        authorMaterialTextView.setText(mNews.getAuthor());
        publishedMaterialTextView.setText(Methods.convertDateToString(mNews.getPublished()));
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


