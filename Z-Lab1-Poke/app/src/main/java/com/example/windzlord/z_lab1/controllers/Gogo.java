package com.example.windzlord.z_lab1.controllers;

import android.content.Intent;
import android.content.res.AssetManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.graphics.Typeface;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.Random;

/**
 * Created by WindzLord on 11/17/2016.
 */

public class Gogo {

    public static void goSetFontTextView(FragmentActivity activity, TextView textView, String font) {
        textView.setTypeface(
                Typeface.createFromAsset(activity.getAssets(), font)
        );
    }

    public static void goSetBackgroundView(FragmentActivity fragment, View view, int id) {
        view.setBackground(fragment.getResources().getDrawable(id));
    }

    public static Bitmap getBitmap(FragmentActivity activity, String fileName) {
        try {
            return BitmapFactory.decodeStream(activity.getAssets().open(fileName));
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    public static void changeColor(Bitmap myBitmap) {
        int[] allPixels = new int[myBitmap.getHeight() * myBitmap.getWidth()];
        myBitmap.getPixels(allPixels, 0, myBitmap.getWidth(), 0, 0, myBitmap.getWidth(), myBitmap.getHeight());
        for (int i = 0; i < allPixels.length; i++)
            if (allPixels[i] != Color.TRANSPARENT)
                allPixels[i] = Color.BLACK;
        myBitmap.setPixels(allPixels, 0, myBitmap.getWidth(), 0, 0, myBitmap.getWidth(), myBitmap.getHeight());
    }

    public static int getRandom(int max) {
        return new Random().nextInt(max);
    }

    public static int[] getRandoms(int max, int base) {
        int[] ret = new int[3];
        while (ret[0] == ret[1] | ret[0] == ret[2] | ret[1] == ret[2]
                | ret[0] == base | ret[1] == base | ret[2] == base) {
            ret[0] = getRandom(max);
            ret[1] = getRandom(max);
            ret[2] = getRandom(max);
        }
        return ret;
    }

    public static int[] getRandomArray(int size) {
        int[] ret = new int[size];
        ArrayList<Integer> integers = new ArrayList<>();
        for (int i = 0; i < size; i++) integers.add(i);
        Collections.shuffle(integers);
        for (int i = 0; i < size; i++) ret[i] = integers.get(i);
        return ret;
    }
}
