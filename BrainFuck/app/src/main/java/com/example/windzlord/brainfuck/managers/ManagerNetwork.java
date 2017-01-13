package com.example.windzlord.brainfuck.managers;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.util.Log;

/**
 * Created by WindzLord on 10/22/2016.
 */

public class ManagerNetwork {

    private final String TAG = this.getClass().getSimpleName();

    private ConnectivityManager connectivityManager;

    private ManagerNetwork(Context context) {
        connectivityManager = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
    }

    public boolean isConnectedToInternet() {
        NetworkInfo networkInfo = connectivityManager.getActiveNetworkInfo();
        boolean isConnectedToInternet = networkInfo != null && networkInfo.isConnected();
        Log.d(TAG, "INTERNET " + (isConnectedToInternet ? "OK" : "DOWN"));
        return isConnectedToInternet;
    }

    private static ManagerNetwork instance;

    public static ManagerNetwork getInstance() {
        return instance;
    }

    public static void init(Context context) {
        instance = new ManagerNetwork(context);
    }
}
