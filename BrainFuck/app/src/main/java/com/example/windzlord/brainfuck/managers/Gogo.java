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

    public static final int NUMBER_QUIZ = 5;
    public static final int TIME = 5000;

    public final static String MEMORY = "Memory";
    public final static String CALCULATION = "Calculation";
    public final static String CONCENTRATION = "Concentration";
    public final static String OBSERVATION = "Observation";

    public final static String[] GAME_LIST = {MEMORY, CALCULATION, CONCENTRATION, OBSERVATION};

    public static String goFormatString(int integer) {
        if (integer < 1000) return "" + integer;
        String ret = integer / 1000 + ",";
        int z = integer % 1000;
        ret += z < 10 ? "00" + z : z < 100 ? "0" + z : "" + z;
        return ret;
    }

    public static int getRandom(int max) {
        return new Random().nextInt(max);
    }

    public static boolean[] getArrayObserOne(boolean[] core) {
        boolean[] ret = new boolean[9];
        for (int i = 0; i < 9; i++) ret[i] = getRandom(10) < 5;
        if (core == null) return ret;
        while (true) {
            for (int i = 0; i < 9; i++) {
                if (ret[i] ^ core[i]) return ret;
            }
            ret = getArrayObserOne(null);
        }
    }

    public static int[] getArrayObserTwo(int size) {
        List<Integer> list = new ArrayList<>();
        for (int i = 0; i < size; i++) {
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

    public static boolean[] getArrayObserThree() {
        boolean[] ret = new boolean[16];
        int count = 0;
        for (int i = 0; i < 16; i++) {
            ret[i] = getRandom(10) < 5;
            if (ret[i]) count++;
        }
        if (count >= 6 & count <= 10) return ret;
        else return getArrayObserThree();
    }

    public static int[] getArrayObserThreeAnswer(int core) {
        switch (getRandom(4)) {
            case 1:
                return new int[]{core, core + 1, core + 2, core + 3, 0};
            case 2:
                return new int[]{core - 1, core, core + 1, core + 2, 1};
            case 3:
                return new int[]{core - 2, core - 1, core, core + 1, 2};
            default:
                return new int[]{core - 3, core - 2, core - 1, core, 3};
        }
    }

    public static List<Integer> getArrayMemoryOne() {
        List<Integer> ret = new ArrayList<>();
        for (int i = 0; i < 12; i++) ret.add(i);
        Collections.shuffle(ret);
        switch (Gogo.getRandom(3)) {
            case 1:
                return ret.subList(0, 4);
            case 2:
                return ret.subList(0, 5);
            default:
                return ret.subList(0, 6);
        }
    }

    public static boolean[] getArrayMemoryTwo() {
        boolean[] ret = new boolean[16];
        int count = 0;
        for (int i = 0; i < 16; i++) {
            ret[i] = getRandom(10) < 5;
            if (ret[i]) count++;
        }
        if (count >= 6 & count <= 10) return ret;
        else return getArrayMemoryTwo();
    }

    public static List<Integer> getArrayMemoryThree() {
        List<Integer> ret = new ArrayList<>();
        for (int i = 0; i < 20; i++) ret.add(i / 2);
        Collections.shuffle(ret);
        return ret;
    }
}
