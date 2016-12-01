package com.example.windzlord.z_lab2_music.managers;

import android.content.Context;
import android.content.SharedPreferences;

/**
 * Created by WindzLord on 11/17/2016.
 */

public class PreferenceManager {

    private static final String KEY = "myMusicApplication";

    private static final String KEY_FAVOURITE_MEDIA = "favourite";


    private SharedPreferences sharedPreferences;

    public PreferenceManager(Context context) {
        sharedPreferences = context.getSharedPreferences(KEY, Context.MODE_PRIVATE);
    }

    private static PreferenceManager instance;

    public static PreferenceManager getInstance() {
        return instance;
    }

    public static void init(Context context) {
        instance = new PreferenceManager(context);
    }

    public void putStatusGene(int gen, boolean isActive) {
        sharedPreferences.edit().putBoolean(KEY_FAVOURITE_MEDIA + gen, isActive).apply();
    }

    public boolean getFavourite(int gen) {
        return sharedPreferences.getBoolean(KEY_FAVOURITE_MEDIA + gen, false);
    }

}
