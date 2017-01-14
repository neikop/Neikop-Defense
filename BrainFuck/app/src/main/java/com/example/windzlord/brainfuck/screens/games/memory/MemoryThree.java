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
import com.example.windzlord.brainfuck.managers.ManagerBrain;
import com.example.windzlord.brainfuck.screens.games.GameDaddy;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 */
public class MemoryThree extends GameDaddy {

    private int srcHided = R.drawable.game_iz_resource_0x;
    private int bgrNormal = R.drawable.custom_corner_background_5_outline;
    private int bgrChosen = R.drawable.custom_corner_background_5_outline_chosen;

    @DrawableRes
    int imageResources[] = {R.drawable.game_iz_resource_13, R.drawable.game_iz_resource_23,
            R.drawable.game_iz_resource_17, R.drawable.game_iz_resource_22,
            R.drawable.game_iz_resource_21, R.drawable.game_iz_resource_24,
            R.drawable.game_iz_resource_18, R.drawable.game_iz_resource_16,
            R.drawable.game_iz_resource_20, R.drawable.game_iz_resource_25};

    private int size = 20;
    private boolean[] isChosen = new boolean[size];
    private List<Integer> coreArray;
    private int matched;
    private int END_GAME;

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
        RATE = 12;
        TIME = TIME * RATE;
        QUIZ = 1;
        END_GAME = 10;
        gameStatusLayout.updateValues(100, TIME, TIME, 0, QUIZ, 0);
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
                    if (view.getVisibility() == View.VISIBLE)
                        view.startAnimation(scaleTwo);
            }
        });
        scaleTwo.setAnimationListener(new AnimationAdapter() {
            public void onAnimationEnd(Animation animation) {
                prepareQuiz();
            }
        });

        for (View view : goChildGroup(layoutGame))
            if (view.getVisibility() == View.VISIBLE) {
                for (View v : goChildGroup(layoutGame))
                    if (v.getVisibility() == View.VISIBLE) v.startAnimation(scaleOne);
                return;
            }
        prepareQuiz();
    }

    @Override
    protected void prepareQuiz() {
        matched = 0;
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
        scaleOne.setAnimationListener(new AnimationAdapter() {
            public void onAnimationStart(Animation animation) {
                onShowing = true;
            }

            public void onAnimationEnd(Animation animation) {
                showQuiz();
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
                going++;
                gameStatusLayout.setGoingCount(going);
                gameStatusLayout.setGoingProgress(going);
            }
        });
        if (going >= QUIZ) goEndGame(ManagerBrain.MEMORY, 3);
        else for (View view : goChildGroup(layoutGame)) view.startAnimation(scaleOne);
    }

    @Override
    protected void showQuiz() {
        coreArray = getCoreArray();
        for (int i = 0; i < size; i++)
            ((ImageView) layoutGame.getChildAt(i)).setImageResource(srcHided);
        for (int i = 0; i < size; i++) {
            int x = i;
            layoutGame.getChildAt(x).setOnClickListener(v -> {
                if (!clickable) return;
                flip(x, isChosen[x]);
                isChosen[x] ^= 1 + 1 == 2;
                if (!isChosen[x]) return;

                boolean someoneWasChosen = true;
                for (boolean e : isChosen) someoneWasChosen ^= e;
                if (someoneWasChosen) {
                    boolean match = false;
                    int y = -1;
                    for (y = 0; y < size; y++) {
                        if (y == x) continue;
                        if (isChosen[y]) match = coreArray.get(x) == coreArray.get(y);
                        if (match) break;
                    }
                    clickable = false;
                    if (match) {
                        matched++;
                        layoutGame.getChildAt(x).setOnClickListener(null);
                        layoutGame.getChildAt(y).setOnClickListener(null);
                        int z = y;
                        new CountDownTimerAdapter(500) {
                            public void onFinish() {
                                clickable = true;
                                layoutGame.getChildAt(x).setVisibility(View.INVISIBLE);
                                layoutGame.getChildAt(z).setVisibility(View.INVISIBLE);
                            }
                        }.start();
                        if (matched == END_GAME) {
                            goNext(true);
                        }
                    } else new CountDownTimerAdapter(500) {
                        public void onFinish() {
                            clickable = true;
                            for (int z = 0; z < size; z++) {
                                isChosen[z] = false;
                                flip(z, true);
                            }
                        }
                    }.start();
                }
            });
        }
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

    private List<Integer> getCoreArray() {
        List<Integer> ret = new ArrayList<>();
        for (int i = 0; i < 20; i++) ret.add(i / 2);
        Collections.shuffle(ret);
        return ret;
    }
}
