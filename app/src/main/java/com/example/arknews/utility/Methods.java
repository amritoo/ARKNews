package com.example.arknews.utility;

import android.content.Context;

import com.example.arknews.dao.ARKDatabase;
import com.example.arknews.ui.settings.AutoDelActivity;

import java.util.Date;

public class Methods {

    public static Date convertDate(String dateString) {
        // TODO format and add date

//        String dateString = "2022-01-11T06:51:47Z";
//        System.out.println(dateString);
//
//        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss'Z'");

        return new Date();
    }

    public static void autoDelete(Context context) {
        int time = Preferences.getInstance(context).read(AutoDelActivity.AUTODELTIME, 30);
        Date date = new Date(new Date().getTime() - time * 86400000L); //24 * 60 * 60 * 1000
        ARKDatabase.getInstance(context).newsDao().deleteBefore(date);
    }
}
