package com.example.windzlord.brainfuck.screens.games.concentration;


import android.os.Bundle;
import android.os.CountDownTimer;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.RotateAnimation;
import android.view.animation.ScaleAnimation;
import android.widget.ImageView;

import com.example.windzlord.brainfuck.R;
import com.example.windzlord.brainfuck.adapters.AnimationAdapter;
import com.example.windzlord.brainfuck.adapters.CountDownTimerAdapter;
import com.example.windzlord.brainfuck.managers.Gogo;
import com.example.windzlord.brainfuck.screens.games.NeikopzGame;

import java.util.ArrayList;
import java.util.Arrays;

/**
 * A simple {@link Fragment} subclass.
 */
public class ConcenTwo extends NeikopzGame {


    private static final String TAG = ConcenTwo.class.getSimpleName();
    private int bgrChosen = R.drawable.custom_corner_background_outline_game;
    private int bgrNormal = R.drawable.custom_corner_background_outline;
    private boolean[] isChosen = new boolean[12];
    private boolean[] coreArray;
    private View[] views = new View[12];

    public ConcenTwo() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return createView(inflater.inflate(R.layout.game_concen_two, container, false));
    }

    @Override
    protected void startGame() {
        super.startGame();
        for (int i = 0; i < 12; i++) views[i] = layoutGame.getChildAt(i);

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
        for (View view : goChildGroup(layoutGame))
            if (view.getVisibility() == View.VISIBLE) view.startAnimation(scaleOne);
    }

    @Override
    protected void prepareQuiz() {
        layoutGame.clearAnimation();
        for (int i = 0; i < 12; i++) isChosen[i] = false;
        new CountDownTimerAdapter(1000, 1) {
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
                coreArray = Gogo.getArrayConcenTwo();
                for (int i = 0; i < 12; i++)
                    views[i].setBackgroundResource(coreArray[i] ? bgrChosen : bgrNormal);
                for (View view : goChildGroup(layoutGame)) view.startAnimation(scaleTwo);
            }
        });
        scaleTwo.setAnimationListener(new AnimationAdapter() {
            public void onAnimationEnd(Animation animation) {
                new CountDownTimerAdapter(1500, 1) {
                    public void onFinish() {
                        for (View view : goChildGroup(layoutGame)) view.startAnimation(scaleThree);
                    }
                }.start();
            }
        });
        scaleThree.setAnimationListener(new AnimationAdapter() {
            public void onAnimationEnd(Animation animation) {
                for (int i = 0; i < 12; i++) views[i].setBackgroundResource(bgrNormal);
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
        if (going >= NUMBER_QUIZ) goEndGame(Gogo.CONCENTRATION, 2);
        else for (View view : goChildGroup(layoutGame)) view.startAnimation(scaleOne);
    }

    @Override
    protected void showQuiz() {
        going++;
        gameStatusLayout.setGoingCount(going);
        gameStatusLayout.setGoingProgress(going);
        for (int i = 0; i < 12; i++) {
            int one = i;
            views[one].setOnClickListener(v -> {
                if (!clickable) return;
                if (!isChosen[one]) {
                    views[one].setBackgroundResource(bgrChosen);
                    isChosen[one] = true;

                    if (!coreArray[one]) {
                        goNext(false);
                    } else {
                        boolean check = true;
                        for (int c = 0; c < 12; c++) {
                            if (isChosen[c] != coreArray[c]) {
                                check = false;
                                break;
                            }
                        }
                        if (check) {
                            goNext(true);
                        }
                    }
                }

            });
        }
    }

    @Override
    protected void goHighScoreColor() {
        imageViewScore.setImageResource(R.color.colorOrangeLight);
        textViewScore.setTextColor(getResources().getColor(R.color.colorWhite));
    }

}
