package com.example.arknews.ui.settings;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;

import com.example.arknews.R;
import com.example.arknews.ui.categories.CategorySelectionActivity;
import com.example.arknews.ui.help.UserManualActivity;
import com.google.android.material.appbar.MaterialToolbar;
import com.google.android.material.button.MaterialButton;

public class SettingsActivity extends AppCompatActivity {

    private MaterialButton theme, notify, language, auto_del, category, help, manual;
    Context context;
    MaterialToolbar toolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);

        context= this;
        initializeViews();
        setListeners();
    }

    void initializeViews() {
        toolbar = findViewById(R.id.settings_toolbar);
        theme = findViewById(R.id.theme);
        notify = findViewById(R.id.notify);
        language = findViewById(R.id.language);
        auto_del = findViewById(R.id.auto_deletion);
        category = findViewById(R.id.category);
        help = findViewById(R.id.help);
        manual = findViewById(R.id.user_manual);
    }

    void setListeners() {
        toolbar.setNavigationOnClickListener(v -> finish());

        //Appearance
        theme.setOnClickListener(view -> {
            Dialog dialog;
            dialog = new Dialog(context);
            dialog.setContentView(R.layout.layout_dialog_theme);
            //dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
            dialog.show();
            Button closeTheme = dialog.findViewById(R.id.close_theme_dialog);
            closeTheme.setOnClickListener(view1 -> dialog.dismiss());
        });

        //Redirect to Notify Activity
        notify.setOnClickListener(v -> startActivity(new Intent(SettingsActivity.this, NotifyActivity.class)));

        //Language
        language.setOnClickListener(view -> {
            Dialog dialog;
            dialog = new Dialog(context);
            dialog.setContentView(R.layout.layout_dialog_language);
            //dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
            dialog.show();
            Button closeLanguage = dialog.findViewById(R.id.close_language_dialog);
            closeLanguage.setOnClickListener(view12 -> dialog.dismiss());
        });

        //Redirect to Auto-Delete Activity
        auto_del.setOnClickListener(v -> startActivity(new Intent(SettingsActivity.this, AutoDelActivity.class)));

        //Redirect to Category Selection Activity Activity
        //category.setOnClickListener(v -> startActivity(new Intent(SettingsActivity.this, CategorySelectionActivity.class)));

        //Help--> Default Gmail
        help.setOnClickListener(v -> {
            Intent intent=new Intent(Intent.ACTION_SEND);
            String[] recipients={"newsArkinfo@gmail.com"};
            intent.putExtra(Intent.EXTRA_EMAIL, recipients);
            intent.putExtra(Intent.EXTRA_SUBJECT,"");
            intent.putExtra(Intent.EXTRA_TEXT,"");
            intent.putExtra(Intent.EXTRA_CC,"newsArkinfo@gmail.com");
            intent.setType("text/html");
            intent.setPackage("com.google.android.gm");
            startActivity(Intent.createChooser(intent, "Send mail"));
        });
        //Redirect to User Manual Activity
        manual.setOnClickListener(v -> startActivity(new Intent(SettingsActivity.this, UserManualActivity.class)));
    }
}