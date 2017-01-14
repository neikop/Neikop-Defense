package com.example.windzlord.brainfuck.screens.games.calculation;


import android.os.Bundle;
import android.os.CountDownTimer;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.ScaleAnimation;
import android.view.animation.TranslateAnimation;
import android.widget.Button;

import com.example.windzlord.brainfuck.R;
import com.example.windzlord.brainfuck.adapters.AnimationAdapter;
import com.example.windzlord.brainfuck.adapters.CountDownTimerAdapter;
import com.example.windzlord.brainfuck.managers.ManagerBrain;
import com.example.windzlord.brainfuck.screens.games.GameDaddy;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/**
 * A simple {@link Fragment} subclass.
 */
public class CalcuThree extends GameDaddy {

    private List<Button> Button = new ArrayList<>();

    private int[][] core = new int[][]{{},
            {2, 5}, {1, 3, 6}, {2, 4, 7}, {3, 8},
            {1, 6, 9}, {2, 5, 7, 10}, {3, 6, 8, 11}, {4, 7, 12},
            {5, 10, 13}, {6, 9, 11, 14}, {7, 10, 12, 15}, {8, 11, 16},
            {9, 14}, {10, 13, 15}, {11, 14, 16}, {12, 15}};

    private boolean active = false;
    private int marked;

    public CalcuThree() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return createView(inflater.inflate(R.layout.game_calcu_three, container, false));
    }

    protected void startGame() {
        RATE = 12;
        TIME = TIME * RATE;
        QUIZ = 1;
        gameStatusLayout.updateValues(100, TIME, TIME, 0, QUIZ, 0);
        going = score = 0;

        Button.add(null);
        for (View view : goChildGroup(layoutGame)) Button.add((Button) view);

        goStartAnimation();
    }

    @Override
    protected void goPrepare() {
        ScaleAnimation scaleOne = new ScaleAnimation(1, 0, 1, 0, 1, 0.5f, 1, 0.5f);
        scaleOne.setDuration(250);
        ScaleAnimation scaleTwo = new ScaleAnimation(0, 1, 0, 1, 1, 0.5f, 1, 0.5f);
        scaleTwo.setDuration(250);
        scaleOne.setAnimationListener(new AnimationAdapter() {
            public void onAnimationEnd(Animation animation) {
                for (int i = 0; i < goChildGroup(layoutGame).size(); i++) {
                    Button button = ((Button) goChildGroup(layoutGame).get(i));
                    button.setVisibility(View.VISIBLE);
                    button.setText("" + (i + 1));
                    if (i == 15) button.setVisibility(View.INVISIBLE);
                    else button.startAnimation(scaleTwo);
                }
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
                showQuiz();
                for (View view : goChildGroup(layoutGame))
                    if (view.getVisibility() == View.VISIBLE) view.startAnimation(scaleTwo);
            }
        });
        scaleTwo.setAnimationListener(new AnimationAdapter() {
            public void onAnimationEnd(Animation animation) {
                onShowing = false;
                clickable = true;
                active = true;
                counter = new CountDownTimer(TIME, 1) {
                    @Override
                    public void onTick(long millisUntilFinished) {
                        remain = millisUntilFinished;
                        gameStatusLayout.setTimeProgress((int) millisUntilFinished);
                        gameStatusLayout.setTimeCount((int) (millisUntilFinished / (50 * RATE)));
                    }

                    @Override
                    public void onFinish() {
                        goNext(false);
                    }
                }.start();
                going++;
                gameStatusLayout.setGoingCount(going);
                gameStatusLayout.setGoingProgress(going);
            }
        });
        if (going >= QUIZ) goEndGame(ManagerBrain.CALCULATION, 3);
        else new CountDownTimerAdapter(500) {
            public void onFinish() {
                for (View view : goChildGroup(layoutGame))
                    if (view.getVisibility() == View.VISIBLE) view.startAnimation(scaleOne);
            }
        }.start();
    }

    @Override
    protected void showQuiz() {
        marked = 16;
        for (int i = 0; i < 100; i++)
            swapWithMarked(core[marked][new Random().nextInt(core[marked].length)]);
    }

    @Override
    protected void nextQuiz(boolean completed) {
        new CountDownTimerAdapter(1000) {
            public void onFinish() {
                int bonus = completed ? gameStatusLayout.getTimeCount() : 0;
                score += bonus;
                textViewBonus.setText("+" + bonus);
                textViewBonus.setVisibility(View.VISIBLE);
                TranslateAnimation goUp = new TranslateAnimation(
                        1, 0, 1, 0, 1, 0, 1, -1);
                goUp.setDuration(500);
                goUp.setAnimationListener(new AnimationAdapter() {
                    public void onAnimationEnd(Animation animation) {
                        textViewBonus.setVisibility(View.INVISIBLE);
                        textViewScore.setText("" + score);
                    }
                });
                textViewBonus.startAnimation(goUp);
                goPrepare();
            }
        }.start();
    }

    @Override
    protected void addListeners() {
        super.addListeners();
        for (int i = 1; i <= 16; i++) {
            int x = i;
            Button.get(i).setOnClickListener((v) -> goClickButton(x));
        }
    }

    private void goClickButton(int x) {
        boolean canSwap = false;
        for (int nearby : core[x]) if (nearby == marked) canSwap = true;
        if (canSwap) swapWithMarked(x);
    }

    private void swapWithMarked(int target) {
        Button.get(target).setVisibility(View.INVISIBLE);
        Button.get(marked).setVisibility(View.VISIBLE);
        Button.get(marked).setText(Button.get(target).getText());
        marked = target;
        if (checkWin()) goNext(true);
    }

    private boolean checkWin() {
        boolean ok = true;
        for (int i = 1; i < 16; i++) ok &= (Button.get(i).getText().toString().equals("" + i));
        return ok & active & Button.get(16).getVisibility() == View.INVISIBLE;
    }
}
