package com.example.arknews.ui.news_article;

import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.MenuItem;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import com.example.arknews.R;
import com.example.arknews.dao.ARKDatabase;
import com.example.arknews.model.Category;
import com.example.arknews.model.Channel;
import com.example.arknews.model.History;
import com.example.arknews.model.News;
import com.example.arknews.utility.Constants;
import com.example.arknews.utility.Preferences;
import com.google.android.material.appbar.MaterialToolbar;
import com.google.android.material.button.MaterialButton;
import com.google.android.material.textview.MaterialTextView;
import com.squareup.picasso.Picasso;

import java.util.List;

public class ArticleActivity extends AppCompatActivity {

    MaterialTextView channelMaterialTextView, categoryMaterialTextView, titleMaterialTextView, authorMaterialTextView, publishedMaterialTextView, updateMaterialTextView, contentsMaterialTextView;
    ImageView articleImageView;
    MaterialToolbar toolbar;
    MaterialButton readFullNews_mb;

    List<News> newsList;

    private News mNews;
    private Channel mChannel;
    private Category mCategory;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_article);

        initializeViews();
        setListeners();

//        int id = getIntent().getIntExtra(Constants.NEWSID, 0);
        String url = getIntent().getStringExtra(Constants.NEWSID);
        //add condition, if id = 0 show error message

        mNews = ARKDatabase.getInstance(this).newsDao().getByUrl(url);
        mChannel = ARKDatabase.getInstance(this).channelDao().getChannelById(mNews.getChannelId());
        mCategory = ARKDatabase.getInstance(this).categoryDao().getCategoryById(mNews.getCategoryId());

        setData();

        if (!Preferences.getInstance(this).read(HistoryActivity.HISTORY_PREF, false)) {
            // add to history
            History history = new History(
                    mNews.getChannelId(), mNews.getCategoryId(),
                    mNews.getTitle(), mNews.getAuthor(),
                    mNews.getPublished(), mNews.getUrl(),
                    mNews.getUrlToImage(), mNews.getId());
            ARKDatabase.getInstance(this).historyDao().insert(history);
        }
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
        readFullNews_mb = findViewById(R.id.article_view_full_mb);
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
                    return true;
                case R.id.article_menu_category:
                    updateCategory();
                    return true;
                case R.id.article_menu_unfavorite:
                    updateFavorite();
                    return true;
                case R.id.article_menu_copy_link:
                    url = mNews.getUrl();
                    ClipboardManager clipboard = (ClipboardManager) getSystemService(Context.CLIPBOARD_SERVICE);
                    ClipData clip = ClipData.newPlainText("ARK news", url);
                    clipboard.setPrimaryClip(clip);
                    Toast.makeText(getApplicationContext(), "Copied to Clipboard", Toast.LENGTH_SHORT).show();
                    return true;
            }
            return false;
        });

        readFullNews_mb.setOnClickListener(view -> {
            new AlertDialog.Builder(this)
                    .setTitle("Redirect to browser")
                    .setMessage("Are you sure you want to redirect to your default browser to view full News?")
                    .setPositiveButton(android.R.string.yes, (dialog, which) -> {
                        Intent browse = new Intent(Intent.ACTION_VIEW, Uri.parse(mNews.getUrl()));
                        startActivity(browse);
                    })
                    .setNegativeButton(android.R.string.no, null)
                    .setIcon(android.R.drawable.ic_dialog_alert)
                    .show();

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
        publishedMaterialTextView.setText(mNews.getPublished().toString());
//        updateMaterialTextView.setText(mNews.getUpdated().toString());
        contentsMaterialTextView.setText(mNews.getContent());
    }

    void updatePinned() {
        MenuItem item = toolbar.getMenu().findItem(R.id.article_menu_pin);
        if (mNews.getPinned()) {
            mNews.setPinned(false);
            item.setIcon(R.drawable.ic_baseline_star_border_24);
        } else {
            mNews.setPinned(true);
            item.setIcon(R.drawable.ic_baseline_star_24);
        }
        // update pinned to database
        ARKDatabase.getInstance(this).newsDao().update(mNews);
    }

    void updateFavorite() {
        mChannel.setSelected(!mChannel.isSelected());
        ARKDatabase.getInstance(this).channelDao().update(mChannel);
    }

    void updateCategory() {
        mCategory.setSelected(!mCategory.isSelected());
        ARKDatabase.getInstance(this).categoryDao().update(mCategory);
    }
}


