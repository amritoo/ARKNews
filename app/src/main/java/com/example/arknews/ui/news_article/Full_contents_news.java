package com.example.arknews.ui.news_article;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.example.arknews.R;
import com.google.android.material.snackbar.Snackbar;

import java.net.MalformedURLException;
import java.net.URL;

public class Full_contents_news extends AppCompatActivity {
    private String mNewsUrl = null;
    private URL mUrlForIntent;

    @SuppressLint("QueryPermissionsNeeded")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_full_contents_news);

        Bundle bundle = getIntent().getExtras();
        String setTitleOfActivity = bundle.getString("HEADLINES");
        if (setTitleOfActivity != null) {
            setTitle(setTitleOfActivity.substring(0, 15) + "...");
        }


        TextView headlineTextView = findViewById(R.id.headlineOnTop);
        TextView mainNewsTextView = findViewById(R.id.mainNewsText);
        ImageView ig = findViewById(R.id.thumbnailImageOnTop);
        // Button moreContent = findViewById(R.id.moreContentButton);
        ImageButton share = findViewById(R.id.shareNewsFAB);


        if (bundle.getString("MAIN_CONTENT") != null) {
            mNewsUrl = bundle.getString("NEWS_URL");
            mainNewsTextView.setText(bundle.getString("MAIN_CONTENT"));
            headlineTextView.setText(bundle.getString("HEADLINES"));
            //SET THE IMAGE
            byte[] byteArray = bundle.getByteArray("THUMBNAIL");
            Bitmap bmap = BitmapFactory.decodeByteArray(byteArray, 0, byteArray.length);
            ig.setImageBitmap(bmap);
        }


        if (mNewsUrl != null) {
            try {
                share.setVisibility(View.VISIBLE);
                mUrlForIntent = new URL(mNewsUrl);
            } catch (MalformedURLException e) {
                e.printStackTrace();
            }
        } else {
            share.setVisibility(View.GONE);
        }

        share.setOnClickListener(view -> {
            Snackbar.make(findViewById(android.R.id.content), mUrlForIntent.toString(), Snackbar.LENGTH_SHORT).show();
            Intent i = new Intent();
            i.setAction(Intent.ACTION_SEND);
            i.putExtra(Intent.EXTRA_TEXT, mUrlForIntent.toString());
            i.setType("text/plain");

            String title = "Share";
            Intent chooser = Intent.createChooser(i, title);
            if (i.resolveActivity(getPackageManager()) != null) {
                startActivity(chooser);
            }
        });


    }

    @Override
    public void onBackPressed() {
        finish();
    }

}


