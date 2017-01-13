package com.example.windzlord.brainfuck;

import android.app.Application;

import com.example.windzlord.brainfuck.adapters.CountDownTimerAdapter;
import com.example.windzlord.brainfuck.managers.Gogo;
import com.example.windzlord.brainfuck.managers.ManagerFile;
import com.example.windzlord.brainfuck.managers.ManagerGameData;
import com.example.windzlord.brainfuck.managers.ManagerNetwork;
import com.example.windzlord.brainfuck.managers.ManagerPreference;
import com.example.windzlord.brainfuck.managers.ManagerServer;
import com.example.windzlord.brainfuck.managers.ManagerUserData;
import com.facebook.FacebookSdk;
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
        ManagerFile.init(this);
        FacebookSdk.sdkInitialize(this);
        initImageLoader();

        String userID = ManagerPreference.getInstance().getUserID();
        ManagerServer.getInstance().uploadLocalToServer(userID);

        goLoopSync(Gogo.GAME_LOOPER_SYNC);
    }

    private void goLoopSync(boolean loop) {
        new CountDownTimerAdapter(30000) {
            @Override
            public void onFinish() {
                String userID = ManagerPreference.getInstance().getUserID();
                ManagerServer.getInstance().uploadLocalToServer(userID);
                if (loop) goLoopSync(loop);
            }
        }.start();
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
