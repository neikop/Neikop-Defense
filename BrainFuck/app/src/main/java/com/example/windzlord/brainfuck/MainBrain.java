package com.example.windzlord.brainfuck;

import android.app.Application;

import com.example.windzlord.brainfuck.managers.DBContextSV;
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

        //Test
//        ManagerPreference.getInstance().putUserID("daicahai");
////        String userID = ManagerPreference.getInstance().getUserID();
//        DBContextSV.getInstance().settingStartApp("daicahai");
//        ManagerPreference.getInstance().putLevel(Gogo.CALCULATION,1,2);
        //Test default UserID
//        ManagerPreference.getInstance().putUserID("daicahai");
//        String userID = ManagerPreference.getInstance().getUserID();
//        DBContextSV.getInstance().settingStartApp("daicahai");

        if(ManagerNetwork.getInstance().isConnectedToInternet()){
            String userID = ManagerPreference.getInstance().getUserID();
            if(!userID.equals("")){
                DBContextSV.getInstance().settingStartApp(userID);
            }
        }

    }
}
