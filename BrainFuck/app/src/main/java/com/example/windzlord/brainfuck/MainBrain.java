package com.example.windzlord.brainfuck;

import android.app.Application;

import com.example.windzlord.brainfuck.managers.DBContextSV;
import com.example.windzlord.brainfuck.managers.Gogo;
import com.example.windzlord.brainfuck.managers.ManagerNetwork;
import com.example.windzlord.brainfuck.managers.ManagerPreference;
import com.example.windzlord.brainfuck.screens.games.calculation.CalcuOne;
import com.example.windzlord.brainfuck.screens.games.calculation.CalcuTwo;

/**
 * Created by WindzLord on 12/27/2016.
 */

public class MainBrain extends Application {

    @Override
    public void onCreate() {
        super.onCreate();
        settingThingsUp();
        ManagerPreference managerPreference = new ManagerPreference(getBaseContext());
        managerPreference.putLevel(Gogo.CALCULATION,1,2);

        ManagerPreference.getInstance().putUserID("daicahai");
        String userID = ManagerPreference.getInstance().getUserID();
        DBContextSV.getInstance().settingThingsUp("daicahai");
    }

    private void settingThingsUp() {
        ManagerNetwork.init(this);
        ManagerPreference.init(this);
        DBContextSV.init(this);
    }
}
