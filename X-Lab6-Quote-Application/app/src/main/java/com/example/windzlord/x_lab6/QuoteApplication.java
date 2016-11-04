package com.example.windzlord.x_lab6;

import android.app.Application;

import com.example.windzlord.x_lab6.managers.DBContextRealm;
import com.example.windzlord.x_lab6.managers.DBContextSQLite;
import com.example.windzlord.x_lab6.managers.FileManager;
import com.example.windzlord.x_lab6.managers.NetworkManager;
import com.example.windzlord.x_lab6.managers.Preferences;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;

/**
 * Created by WindzLord on 10/12/2016.
 */

public class QuoteApplication extends Application {

    @Override
    public void onCreate() {
        super.onCreate();

        ImageLoader.getInstance().init(new ImageLoaderConfiguration.Builder(this)
                .defaultDisplayImageOptions(DisplayImageOptions.createSimple()).build());
        Preferences.init(this);
        NetworkManager.init(this);
        FileManager.init(this);
        DBContextSQLite.init(this);
        DBContextRealm.init();
    }
}
