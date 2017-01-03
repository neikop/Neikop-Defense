package com.example.windzlord.brainfuck.screens.games.observation;


import android.os.Bundle;
import android.os.CountDownTimer;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.view.animation.AnimationSet;
import android.view.animation.ScaleAnimation;
import android.view.animation.TranslateAnimation;
import android.widget.ImageView;

import com.example.windzlord.brainfuck.R;
import com.example.windzlord.brainfuck.adapters.AnimationAdapter;
import com.example.windzlord.brainfuck.adapters.CountDownTimerAdapter;
import com.example.windzlord.brainfuck.managers.Gogo;
import com.example.windzlord.brainfuck.screens.games.NeikopzGame;

import butterknife.BindView;

/**
 * A simple {@link Fragment} subclass.
 */
public class ObserverOne extends NeikopzGame {

    @BindView(R.id.layout_game_x)
    ViewGroup groupX;

    @BindView(R.id.layout_game_y)
    ViewGroup groupY;

    @BindView(R.id.layout_game_z)
    ViewGroup groupZ;

    private boolean[] coreArray;
    private boolean[] fakeArray;

    public ObserverOne() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return createView(inflater.inflate(R.layout.game_observer_one, container, false));
    }

    @Override
    protected void goPrepare() {
        TranslateAnimation goRight = new TranslateAnimation(1, 0, 1, 1, 1, 0, 1, 0);
        TranslateAnimation goLeft = new TranslateAnimation(1, 0, 1, -1, 1, 0, 1, 0);
        AlphaAnimation goHide = new AlphaAnimation(1, 0);
        goRight.setDuration(500);
        goLeft.setDuration(500);
        goHide.setDuration(500);
        AnimationSet setX = new AnimationSet(true);
        setX.addAnimation(goRight);
        setX.addAnimation(goHide);
        AnimationSet setY = new AnimationSet(true);
        setY.addAnimation(goLeft);
        setY.addAnimation(goHide);
        setX.setAnimationListener(new AnimationAdapter() {
            public void onAnimationEnd(Animation animation) {
                goVisibility(View.INVISIBLE, groupX, groupZ);
                for (View view : goChildGroup(groupY))
                    goImageResou(view, R.color.colorWhite);
                prepareQuiz();
            }
        });
        new CountDownTimerAdapter(200, 1) {
            public void onFinish() {
                groupX.startAnimation(setX);
                groupZ.startAnimation(setY);
            }
        }.start();
    }

    @Override
    protected void prepareQuiz() {
        new CountDownTimerAdapter(500, 1) {
            public void onFinish() {
                goShow();
            }
        }.start();
    }

    @Override
    protected void goShow() {
        coreArray = Gogo.getArrayObserOne(null);
        fakeArray = Gogo.getArrayObserOne(coreArray);
        ScaleAnimation scaleOne = new ScaleAnimation(1, 0, 1, 0, 1, 0.5f, 1, 0.5f);
        scaleOne.setDuration(250);
        ScaleAnimation scaleTwo = new ScaleAnimation(0, 1, 0, 1, 1, 0.5f, 1, 0.5f);
        scaleTwo.setDuration(250);
        scaleOne.setAnimationListener(new AnimationAdapter() {
            public void onAnimationStart(Animation animation) {
                onShowing = true;
            }

            public void onAnimationEnd(Animation animation) {
                for (int i = 0; i < 9; i++)
                    if (coreArray[i]) goImageResou(goChildGroup(groupY).get(i), R.color.colorWhite);
                    else goImageResou(goChildGroup(groupY).get(i), R.color.colorBlackLight);
                goStartAnimation(scaleTwo, goChildGroup(groupY));
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
                        goClick(false);
                    }
                }.start();
                going++;
                gameStatusLayout.setGoingCount(going);
                gameStatusLayout.setGoingProgress(going);
            }
        });
        goStartAnimation(scaleOne, goChildGroup(groupY));
        if (going >= NUMBER_QUIZ) goEndGame(Gogo.OBSERVATION, 1);
        showQuiz();
    }

    @Override
    protected void showQuiz() {
        goVisibility(View.VISIBLE, groupX, groupZ);
        boolean xxx = Gogo.getRandom(2) == 0;
        for (int i = 0; i < 9; i++)
            if (xxx) {
                ((ImageView) groupX.getChildAt(i)).setImageResource(coreArray[i] ?
                        R.color.colorWhite : R.color.colorBlackLight);
                ((ImageView) groupZ.getChildAt(i)).setImageResource(fakeArray[i] ?
                        R.color.colorWhite : R.color.colorBlackLight);
            } else {
                ((ImageView) groupZ.getChildAt(i)).setImageResource(coreArray[i] ?
                        R.color.colorWhite : R.color.colorBlackLight);
                ((ImageView) groupX.getChildAt(i)).setImageResource(fakeArray[i] ?
                        R.color.colorWhite : R.color.colorBlackLight);
            }
        groupX.setOnClickListener(v -> goClick(xxx));
        groupZ.setOnClickListener(v -> goClick(!xxx));

        TranslateAnimation goRight = new TranslateAnimation(1, -1, 1, 0, 1, 0, 1, 0);
        TranslateAnimation goLeft = new TranslateAnimation(1, 1, 1, 0, 1, 0, 1, 0);
        AlphaAnimation goShow = new AlphaAnimation(0, 1);
        goRight.setDuration(500);
        goLeft.setDuration(500);
        goShow.setDuration(500);
        AnimationSet setX = new AnimationSet(true);
        setX.addAnimation(goLeft);
        setX.addAnimation(goShow);
        AnimationSet setY = new AnimationSet(true);
        setY.addAnimation(goRight);
        setY.addAnimation(goShow);
        groupX.startAnimation(setX);
        groupZ.startAnimation(setY);
    }

    private void goClick(boolean completed) {
        if (!clickable) return;
        goNext(completed);
    }

}
