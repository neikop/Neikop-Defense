package com.example.windzlord.brainfuck.managers;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Random;

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

    public static int getRandom(int max) {
        return new Random().nextInt(max);
    }

    public static int[] getArrayMemoryOne(int max) {
        List<Integer> list = new ArrayList<>();
        for (int i = 0; i < max; i++) {
            list.add(i);
        }
        Collections.shuffle(list);
        int ret[] = new int[8];
        for (int i = 0; i < 6; i++) {
            ret[i] = list.get(i);
        }
        while (ret[6] == ret[7]) {
            ret[6] = ret[new Random().nextInt(6)];
            ret[7] = ret[new Random().nextInt(6)];
        }
        Collections.shuffle(Arrays.asList(ret));
        return ret;
    }
}
