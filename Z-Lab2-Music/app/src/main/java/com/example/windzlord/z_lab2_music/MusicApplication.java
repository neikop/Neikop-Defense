package com.example.windzlord.z_lab2_music;

import android.app.Application;
import android.content.Intent;

import com.example.windzlord.z_lab2_music.services.MediaDownloader;

import io.realm.Realm;

/**
 * Created by WindzLord on 11/28/2016.
 */

public class MusicApplication extends Application {

    @Override
    public void onCreate() {
        super.onCreate();
        settingThingsUp();
    }

    private void settingThingsUp() {
        startService(new Intent(this, MediaDownloader.class));
    }
}
