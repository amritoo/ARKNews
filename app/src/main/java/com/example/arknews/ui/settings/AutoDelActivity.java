package com.example.arknews.ui.settings;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.RadioButton;
import android.widget.Toast;

import com.example.arknews.R;
import com.example.arknews.utility.Preferences;
import com.google.android.material.appbar.MaterialToolbar;

public class AutoDelActivity extends AppCompatActivity {

    public static final String AUTODELTIME = "autodel_time";

    MaterialToolbar toolbarAutoDel;

    RadioButton radioButton2, radioButton7, radioButton30;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_auto_del);

        toolbarAutoDel = findViewById(R.id.deletion_toolbar);
        toolbarAutoDel.setNavigationOnClickListener(v -> finish());

        radioButton2 = findViewById(R.id.autodel_day_rb);
        radioButton7 = findViewById(R.id.autodel_week_rb);
        radioButton30 = findViewById(R.id.autodel_month_rb);

        int time = Preferences.getInstance(this).read(AUTODELTIME, 30);
        switch (time) {
            case 2:
                radioButton2.setChecked(true);
                break;
            case 7:
                radioButton7.setChecked(true);
                break;
            case 30:
                radioButton30.setChecked(true);
                break;
        }
    }

    public void onRadioButtonClicked(View view) {
        boolean checked = ((RadioButton) view).isChecked();
        switch (view.getId()) {
            case R.id.autodel_day_rb:
                if (checked) {
                    Preferences.getInstance(this).write(AUTODELTIME, 2);
                }
                break;
            case R.id.autodel_week_rb:
                if (checked) {
                    Preferences.getInstance(this).write(AUTODELTIME, 7);
                }
                break;
            case R.id.autodel_month_rb:
                if (checked) {
                    Preferences.getInstance(this).write(AUTODELTIME, 30);
                }
                break;
        }
    }

}

