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

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Random;
import java.util.Stack;

/**
 * A simple {@link Fragment} subclass.
 */
public class MemoryTwo extends GameDaddy {

    private int bgrChosen = R.drawable.custom_corner_background_7_outline;
    private int bgrNormal = R.drawable.custom_corner_background_5_outline;

    private int size = 12;
    private boolean[] isChosen = new boolean[size];
    private List<Integer> coreArray;

    public MemoryTwo() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return createView(inflater.inflate(R.layout.game_memory_two, container, false));
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
        coreArray = getCoreArray();
        Stack<View> stack = new Stack<>();
        for (int i = coreArray.size() - 1; i >= 0; i--)
            stack.push(layoutGame.getChildAt(coreArray.get(i)));
        if (going >= QUIZ) goEndGame(ManagerBrain.MEMORY, 2);
        else goNext(stack);
    }

    private void goNext(Stack<View> views) {
        View view = views.pop();
        ScaleAnimation scaleOne = new ScaleAnimation(1, 0, 1, 0, 1, 0.5f, 1, 0.5f);
        scaleOne.setDuration(200);
        ScaleAnimation scaleTwo = new ScaleAnimation(0, 1, 0, 1, 1, 0.5f, 1, 0.5f);
        scaleTwo.setDuration(200);
        scaleOne.setAnimationListener(new AnimationAdapter() {
            public void onAnimationEnd(Animation animation) {
                view.setBackgroundResource(bgrChosen);
                view.startAnimation(scaleTwo);
            }
        });
        scaleTwo.setAnimationListener(new AnimationAdapter() {
            public void onAnimationEnd(Animation animation) {
                if (views.isEmpty()) {
                    new CountDownTimerAdapter(1000) {
                        public void onFinish() {
                            goShow2();
                        }
                    }.start();
                } else goNext((views));
            }
        });
        view.setVisibility(View.VISIBLE);
        view.startAnimation(scaleOne);
    }

    private void goShow2() {
        ScaleAnimation scaleOne = new ScaleAnimation(1, 0, 1, 0, 1, 0.5f, 1, 0.5f);
        scaleOne.setDuration(200);
        ScaleAnimation scaleTwo = new ScaleAnimation(0, 1, 0, 1, 1, 0.5f, 1, 0.5f);
        scaleTwo.setDuration(200);
        scaleOne.setAnimationListener(new AnimationAdapter() {
            public void onAnimationStart(Animation animation) {
                onShowing = true;
            }

            public void onAnimationEnd(Animation animation) {
                for (View view : goChildGroup(layoutGame)) {
                    view.setBackgroundResource(bgrNormal);
                    view.startAnimation(scaleTwo);
                }
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
        goStartAnimation(scaleOne, goChildGroup(layoutGame));
    }

    @Override
    protected void showQuiz() {
        going++;
        gameStatusLayout.setGoingCount(going);
        gameStatusLayout.setGoingProgress(going);
        Stack<Integer> clicks = new Stack<>();
        for (int i = 0; i < size; i++) {
            int one = i;
            layoutGame.getChildAt(one).setOnClickListener(v -> {
                if (!clickable) return;
                if (isChosen[one]) return;
                isChosen[one] = true;
                layoutGame.getChildAt(one).setBackgroundResource(bgrChosen);

                clicks.push(one);
                if (one != coreArray.get(clicks.size() - 1)) goNext(false);
                else if (clicks.size() == coreArray.size()) goNext(true);
            });
        }
    }

    private List<Integer> getCoreArray() {
        List<Integer> ret = new ArrayList<>();
        for (int i = 0; i < 12; i++) ret.add(i);
        Collections.shuffle(ret);
        if (new Random().nextBoolean()) return ret.subList(0, 5);
        return ret.subList(0, 6);
    }

}
