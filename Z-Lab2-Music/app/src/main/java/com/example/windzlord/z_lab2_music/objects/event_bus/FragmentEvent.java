package com.example.windzlord.z_lab2_music.objects.event_bus;

import android.support.v4.app.Fragment;

/**
 * Created by WindzLord on 11/16/2016.
 */

public class FragmentEvent {

    private String className;
    private Fragment fragment;
    private boolean addToBackStack;

    public FragmentEvent(String className, Fragment fragment, boolean addToBackStack) {
        this.className = className;
        this.fragment = fragment;
        this.addToBackStack = addToBackStack;
    }

    public String getClassName() {
        return className;
    }

    public Fragment getFragment() {
        return fragment;
    }

    public boolean isAddToBackStack() {
        return addToBackStack;
    }
}
