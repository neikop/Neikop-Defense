package com.example.windzlord.brainfuck.adapters;

import android.os.CountDownTimer;

/**
 * Created by WindzLord on 12/30/2016.
 */

public abstract class CountDownTimerAdapter extends CountDownTimer {
    /**
     * @param millisInFuture    The number of millis in the future from the call
     *                          to {@link #start()} until the countdown is done and {@link #onFinish()}
     *                          is called.
     */
    public CountDownTimerAdapter(long millisInFuture) {
        super(millisInFuture, millisInFuture);
    }

    @Override
    public void onTick(long millisUntilFinished) {

    }
}
