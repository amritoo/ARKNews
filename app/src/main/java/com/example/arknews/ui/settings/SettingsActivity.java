package com.example.arknews.ui.settings;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.RadioButton;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AppCompatDelegate;

import com.example.arknews.R;
import com.example.arknews.ui.categories.CategorySelectionActivity;
import com.example.arknews.ui.help.FAQActivity;
import com.example.arknews.utility.Preferences;
import com.google.android.material.appbar.MaterialToolbar;
import com.google.android.material.button.MaterialButton;

public class SettingsActivity extends AppCompatActivity {

    private MaterialButton themeMaterialButton, notifyMaterialButton, languageMaterialButton, autoDelMaterialButton, categoryMaterialButton, helpMaterialButton, faqMaterialButton;
    Context context;
    MaterialToolbar toolbarSettings;
    RadioButton english_rb, bengali_rb, nepali_rb;

    public static final String LANGUAGE_PREF = "lan_p";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);

        context = this;
        initializeViews();
        setListeners();

    }

    void initializeViews() {
        toolbarSettings = findViewById(R.id.settings_toolbar);
        themeMaterialButton = findViewById(R.id.settings_theme);
        notifyMaterialButton = findViewById(R.id.settings_notify);
        languageMaterialButton = findViewById(R.id.settings_language);
        autoDelMaterialButton = findViewById(R.id.settings_auto_deletion);
        categoryMaterialButton = findViewById(R.id.settings_category_selection);
        helpMaterialButton = findViewById(R.id.settings_help);
        faqMaterialButton = findViewById(R.id.settings_user_manual);

        english_rb = findViewById(R.id.language_english_rb);
        bengali_rb = findViewById(R.id.language_bengali_rb);
        nepali_rb = findViewById(R.id.language_nepali_rb);

    }

    void setListeners() {
        toolbarSettings.setNavigationOnClickListener(v -> finish());

        //Appearance
        themeMaterialButton.setOnClickListener(view -> {
            Dialog dialog;
            dialog = new Dialog(context);
            dialog.setContentView(R.layout.layout_dialog_theme);
            //Todo show already selected radio button
            dialog.show();
        });

        //Redirect to Notify Activity
        notifyMaterialButton.setOnClickListener(v -> startActivity(new Intent(SettingsActivity.this, NotificationActivity.class)));

        //Language
        languageMaterialButton.setOnClickListener(view -> {
            Dialog dialog;
            dialog = new Dialog(context);
            dialog.setContentView(R.layout.layout_dialog_language);
            dialog.show();

//            int language_pref = Preferences.getInstance(this).read(LANGUAGE_PREF, 0);
//            switch (language_pref) {
//                case 0:
//                    english_rb.setChecked(true);
//                    break;
//                case 1:
//                    bengali_rb.setChecked(true);
//                    break;
//                case 2:
//                    nepali_rb.setChecked(true);
//                    break;
//            }

            MaterialButton closeLanguage = dialog.findViewById(R.id.language_close_mb);
            closeLanguage.setOnClickListener(view12 -> dialog.dismiss());
        });

        //Redirect to Auto-Delete Activity
        autoDelMaterialButton.setOnClickListener(v -> startActivity(new Intent(SettingsActivity.this, AutoDelActivity.class)));

        //Redirect to Category Selection Activity Activity
        categoryMaterialButton.setOnClickListener(v -> startActivity(new Intent(SettingsActivity.this, CategorySelectionActivity.class)));

        //Help--> Default Gmail
        helpMaterialButton.setOnClickListener(v -> {
            Intent intent = new Intent(Intent.ACTION_SEND);
            String[] recipients = {"newsArkinfo@gmail.com"};
            intent.putExtra(Intent.EXTRA_EMAIL, recipients);
            intent.putExtra(Intent.EXTRA_SUBJECT, "");
            intent.putExtra(Intent.EXTRA_TEXT, "");
            intent.putExtra(Intent.EXTRA_CC, "newsArkinfo@gmail.com");
            intent.setType("text/html");
            intent.setPackage("com.google.android.gm");
            startActivity(Intent.createChooser(intent, "Send mail"));
        });
        //Redirect to User Manual Activity
        faqMaterialButton.setOnClickListener(v -> startActivity(new Intent(SettingsActivity.this, FAQActivity.class)));
    }

    public void onRadioButtonClicked(View view) {
        boolean checked = ((RadioButton) view).isChecked();
        switch (view.getId()) {
            case R.id.theme_dark_rb:
                if (checked) {
                    AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES);
                    Preferences.getInstance(this).write("theme", "dark");
                    Toast.makeText(getApplicationContext(), "Theme changed to Dark theme", Toast.LENGTH_SHORT).show();
                }
                break;
            case R.id.theme_light_rb:
                if (checked) {
                    AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO);
                    Preferences.getInstance(this).write("theme", "light");
                    Toast.makeText(getApplicationContext(), "Theme changed to Dark theme", Toast.LENGTH_SHORT).show();
                }
                break;
            case R.id.language_english_rb:
                if (checked) {
                    Preferences.getInstance(this).write(LANGUAGE_PREF, 0);
                    Toast.makeText(getApplicationContext(), "English selected", Toast.LENGTH_SHORT).show();
                }
                break;
            case R.id.language_bengali_rb:
                if (checked) {
                    Preferences.getInstance(this).write(LANGUAGE_PREF, 1);
                    Toast.makeText(getApplicationContext(), "Bengali selected", Toast.LENGTH_SHORT).show();
                }
                break;
            case R.id.language_nepali_rb:
                if (checked) {
                    Preferences.getInstance(this).write(LANGUAGE_PREF, 2);
                    Toast.makeText(getApplicationContext(), "Nepali selected", Toast.LENGTH_SHORT).show();
                }
                break;
        }

    }
}