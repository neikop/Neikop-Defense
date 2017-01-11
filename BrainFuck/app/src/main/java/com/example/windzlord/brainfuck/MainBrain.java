package com.example.windzlord.brainfuck;

import android.app.Application;

import com.example.windzlord.brainfuck.managers.Gogo;
import com.example.windzlord.brainfuck.managers.ManagerGameData;
import com.example.windzlord.brainfuck.managers.ManagerServer;
import com.example.windzlord.brainfuck.managers.ManagerNetwork;
import com.example.windzlord.brainfuck.managers.ManagerPreference;
import com.example.windzlord.brainfuck.managers.ManagerUserData;

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
//
//        for (int i = 1; i < 3; i++) {
//            ManagerPreference.getInstance().putLevel(Gogo.MEMORY, i, 2);
//            ManagerPreference.getInstance().putLevel(Gogo.CALCULATION, i, 2);
//            ManagerPreference.getInstance().putLevel(Gogo.CONCENTRATION, i, 2);
//            ManagerPreference.getInstance().putLevel(Gogo.OBSERVATION, i, 2);
//        }

        String userID = ManagerPreference.getInstance().getUserID();
        if (userID.isEmpty()) {
            System.out.println("User = NULL");
        } else System.out.println("User = " + userID);

        if (ManagerNetwork.getInstance().isConnectedToInternet())
            ManagerServer.getInstance().uploadLocalToServer(userID);

    }
}
