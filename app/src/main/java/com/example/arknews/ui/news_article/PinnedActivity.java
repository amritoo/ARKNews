package com.example.arknews.ui.news_article;

import android.content.res.AssetManager;
import android.graphics.Typeface;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.cardview.widget.CardView;

import com.example.arknews.R;
import com.google.android.material.appbar.AppBarLayout;

public class PinnedActivity extends AppCompatActivity {

    private Typeface montserrat_regular;
    private Typeface montserrat_semiBold;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pinned);
//
//        /*
//         ** Custom Toolbar ( App Bar )
//         **/
//        createToolbar();
//
//        /*
//         ** Asset manager
//         **/
//        assetManager();
//
//        createInfoTextView();
//        createLibraryCardViews();

    }

//    private void assetManager() {
//        AssetManager assetManager = this.getApplicationContext().getAssets();
//        montserrat_regular = Typeface.createFromAsset(assetManager, "fonts/Montserrat-Regular.ttf");
//        montserrat_semiBold = Typeface.createFromAsset(assetManager, "fonts/Montserrat-SemiBold.ttf");
//    }
//
//    private void createInfoTextView() {
//        TextView aboutHeaderAppName = findViewById(R.id.about_header_app_name);
//        // TODO
////        TextView aboutHeaderAppDescription = findViewById(R.id.about_header_app_description);
//        TextView aboutHeaderAppDescription = findViewById(R.id.about_header_app_name);
//        TextView cardInfo = findViewById(R.id.tv_card_info);
//        TextView poweredBy = findViewById(R.id.tv_powered_by);
//        TextView librariesUsed = findViewById(R.id.tv_libraries_used);
//        TextView info1 = findViewById(R.id.tv_info1);
//        TextView author1 = findViewById(R.id.tv_author1);
//        TextView info2 = findViewById(R.id.tv_info2);
//        TextView author2 = findViewById(R.id.tv_author2);
//        TextView info3 = findViewById(R.id.tv_info3);
//        TextView author3 = findViewById(R.id.tv_author3);
//        TextView info4 = findViewById(R.id.tv_info4);
//        TextView author4 = findViewById(R.id.tv_author4);
//
//        aboutHeaderAppName.setTypeface(montserrat_semiBold);
//        aboutHeaderAppDescription.setTypeface(montserrat_regular);
//        cardInfo.setTypeface(montserrat_regular);
//        poweredBy.setTypeface(montserrat_regular);
//        librariesUsed.setTypeface(montserrat_regular);
//        info1.setTypeface(montserrat_semiBold);
//        author1.setTypeface(montserrat_regular);
//        info2.setTypeface(montserrat_semiBold);
//        author2.setTypeface(montserrat_regular);
//        info3.setTypeface(montserrat_semiBold);
//        author3.setTypeface(montserrat_regular);
//        info4.setTypeface(montserrat_semiBold);
//        author4.setTypeface(montserrat_regular);
//    }
//
//    private void createLibraryCardViews() {
//        CardView cardViewInfo = findViewById(R.id.card_info);
//        CardView cardViewLibrary1 = findViewById(R.id.cardView1);
//        CardView cardViewLibrary2 = findViewById(R.id.cardView2);
//        CardView cardViewLibrary3 = findViewById(R.id.cardView3);
//        CardView cardViewLibrary4 = findViewById(R.id.cardView4);
//
//
//        cardViewInfo.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                // code here
//            }
//        });
//
//        cardViewLibrary1.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                //code here
//            }
//        });
//
//        cardViewLibrary2.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                //code here
//            }
//        });
//
//        cardViewLibrary3.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                //code here
//            }
//        });
//
//        cardViewLibrary4.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                //code here
//            }
//        });
//    }
//
//
//    private void createToolbar() {
////        final Toolbar toolbar = findViewById(R.id.toolbar_layout_about);
//        final Toolbar toolbar = findViewById(R.id.deletion_toolbar);
//        setSupportActionBar(toolbar);
//        if (getSupportActionBar() != null) {
//            getSupportActionBar().setDisplayShowTitleEnabled(false);
//        }
//        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
//
////        final TextView toolbarTitle = findViewById(R.id.toolbar_title_about);
//        final TextView toolbarTitle = findViewById(R.id.home_toolbar);
//
//        /*
//         ** Customising animations of the AppBar Layout
//         **/
////        AppBarLayout appBarLayout = findViewById(R.id.app_bar_about);
//        AppBarLayout appBarLayout = findViewById(R.id.about_header_app_name);
//        appBarLayout.addOnOffsetChangedListener(new AppBarLayout.OnOffsetChangedListener() {
//            boolean isShow = false;
//            int scrollRange = -1;
//
//            @Override
//            public void onOffsetChanged(AppBarLayout appBarLayout, int verticalOffset) {
//                if (scrollRange == -1) {
//                    scrollRange = appBarLayout.getTotalScrollRange();
//                }
//                if (scrollRange + verticalOffset == 0) {
//                    toolbarTitle.setVisibility(View.VISIBLE);
//                    toolbarTitle.setTypeface(montserrat_regular);
//                    toolbarTitle.setText("Pinned");
//                    isShow = true;
//                } else if (isShow) {
//                    toolbarTitle.setVisibility(View.GONE);
//                    isShow = false;
//                }
//            }
//        });
//    }
//
//    public boolean onOptionsItemSelected(MenuItem item) {
//
//        // Code here
//
//        return super.onOptionsItemSelected(item);
//    }
//
//
//    @Override
//    public void onBackPressed() {
//        super.onBackPressed();
//        overridePendingTransition(R.anim.pull_in_left, R.anim.push_out_right);
//    }


}
