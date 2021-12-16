package com.example.arknews.settings;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;

import com.example.arknews.R;
import com.google.android.material.appbar.MaterialToolbar;

public class SettingsActivity extends AppCompatActivity {

    private Button theme, language, auto_del, category, help, manual;
    Context context;
    MaterialToolbar toolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);

        toolbar = findViewById(R.id.settings_toolbar);
        toolbar.setNavigationOnClickListener(v -> finish());

        context = this;
        //Appearance
        theme = findViewById(R.id.theme);
        theme.setOnClickListener(view -> {
            Dialog dialog;
            dialog = new Dialog(context);
            dialog.setContentView(R.layout.layout_dialog_theme);
            //dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
            dialog.show();

            Button closeTheme = dialog.findViewById(R.id.close_theme_dialog);
            closeTheme.setOnClickListener(view1 -> dialog.dismiss());

        });

        //Language
        language = findViewById(R.id.language);
        language.setOnClickListener(view -> {
            Dialog dialog;
            dialog = new Dialog(context);
            dialog.setContentView(R.layout.layout_dialog_language);
            //dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
            dialog.show();

            Button closeLanguage = dialog.findViewById(R.id.close_language_dialog);
            closeLanguage.setOnClickListener(view12 -> dialog.dismiss());

        });

        //Redirect to User Manual Activity
        manual = findViewById(R.id.user_manual);
        manual.setOnClickListener(view -> startActivity(new Intent(SettingsActivity.this, UserManualActivity.class)));

        //Redirect to Auto-Delete Activity
        auto_del = findViewById(R.id.auto_deletion);
        auto_del.setOnClickListener(view -> startActivity(new Intent(SettingsActivity.this, AutoDelActivity.class)));

    }

}