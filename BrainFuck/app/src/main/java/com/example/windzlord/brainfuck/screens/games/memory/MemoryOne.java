package com.example.windzlord.brainfuck.screens.games.memory;


import android.os.Bundle;
import android.os.CountDownTimer;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.ScaleAnimation;
import android.widget.ImageView;

import com.example.windzlord.brainfuck.R;
import com.example.windzlord.brainfuck.adapters.AnimationAdapter;
import com.example.windzlord.brainfuck.adapters.CountDownTimerAdapter;
import com.example.windzlord.brainfuck.managers.ManagerBrain;
import com.example.windzlord.brainfuck.screens.games.GameDaddy;

import java.util.Random;

/**
 * A simple {@link Fragment} subclass.
 */
public class MemoryOne extends GameDaddy {

    private int bgrChosen = R.drawable.custom_corner_background_7_outline;
    private int bgrNormal = R.drawable.custom_corner_background_5_outline;

    private int size = 16;
    private boolean[] isChosen = new boolean[size];
    private boolean[] coreArray;

    public MemoryOne() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return createView(inflater.inflate(R.layout.game_memory_one, container, false));
    }

    @Override
    protected void goPrepare() {
        ScaleAnimation scaleOne = new ScaleAnimation(1, 0, 1, 0, 1, 0.5f, 1, 0.5f);
        scaleOne.setDuration(250);
        ScaleAnimation scaleTwo = new ScaleAnimation(0, 1, 0, 1, 1, 0.5f, 1, 0.5f);
        scaleTwo.setDuration(250);
        scaleOne.setAnimationListener(new AnimationAdapter() {
            public void onAnimationEnd(Animation animation) {
                for (View view : goChildGroup(layoutGame)) {
                    ((ImageView) view).setImageResource(R.color.colorFaded);
                    view.setBackgroundResource(bgrNormal);
                }
                for (View view : goChildGroup(layoutGame))
                    view.startAnimation(scaleTwo);
            }
        });
        scaleTwo.setAnimationListener(new AnimationAdapter() {
            public void onAnimationEnd(Animation animation) {
                prepareQuiz();
            }
        });
        for (View view : goChildGroup(layoutGame)) view.startAnimation(scaleOne);
    }

    @Override
    protected void prepareQuiz() {
        for (int i = 0; i < size; i++) isChosen[i] = false;
        new CountDownTimerAdapter(500) {
            public void onFinish() {
                goShow();
            }
        }.start();
    }

    @Override
    protected void goShow() {
        ScaleAnimation scaleOne = new ScaleAnimation(1, 0, 1, 0, 1, 0.5f, 1, 0.5f);
        scaleOne.setDuration(250);
        ScaleAnimation scaleTwo = new ScaleAnimation(0, 1, 0, 1, 1, 0.5f, 1, 0.5f);
        scaleTwo.setDuration(250);
        ScaleAnimation scaleThree = new ScaleAnimation(1, 0, 1, 0, 1, 0.5f, 1, 0.5f);
        scaleThree.setDuration(250);
        ScaleAnimation scaleFour = new ScaleAnimation(0, 1, 0, 1, 1, 0.5f, 1, 0.5f);
        scaleFour.setDuration(250);
        scaleOne.setAnimationListener(new AnimationAdapter() {
            public void onAnimationStart(Animation animation) {
                onShowing = true;
            }

            public void onAnimationEnd(Animation animation) {
                coreArray = getCoreArray();
                for (int i = 0; i < size; i++)
                    layoutGame.getChildAt(i).setBackgroundResource(coreArray[i] ?
                            bgrChosen : bgrNormal);
                for (View view : goChildGroup(layoutGame)) view.startAnimation(scaleTwo);
            }
        });
        scaleTwo.setAnimationListener(new AnimationAdapter() {
            public void onAnimationEnd(Animation animation) {
                new CountDownTimerAdapter(1000) {
                    public void onFinish() {
                        for (View view : goChildGroup(layoutGame)) view.startAnimation(scaleThree);
                    }
                }.start();
            }
        });
        scaleThree.setAnimationListener(new AnimationAdapter() {
            public void onAnimationEnd(Animation animation) {
                for (int i = 0; i < size; i++)
                    layoutGame.getChildAt(i).setBackgroundResource(bgrNormal);
                for (View view : goChildGroup(layoutGame)) view.startAnimation(scaleFour);

            }
        });
        scaleFour.setAnimationListener(new AnimationAdapter() {
            public void onAnimationEnd(Animation animation) {
                onShowing = false;
                clickable = true;
                counter = new CountDownTimer(TIME, 1) {
                    @Override
                    public void onTick(long millisUntilFinished) {
                        remain = millisUntilFinished;
                        gameStatusLayout.setTimeProgress((int) millisUntilFinished);
                        gameStatusLayout.setTimeCount((int) (millisUntilFinished / 50));
                    }

                    @Override
                    public void onFinish() {
                        goNext(false);
                    }
                }.start();
                showQuiz();
            }
        });
        if (going >= QUIZ) goEndGame(ManagerBrain.MEMORY, 1);
        else for (View view : goChildGroup(layoutGame)) view.startAnimation(scaleOne);
    }

    @Override
    protected void showQuiz() {
        going++;
        gameStatusLayout.setGoingCount(going);
        gameStatusLayout.setGoingProgress(going);
        for (int i = 0; i < size; i++) {
            int one = i;
            layoutGame.getChildAt(one).setOnClickListener(v -> {
                if (!clickable) return;
                if (isChosen[one]) return;
                isChosen[one] = true;
                layoutGame.getChildAt(one).setBackgroundResource(bgrChosen);
                if (!coreArray[one]) {
                    goNext(false);
                } else {
                    boolean ok = true;
                    for (int c = 0; c < size; c++)
                        if (isChosen[c] != coreArray[c]) {
                            ok = false;
                            break;
                        }
                    if (ok) goNext(true);
                }
            });
        }
    }

    private boolean[] getCoreArray() {
        boolean[] ret = new boolean[16];
        int count = 0;
        for (int i = 0; i < 16; i++) if (ret[i] = new Random().nextBoolean()) count++;
        if (count < 6 | count > 10) return getCoreArray();
        return ret;
    }

}
