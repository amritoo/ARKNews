package com.example.arknews.ui.settings;

import android.os.Bundle;
import android.view.View;
import android.widget.RadioButton;
import android.widget.RadioGroup;

import androidx.appcompat.app.AppCompatActivity;

import com.example.arknews.R;
import com.example.arknews.utility.Preferences;
import com.google.android.material.appbar.MaterialToolbar;

public class NotificationActivity extends AppCompatActivity {

    public static final String NOTIFICATION_STATUS = "notification_status";

    MaterialToolbar toolbarNotify;
    RadioButton notification_on_rb, notification_off_rb;
    RadioGroup radioGroup;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_notification);

        toolbarNotify = findViewById(R.id.notification_toolbar);
        notification_on_rb = findViewById(R.id.notification_on_rb);
        notification_off_rb = findViewById(R.id.notification_off_rb);

        toolbarNotify.setNavigationOnClickListener(v -> finish());

        boolean status = Preferences.getInstance(this).read(NOTIFICATION_STATUS, true);
        if (status)
            notification_on_rb.setChecked(true);
        else
            notification_off_rb.setChecked(true);
    }

    public void onRadioButtonClicked(View view) {
        if (notification_on_rb.isChecked())
            Preferences.getInstance(this).write(NOTIFICATION_STATUS, true);
        else
            Preferences.getInstance(this).write(NOTIFICATION_STATUS, false);
    }

}