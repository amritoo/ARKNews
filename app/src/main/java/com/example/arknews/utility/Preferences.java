package com.example.arknews.utility;

import android.content.Context;
import android.content.SharedPreferences;

public class Preferences {

    private static final String PREFERENCE_NAME = "ark_pref";
    private static Preferences instance;
    private final SharedPreferences preferences;

    public Preferences(Context context) {
        this.preferences = context.getSharedPreferences(PREFERENCE_NAME, Context.MODE_PRIVATE);
    }

    public static synchronized Preferences getInstance(Context context) {
        if (instance == null) {
            instance = new Preferences(context);
        }
        return instance;
    }

    public void write(String key, String value) {
        SharedPreferences.Editor editor = preferences.edit();
        editor.putString(key, value);
        editor.apply();
    }

    public void write(String key, Boolean value) {
        SharedPreferences.Editor editor = preferences.edit();
        editor.putBoolean(key, value);
        editor.apply();
    }

    public void write(String key, int value) {
        SharedPreferences.Editor editor = preferences.edit();
        editor.putInt(key, value);
        editor.apply();
    }

    public String read(String key, String defaultValue) {
        return preferences.getString(key, defaultValue);
    }

    public boolean read(String key, boolean defaultValue) {
        return preferences.getBoolean(key, defaultValue);
    }

    public int read(String key, int defaultValue) {
        return preferences.getInt(key, defaultValue);
    }

}
