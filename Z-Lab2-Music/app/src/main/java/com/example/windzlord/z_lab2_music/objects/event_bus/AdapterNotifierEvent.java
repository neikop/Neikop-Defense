package com.example.windzlord.z_lab2_music.objects.event_bus;

/**
 * Created by WindzLord on 12/1/2016.
 */

public class AdapterNotifierEvent {

    private String className;
    private int checker;

    public AdapterNotifierEvent(String className, int checker) {
        this.className = className;
        this.checker = checker;
    }

    public String getClassName() {
        return className;
    }

    public int getChecker() {
        return checker;
    }
}
