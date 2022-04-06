package com.example.arknews.ui.settings;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.RadioButton;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AppCompatDelegate;

import com.example.arknews.R;
import com.example.arknews.ui.categories.CategoryActivity;
import com.example.arknews.ui.help.FAQActivity;
import com.example.arknews.utility.Methods;
import com.example.arknews.utility.Preferences;
import com.google.android.material.appbar.MaterialToolbar;
import com.google.android.material.button.MaterialButton;

public class SettingsActivity extends AppCompatActivity {

    public static final String LANGUAGE_PREF = "lan_p";

    private MaterialButton themeMaterialButton, notifyMaterialButton, languageMaterialButton,
            autoDelMaterialButton, categoryMaterialButton, contactUsMaterialButton, faqMaterialButton;
    private MaterialToolbar toolbarSettings;
    private Dialog languageDialog;

    Context context;

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
        contactUsMaterialButton = findViewById(R.id.settings_help);
        faqMaterialButton = findViewById(R.id.settings_user_manual);
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
            languageDialog = new Dialog(context);
            languageDialog.setContentView(R.layout.layout_dialog_language);
            languageDialog.show();
        });

        //Redirect to Auto-Delete Activity
        autoDelMaterialButton.setOnClickListener(v -> startActivity(new Intent(SettingsActivity.this, AutoDelActivity.class)));

        //Redirect to Category Selection Activity Activity
        categoryMaterialButton.setOnClickListener(v -> startActivity(new Intent(SettingsActivity.this, CategoryActivity.class)));

        //Help--> Default Gmail
        contactUsMaterialButton.setOnClickListener(v -> {
            new AlertDialog.Builder(this)
                    .setTitle("Confirmation")
                    .setMessage("Are you sure you would like to contact us via email?")
                    .setPositiveButton(android.R.string.ok, (dialog, which) -> {
                        Intent intent1 = new Intent(Intent.ACTION_SEND);
                        String[] recipients = {"newsArkinfo@gmail.com"};
                        intent1.putExtra(Intent.EXTRA_EMAIL, recipients);
                        intent1.putExtra(Intent.EXTRA_SUBJECT, "");
                        intent1.putExtra(Intent.EXTRA_TEXT, "");
                        intent1.putExtra(Intent.EXTRA_CC, "newsArkinfo@gmail.com");
                        intent1.setType("text/html");
                        intent1.setPackage("com.google.android.gm");
                        startActivity(Intent.createChooser(intent1, "Send mail"));
                    })
                    .setNegativeButton(android.R.string.cancel, null)
                    .setIcon(android.R.drawable.ic_dialog_alert)
                    .show();
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
                    Preferences.getInstance(this).write(LANGUAGE_PREF, "en");
                    Methods.setLanguagePref(this, "en");
                    languageDialog.dismiss();
                    recreate();
                    Toast.makeText(getApplicationContext(), "English selected", Toast.LENGTH_SHORT).show();
                }
                break;
            case R.id.language_bengali_rb:
                if (checked) {
                    Preferences.getInstance(this).write(LANGUAGE_PREF, "bn");
                    Methods.setLanguagePref(this, "be");
                    languageDialog.dismiss();
                    recreate();
                    Toast.makeText(getApplicationContext(), "Bengali selected", Toast.LENGTH_SHORT).show();
                }
                break;
            case R.id.language_nepali_rb:
                if (checked) {
                    Preferences.getInstance(this).write(LANGUAGE_PREF, "ne");
                    Methods.setLanguagePref(this, "ne");
                    languageDialog.dismiss();
                    recreate();
                    Toast.makeText(getApplicationContext(), "Nepali selected", Toast.LENGTH_SHORT).show();
                }
                break;
        }
    }

}