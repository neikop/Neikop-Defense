package com.example.windzlord.brainfuck;

import android.app.Application;

import com.example.windzlord.brainfuck.managers.ManagerServer;
import com.example.windzlord.brainfuck.managers.ManagerNetwork;
import com.example.windzlord.brainfuck.managers.ManagerPreference;
import com.example.windzlord.brainfuck.managers.SQLiteDBHelper;

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
        ManagerNetwork.init(this);
        ManagerServer.init(this);
        SQLiteDBHelper.init(this);

        String userID = ManagerPreference.getInstance().getUserID();
        System.out.println(userID.isEmpty());
        if (ManagerNetwork.getInstance().isConnectedToInternet()) {
            ManagerServer.getInstance().settingStartApp(userID);
        }
    }
}
