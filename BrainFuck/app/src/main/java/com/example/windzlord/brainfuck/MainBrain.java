package com.example.windzlord.brainfuck;

import android.app.Application;

import com.example.windzlord.brainfuck.managers.FileManager;
import com.example.windzlord.brainfuck.managers.Gogo;
import com.example.windzlord.brainfuck.managers.ManagerGameData;
import com.example.windzlord.brainfuck.managers.ManagerServer;
import com.example.windzlord.brainfuck.managers.ManagerNetwork;
import com.example.windzlord.brainfuck.managers.ManagerPreference;
import com.example.windzlord.brainfuck.managers.ManagerUserData;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;

/**
 * Created by WindzLord on 12/27/2016.
 */

public class MainBrain extends Application {

    @Override
    public void onCreate() {
        super.onCreate();
        settingThingsUp();
    }

    private void settingThingsUp() {
        ManagerPreference.init(this);
        ManagerGameData.init(this);
        ManagerNetwork.init(this);
        ManagerServer.init(this);
        ManagerUserData.init(this);
        FileManager.init(this);
        initImageLoader();

//
//        for (int i = 1; i < 3; i++) {
//            ManagerPreference.getInstance().putLevel(Gogo.MEMORY, i, 2);
//            ManagerPreference.getInstance().putLevel(Gogo.CALCULATION, i, 2);
//            ManagerPreference.getInstance().putLevel(Gogo.CONCENTRATION, i, 2);
//            ManagerPreference.getInstance().putLevel(Gogo.OBSERVATION, i, 2);
//        }

        String userID = ManagerPreference.getInstance().getUserID();

        if (ManagerNetwork.getInstance().isConnectedToInternet())
            ManagerServer.getInstance().uploadLocalToServer(userID);

    }

    private void initImageLoader() {
        DisplayImageOptions defaultOptions = new DisplayImageOptions.Builder()
                .cacheInMemory(true)
                .cacheOnDisk(true)
                .build();
        ImageLoaderConfiguration config = new ImageLoaderConfiguration.Builder(this)
                .defaultDisplayImageOptions(defaultOptions)
                .build();
        ImageLoader.getInstance().init(config);
    }
}
