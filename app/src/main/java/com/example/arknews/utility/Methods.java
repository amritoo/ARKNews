package com.example.arknews.utility;

import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.Context;
import android.content.res.Configuration;
import android.content.res.Resources;

import com.example.arknews.dao.ARKDatabase;
import com.example.arknews.ui.settings.AutoDelActivity;
import com.example.arknews.ui.settings.SettingsActivity;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

public class Methods {

    public static Date convertDate(String dateString) {
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss'Z'");
        Date date = null;
        try {
            date = format.parse(dateString);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return date;
    }

    public static String convertDateToString(Date date) {
        SimpleDateFormat dateFormat = new SimpleDateFormat("dd MMM yyyy, HH:mm");
        String dateTime = dateFormat.format(date);
        return dateTime;
    }

    public static String convertDateToShortString(Date date) {
        SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
        String dateTime = dateFormat.format(date);
        return dateTime;
    }

    public static void autoDelete(Context context) {
        int time = Preferences.getInstance(context).read(AutoDelActivity.AUTO_DELETE_TIME, 30);
        Date date = new Date(new Date().getTime() - time * 86400000L); //24 * 60 * 60 * 1000
        ARKDatabase.getInstance(context).newsDao().deleteBefore(date);
    }

    public static void setLanguagePref(Context context) {
        String language = Preferences.getInstance(context).read(SettingsActivity.LANGUAGE_PREF, "en");

        Locale locale = new Locale(language);
        Locale.setDefault(locale);

        Resources resources = context.getResources();
        Configuration config = resources.getConfiguration();
        config.setLocale(locale);
        config.setLayoutDirection(locale);
        resources.updateConfiguration(config, resources.getDisplayMetrics());
    }

    public static void copyToClipboard(Context context, String data) {
        ClipboardManager clipboard = (ClipboardManager) context.getSystemService(Context.CLIPBOARD_SERVICE);
        ClipData clip = ClipData.newPlainText("ark-news", data);
        clipboard.setPrimaryClip(clip);
    }

}
