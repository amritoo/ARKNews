package com.example.arknews.settings;

import androidx.appcompat.app.AppCompatActivity;

import com.example.arknews.R;
import com.google.android.material.textview.MaterialTextView;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.ToggleButton;

public class SettingsActivity extends AppCompatActivity {

    private Button theme, language, auto_del, category, help, manual;
    Context context;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);
        getSupportActionBar().hide();

        context= this;
        //Appearance
        theme = (Button) findViewById(R.id.theme);
        theme.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Dialog dialog;
                dialog = new Dialog(context);
                dialog.setContentView(R.layout.layout_dialog_theme);
                //dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
                dialog.show();

                Button closeTheme= (Button) dialog.findViewById(R.id.close_theme_dialog);
                closeTheme.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        dialog.dismiss();
                    }
                });

            }
        });

        //Language
        language = (Button) findViewById(R.id.language);
        language.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Dialog dialog;
                dialog = new Dialog(context);
                dialog.setContentView(R.layout.layout_dialog_language);
                //dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
                dialog.show();

                Button closeLanguage= (Button) dialog.findViewById(R.id.close_language_dialog);
                closeLanguage.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        dialog.dismiss();
                    }
                });

            }
        });

        //Redirect to User Manual Activity
        manual= (Button) findViewById(R.id.user_manual);
        manual.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(SettingsActivity.this, UserManualActivity.class));
            }
        });

        //Redirect to Auto-Delete Activity
        auto_del= (Button) findViewById(R.id.auto_deletion);
        auto_del.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(SettingsActivity.this, AutoDelActivity.class));
            }
        });



    }

}