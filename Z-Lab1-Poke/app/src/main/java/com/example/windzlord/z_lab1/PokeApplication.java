package com.example.windzlord.z_lab1;

import android.app.Application;

import com.example.windzlord.z_lab1.managers.SQLiteContext;
import com.example.windzlord.z_lab1.managers.Preference;

/**
 * Created by WindzLord on 11/17/2016.
 */

public class PokeApplication extends Application {

    @Override
    public void onCreate() {
        super.onCreate();
        settingThingsUp();
    }

    private void settingThingsUp() {
        Preference.init(this);
        SQLiteContext.init(this);
    }
}
