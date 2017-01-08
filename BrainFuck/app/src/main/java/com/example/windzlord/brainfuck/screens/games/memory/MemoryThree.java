package com.example.windzlord.brainfuck.screens.games.memory;


import android.os.Bundle;
import android.os.CountDownTimer;
import android.support.annotation.DrawableRes;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.ScaleAnimation;
import android.view.animation.TranslateAnimation;
import android.widget.ImageView;

import com.example.windzlord.brainfuck.R;
import com.example.windzlord.brainfuck.adapters.AnimationAdapter;
import com.example.windzlord.brainfuck.adapters.CountDownTimerAdapter;
import com.example.windzlord.brainfuck.managers.Gogo;
import com.example.windzlord.brainfuck.screens.games.NeikopzGame;

import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 */
public class MemoryThree extends NeikopzGame {

    private int srcHided = R.drawable.game_iz_resouce_0x;
    private int bgrNormal = R.drawable.custom_corner_background_outline;
    private int bgrChosen = R.drawable.custom_corner_background_outline_chosen;

    @DrawableRes
    int imageResources[] = {R.drawable.game_iz_resouce_13, R.drawable.game_iz_resouce_23,
            R.drawable.game_iz_resouce_17, R.drawable.game_iz_resouce_22,
            R.drawable.game_iz_resouce_21, R.drawable.game_iz_resouce_24,
            R.drawable.game_iz_resouce_18, R.drawable.game_iz_resouce_16,
            R.drawable.game_iz_resouce_20, R.drawable.game_iz_resouce_25};

    private int size = 20;
    private boolean[] isChosen = new boolean[size];
    private List<Integer> coreArray;
    private int matched;

    protected final int NUMBER_QUIZ = 1;

    public MemoryThree() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return createView(inflater.inflate(R.layout.game_memory_three, container, false));
    }

    protected void startGame() {
        RATE = 24;
        TIME = TIME * RATE;
        gameStatusLayout.updateValues(100, TIME, TIME, 0, NUMBER_QUIZ, 0);
        going = score = 0;

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
        matched = 0;
        for (int i = 0; i < size; i++) isChosen[i] = false;
        new CountDownTimerAdapter(500, 1) {
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
                coreArray = Gogo.getArrayMemoryThree();
                for (int i = 0; i < size; i++)
                    ((ImageView) layoutGame.getChildAt(i)).setImageResource(srcHided);
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
                        gameStatusLayout.setTimeCount((int) (millisUntilFinished / (50 * RATE)));
                    }

                    @Override
                    public void onFinish() {
                        goNext(false);
                    }
                }.start();
                showQuiz();
            }
        });
        if (going >= NUMBER_QUIZ) goEndGame(Gogo.MEMORY, 3);
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
                flip(one, isChosen[one]);
                isChosen[one] ^= 1 + 1 == 2;

                boolean someoneWasChosen = true;
                for (boolean e : isChosen) someoneWasChosen ^= e;
                if (someoneWasChosen) {
                    boolean match = false;
                    int two = -1;
                    for (two = 0; two < size; two++) {
                        if (two == one) continue;
                        if (isChosen[two]) match = coreArray.get(one) == coreArray.get(two);
                        if (match) break;
                    }
                    clickable = false;
                    if (match) {
                        matched++;
                        layoutGame.getChildAt(one).setOnClickListener(null);
                        layoutGame.getChildAt(two).setOnClickListener(null);
                        int zoo = two;
                        new CountDownTimerAdapter(500, 1) {
                            public void onFinish() {
                                clickable = true;
                                layoutGame.getChildAt(one).setVisibility(View.INVISIBLE);
                                layoutGame.getChildAt(zoo).setVisibility(View.INVISIBLE);
                            }
                        }.start();
                        if (matched == 10) {
                            goNext(true);
                        }
                    } else new CountDownTimerAdapter(500, 1) {
                        public void onFinish() {
                            clickable = true;
                            for (int e = 0; e < size; e++) {
                                isChosen[e] = false;
                                flip(e, true);
                            }
                        }
                    }.start();
                }
            });
        }
    }

    private void flip(int position, boolean goHide) {
        if (goHide) {
            layoutGame.getChildAt(position).setBackgroundResource(bgrNormal);
            ((ImageView) layoutGame.getChildAt(position)).setImageResource(srcHided);
        } else {
            layoutGame.getChildAt(position).setBackgroundResource(bgrChosen);
            ((ImageView) layoutGame.getChildAt(position)).setImageResource(
                    imageResources[coreArray.get(position)]);
        }
    }

    @Override
    protected void nextQuiz(boolean completed) {
        new CountDownTimerAdapter(1000, 1) {
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
    protected void goHighScoreColor() {
        imageViewScore.setImageResource(R.color.colorOrangeLight);
        textViewScore.setTextColor(getResources().getColor(R.color.colorWhite));
    }
}
