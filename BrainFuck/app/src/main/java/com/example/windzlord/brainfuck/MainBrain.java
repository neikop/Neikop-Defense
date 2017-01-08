package com.example.windzlord.brainfuck;

import android.app.Application;

import com.example.windzlord.brainfuck.managers.ManagerDatabaseGame;
import com.example.windzlord.brainfuck.managers.ManagerServer;
import com.example.windzlord.brainfuck.managers.ManagerNetwork;
import com.example.windzlord.brainfuck.managers.ManagerPreference;

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
        ManagerDatabaseGame.init(this);
        ManagerNetwork.init(this);
        ManagerServer.init(this);

        ManagerServer.getInstance().gameUpload(
                ManagerPreference.getInstance().getUserID());
    }
}
