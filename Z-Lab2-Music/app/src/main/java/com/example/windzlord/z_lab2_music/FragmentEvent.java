package com.example.windzlord.z_lab2_music;

import android.support.v4.app.Fragment;

/**
 * Created by WindzLord on 11/16/2016.
 */

public class FragmentEvent {

    private Fragment fragment;
    private boolean addToBackStack;

    public FragmentEvent(Fragment fragment, boolean addToBackStack) {
        this.fragment = fragment;
        this.addToBackStack = addToBackStack;
    }

    public Fragment getFragment() {
        return fragment;
    }

    public boolean isAddToBackStack() {
        return addToBackStack;
    }
}
