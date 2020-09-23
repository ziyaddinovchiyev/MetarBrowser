package com.cinemo.metarbrowser.util;

import android.content.Context;
import android.content.SharedPreferences;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;

import com.cinemo.metarbrowser.MetarApp;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Locale;

public class Utils {

    public static SimpleDateFormat df = new SimpleDateFormat("yyyy/MM/dd hh:mm a", Locale.GERMAN);

    public static void writeLastUpdateDate() {
        SharedPreferences sharedPreferences = MetarApp.get().getApplicationContext().getSharedPreferences("WORKER", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString("lastUpdatedAt", Utils.df.format(Calendar.getInstance().getTime()));
        editor.apply();
    }

    public static boolean isNetworkAvailable() {
        ConnectivityManager cm = (ConnectivityManager) MetarApp.get().getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo activeNetwork = cm.getActiveNetworkInfo();
        return activeNetwork != null && activeNetwork.isConnectedOrConnecting();
    }
}
