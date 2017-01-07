package com.example.windzlord.brainfuck;

import android.app.Application;

import com.example.windzlord.brainfuck.managers.DBContextSV;
import com.example.windzlord.brainfuck.managers.Gogo;
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
        ManagerNetwork.init(this);
        ManagerPreference.init(this);
        DBContextSV.init(this);

        ManagerPreference.getInstance().putUserID("");

        for (int i = 1; i < 4; i++)
            for (String game : Gogo.GAME_LIST)
                ManagerPreference.getInstance().putLevel(game, i, 2);

        if (ManagerNetwork.getInstance().isConnectedToInternet()) {
            String userID = ManagerPreference.getInstance().getUserID();
            if (!userID.equals(""))
                DBContextSV.getInstance().settingStartApp(userID);
        }

    }
}
