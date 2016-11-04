package com.example.windzlord.x_lab6.models;


import android.support.v4.app.Fragment;

/**
 * Created by WindzLord on 10/12/2016.
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
