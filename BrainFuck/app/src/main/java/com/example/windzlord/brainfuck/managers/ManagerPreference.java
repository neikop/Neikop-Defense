package com.example.windzlord.brainfuck.managers;

import android.content.Context;
import android.content.SharedPreferences;

/**
 * Created by WindzLord on 11/17/2016.
 */

public class ManagerPreference {

    private static final String KEY = "BrainFuck";

    private static final String KEY_FAVOURITE_MEDIA = "favourite";


    private SharedPreferences sharedPreferences;

    public ManagerPreference(Context context) {
        sharedPreferences = context.getSharedPreferences(KEY, Context.MODE_PRIVATE);
    }

    private static ManagerPreference instance;

    public static ManagerPreference getInstance() {
        return instance;
    }

    public static void init(Context context) {
        instance = new ManagerPreference(context);
    }

    public void putStatusGene(int gen, boolean isActive) {
        sharedPreferences.edit().putBoolean(KEY_FAVOURITE_MEDIA + gen, isActive).apply();
    }

    public boolean getFavourite(int gen) {
        return sharedPreferences.getBoolean(KEY_FAVOURITE_MEDIA + gen, false);
    }

}
