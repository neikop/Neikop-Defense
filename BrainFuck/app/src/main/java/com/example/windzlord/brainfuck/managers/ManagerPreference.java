package com.example.windzlord.brainfuck.managers;

import android.content.Context;
import android.content.SharedPreferences;

/**
 * Created by WindzLord on 11/17/2016.
 */

public class ManagerPreference {

    private static final String KEY = "BrainFuck";

    private static final String SCORE = "Score";
    private static final String LEVEL = "Level";
    private static final String EXP_CURRENT = "ExpCurr";
    private static final String USER_ID = "UserID";

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

    public void putScore(String type, int index, int score) {
        sharedPreferences.edit().putInt(SCORE + type + index, score).apply();
    }

    public int getScore(String type, int index) {
        return sharedPreferences.getInt(SCORE + type + index, 0);
    }

    public void putLevel(String type, int index, int level) {
        sharedPreferences.edit().putInt(LEVEL + type + index, level).apply();
    }

    public int getLevel(String type, int index) {
        return sharedPreferences.getInt(LEVEL + type + index, 1);
    }

    public int getExpNext(String type, int index) {
        return getLevel(type, index) * 300;
    }

    public void putExpCurrent(String type, int index, int exp) {
        sharedPreferences.edit().putInt(EXP_CURRENT + type + index, exp).apply();
    }

    public int getExpCurrent(String type, int index) {
        return sharedPreferences.getInt(EXP_CURRENT + type + index, 0);
    }

    public boolean isUnlocked(String type, int index) {
        return index == 1 || getLevel(type, index - 1) > 1;
    }

    public void putUserID(String userID) {
        sharedPreferences.edit().putString(USER_ID, userID).apply();
    }

    public String getUserID() {
        return sharedPreferences.getString(USER_ID, "");
    }

    public void clear() {
        sharedPreferences.edit().clear().apply();
    }

}
