package com.example.windzlord.x_lab6.managers;

import android.content.Context;
import android.content.SharedPreferences;

/**
 * Created by WindzLord on 10/12/2016.
 */

public class Preferences {
    private static final String KEY = "myQuoteApplication";
    private static final String KEY_USERNAME = "username";
    private static final String KEY_IMAGE_COUNT = "imageCount";
    private SharedPreferences sharedPreferences;

    public Preferences(Context context) {
        sharedPreferences = context.getSharedPreferences(KEY, Context.MODE_PRIVATE);
    }

    public String getUsername() {
        return sharedPreferences.getString(KEY_USERNAME, null);
    }

    public int getImageCount() {
        return sharedPreferences.getInt(KEY_IMAGE_COUNT, -1);
    }

    public void putImageCount(int imageCount) {
        sharedPreferences.edit().putInt(KEY_IMAGE_COUNT, imageCount).apply();
    }

    public void putUsername(String username) {
        sharedPreferences.edit().putString(KEY_USERNAME, username).apply();
    }

    private static Preferences instance;
    public static Preferences getInstance() {
        return instance;
    }

    public static void init(Context context) {
        instance = new Preferences(context);
    }

    public void clearCache() {
        putUsername(null);
    }
}
