package com.example.windzlord.brainfuck.managers;

import android.app.Activity;
import android.content.res.AssetFileDescriptor;
import android.media.MediaPlayer;
import android.util.Log;

import java.io.IOException;

/**
 * Created by WindzLord on 12/27/2016.
 */

public class ManagerBrain {

    private static final String TAG = "ManagerBrain";

    public static final int QUIZ = 5;
    public static final int TIME = 5000;
    public static final boolean GAME_LOOPER_SYNC = true;

    public final static String MEMORY = "Memory";
    public final static String CALCULATION = "Calculation";
    public final static String CONCENTRATION = "Concentration";
    public final static String OBSERVATION = "Observation";

    public final static String[] GAME_LIST = {MEMORY, CALCULATION, CONCENTRATION, OBSERVATION};

    public final static String SOUND_CORRECT = "sounds/true_sound.wav";
    public final static String SOUND_WRONG = "sounds/wrong_sound.wav";
    public final static String SOUND_PING = "sounds/ping_sound.wav";
    public final static String SOUND_WELCOME = "sounds/welcome.mp3";

    public static void goMusic(Activity activity, String song, boolean loop) {
        try {
            AssetFileDescriptor file = activity.getAssets().openFd(song);
            MediaPlayer player = new MediaPlayer();
            player.setDataSource(file.getFileDescriptor(), file.getStartOffset(), file.getLength());
            player.prepare();
            player.setLooping(loop);
            player.start();
        } catch (NullPointerException | IOException musicException) {
            Log.d(TAG, "goMusic " + musicException);
        }
    }

    public static String goFormatString(int integer) {
        if (integer < 1000) return "" + integer;
        String ret = integer / 1000 + ",";
        int z = integer % 1000;
        ret += z < 10 ? "00" + z : z < 100 ? "0" + z : "" + z;
        return ret;
    }

}
