package com.example.windzlord.z_lab2_music.adapters;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

import com.example.windzlord.z_lab2_music.screens.*;

/**
 * Created by WindzLord on 11/28/2016.
 */

public class PagerAdapter extends FragmentStatePagerAdapter {

    private int numberOfTab;

    public PagerAdapter(FragmentManager manager, int numberOfTab) {
        super(manager);
        this.numberOfTab = numberOfTab;
    }

    @Override
    public Fragment getItem(int position) {
        switch (position) {
            case 0:
                return new GenresPagerFragment();
            case 1:
                return new PlaylistFragment();
            case 2:
                return new OfflineFragment();
            default:
                return null;
        }
    }

    @Override
    public int getCount() {
        return numberOfTab;
    }
}