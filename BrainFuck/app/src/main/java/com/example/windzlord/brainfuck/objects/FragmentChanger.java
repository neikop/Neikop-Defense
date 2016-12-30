package com.example.windzlord.brainfuck.objects;

import android.support.v4.app.Fragment;

/**
 * Created by WindzLord on 11/16/2016.
 */

public class FragmentChanger {

    private Fragment fragment;
    private boolean addToBackStack;

    public FragmentChanger(Fragment fragment, boolean addToBackStack) {
        this.fragment = fragment;
        this.addToBackStack = addToBackStack;
    }

    public String getClassName() {
        return fragment.getClass().getSimpleName();
    }

    public Fragment getFragment() {
        return fragment;
    }

    public boolean isAddToBackStack() {
        return addToBackStack;
    }
}
