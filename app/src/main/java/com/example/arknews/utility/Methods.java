package com.example.arknews.utility;

import android.content.Context;

import com.example.arknews.dao.ARKDatabase;
import com.example.arknews.ui.settings.AutoDelActivity;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

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

    public static void autoDelete(Context context) {
        int time = Preferences.getInstance(context).read(AutoDelActivity.AUTODELTIME, 30);
        Date date = new Date(new Date().getTime() - time * 86400000L); //24 * 60 * 60 * 1000
        ARKDatabase.getInstance(context).newsDao().deleteBefore(date);
    }
}
