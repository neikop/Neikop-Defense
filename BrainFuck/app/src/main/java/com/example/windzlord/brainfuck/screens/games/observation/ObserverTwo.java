package com.example.windzlord.brainfuck.screens.games.observation;


import android.os.Bundle;
import android.os.CountDownTimer;
import android.support.annotation.DrawableRes;
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

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Random;

/**
 * A simple {@link Fragment} subclass.
 */
public class ObserverTwo extends GameDaddy {

    private int srcHided = R.drawable.game_iz_resource_0x;
    private int bgrNormal = R.drawable.custom_corner_background_5_outline;
    private int bgrChosen = R.drawable.custom_corner_background_5_outline_chosen;

    @DrawableRes
    int imageResources[] = {R.drawable.game_iz_resource_10,
            R.drawable.game_iz_resource_11, R.drawable.game_iz_resource_12, R.drawable.game_iz_resource_13,
            R.drawable.game_iz_resource_14, R.drawable.game_iz_resource_15, R.drawable.game_iz_resource_16,
            R.drawable.game_iz_resource_17, R.drawable.game_iz_resource_18, R.drawable.game_iz_resource_19,
            R.drawable.game_iz_resource_20, R.drawable.game_iz_resource_21, R.drawable.game_iz_resource_22,
            R.drawable.game_iz_resource_23, R.drawable.game_iz_resource_24, R.drawable.game_iz_resource_25};

    private boolean isChosen[] = new boolean[8];
    private int[] coreArray;
    private int matched;

    public ObserverTwo() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return createView(inflater.inflate(R.layout.game_observer_two, container, false));
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
                    ((ImageView) view).setImageResource(srcHided);
                    view.setBackgroundResource(bgrNormal);
                    view.setVisibility(View.VISIBLE);
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
        for (View view : goChildGroup(layoutGame))
            if (view.getVisibility() == View.VISIBLE) view.startAnimation(scaleOne);
    }

    @Override
    protected void prepareQuiz() {
        matched = 0;
        for (int i = 0; i < 8; i++) isChosen[i] = false;
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
        scaleOne.setAnimationListener(new AnimationAdapter() {
            public void onAnimationStart(Animation animation) {
                onShowing = true;
            }

            public void onAnimationEnd(Animation animation) {
                coreArray = getCoreArray(imageResources.length);
                for (int i = 0; i < 8; i++)
                    ((ImageView) layoutGame.getChildAt(i)).setImageResource(imageResources[coreArray[i]]);
                for (View view : goChildGroup(layoutGame)) view.startAnimation(scaleTwo);
            }
        });
        scaleTwo.setAnimationListener(new AnimationAdapter() {
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
        if (going >= QUIZ) goEndGame(ManagerBrain.OBSERVATION, 2);
        else for (View view : goChildGroup(layoutGame)) view.startAnimation(scaleOne);
    }

    @Override
    protected void showQuiz() {
        going++;
        gameStatusLayout.setGoingCount(going);
        gameStatusLayout.setGoingProgress(going);
        for (int i = 0; i < 8; i++) {
            int one = i;
            layoutGame.getChildAt(one).setOnClickListener(v -> {
                if (!clickable) return;
                if (isChosen[one]) layoutGame.getChildAt(one).setBackgroundResource(bgrNormal);
                else layoutGame.getChildAt(one).setBackgroundResource(bgrChosen);
                isChosen[one] ^= 1 + 1 == 2;

                boolean someoneWasChosen = true;
                for (boolean e : isChosen) someoneWasChosen ^= e;
                if (someoneWasChosen) {
                    boolean match = false;
                    int two = -1;
                    for (two = 0; two < 8; two++) {
                        if (two == one) continue;
                        if (isChosen[two]) match = coreArray[one] == coreArray[two];
                        if (match) break;
                    }
                    if (match) {
                        matched++;
                        layoutGame.getChildAt(one).setOnClickListener(null);
                        layoutGame.getChildAt(two).setOnClickListener(null);
                        int zoo = two;
                        new CountDownTimerAdapter(300) {
                            public void onFinish() {
                                layoutGame.getChildAt(one).setVisibility(View.INVISIBLE);
                                layoutGame.getChildAt(zoo).setVisibility(View.INVISIBLE);
                            }
                        }.start();
                        if (matched == 2) {
                            goNext(true);
                        }
                    } else new CountDownTimerAdapter(300) {
                        public void onFinish() {
                            for (int e = 0; e < 8; e++) {
                                isChosen[e] = false;
                                layoutGame.getChildAt(e).setBackgroundResource(bgrNormal);
                            }
                        }
                    }.start();
                }
            });
        }
    }

    private int[] getCoreArray(int size) {
        List<Integer> number = new ArrayList<>();
        for (int i = 0; i < size; i++) number.add(i);
        Collections.shuffle(number);
        int ret[] = new int[8];
        for (int i = 0; i < 6; i++) ret[i] = number.get(i);
        while (ret[6] == ret[7]) {
            ret[6] = ret[new Random().nextInt(6)];
            ret[7] = ret[new Random().nextInt(6)];
        }
        Collections.shuffle(Arrays.asList(ret));
        return ret;
    }
}
