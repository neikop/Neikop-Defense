package com.example.windzlord.brainfuck.managers;

/**
 * Created by WindzLord on 12/27/2016.
 */

public class Gogo {

    public static String goFormatString(int integer) {
        if (integer < 1000) return "" + integer;
        String ret = integer / 1000 + ",";
        int z = integer % 1000;
        ret += z < 10 ? "00" + z : z < 100 ? "0" + z : "" + z;
        return ret;
    }

    public static String goFormatTime(int time) {
        int min = time / 60;
        int sec = time - min * 60;
        return "" + (min < 10 ? ("0" + min) : ("" + min))
                + ":" + (sec < 10 ? ("0" + sec) : ("" + sec));
    }
}
