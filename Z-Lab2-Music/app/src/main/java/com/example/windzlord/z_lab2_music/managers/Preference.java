package com.example.windzlord.z_lab2_music.managers;

import android.content.Context;
import android.content.SharedPreferences;

/**
 * Created by WindzLord on 11/17/2016.
 */

public class Preference {

    private static final String KEY = "myMusicApplication";
    private static final String KEY_ACTIVE_GEN = "myActivatingGenes";
    private static final String KEY_ACTIVE_SOUND = "myActivatingSound";
    private static final String KEY_ACTIVE_MUSIC = "myActivatingMusic";

    private static final String KEY_SCORE = "myScore";
    private static final String KEY_HIGH_SCORE = "myHighScore";

    private SharedPreferences sharedPreferences;

    public Preference(Context context) {
        sharedPreferences = context.getSharedPreferences(KEY, Context.MODE_PRIVATE);
        putScore(0);
    }

    private static Preference instance;

    public static Preference getInstance() {
        return instance;
    }

    public static void init(Context context) {
        instance = new Preference(context);
    }

    public void putStatusGene(int gen, boolean isActive) {
        sharedPreferences.edit().putBoolean(KEY_ACTIVE_GEN + gen, isActive).apply();
    }

    public boolean getStatusGene(int gen) {
        return sharedPreferences.getBoolean(KEY_ACTIVE_GEN + gen, (gen == 1));
    }

    public void putStatusSound(boolean isActive) {
        sharedPreferences.edit().putBoolean(KEY_ACTIVE_SOUND, isActive).apply();
    }

    public boolean getStatusSound() {
        return sharedPreferences.getBoolean(KEY_ACTIVE_SOUND, true);
    }

    public void putStatusMusic(boolean isActive) {
        sharedPreferences.edit().putBoolean(KEY_ACTIVE_MUSIC, isActive).apply();
    }

    public boolean getStatusMusic() {
        return sharedPreferences.getBoolean(KEY_ACTIVE_MUSIC, true);
    }

    public void putScore(int score) {
        sharedPreferences.edit().putInt(KEY_SCORE, score).apply();
    }

    public int getScore() {
        return sharedPreferences.getInt(KEY_SCORE, 0);
    }

    public void putHighScore(int score) {
        sharedPreferences.edit().putInt(KEY_HIGH_SCORE, score).apply();
    }

    public int getHighScore() {
        return sharedPreferences.getInt(KEY_HIGH_SCORE, 0);
    }

}
