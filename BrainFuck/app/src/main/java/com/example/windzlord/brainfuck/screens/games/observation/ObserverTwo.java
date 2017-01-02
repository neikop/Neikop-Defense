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
import com.example.windzlord.brainfuck.managers.Gogo;
import com.example.windzlord.brainfuck.screens.games.NeikopzGame;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * A simple {@link Fragment} subclass.
 */
public class ObserverTwo extends NeikopzGame {

    @BindView(R.id.imageView_1)
    ImageView imageView1;

    @BindView(R.id.imageView_2)
    ImageView imageView2;

    @BindView(R.id.imageView_3)
    ImageView imageView3;

    @BindView(R.id.imageView_4)
    ImageView imageView4;

    @BindView(R.id.imageView_5)
    ImageView imageView5;

    @BindView(R.id.imageView_6)
    ImageView imageView6;

    @BindView(R.id.imageView_7)
    ImageView imageView7;

    @BindView(R.id.imageView_8)
    ImageView imageView8;

    private int srcHided = R.drawable.go_ic_num0x;
    private int srcFirst = R.drawable.go_ic_num0z;
    private int bgrNormal = R.drawable.custom_corner_background_outline;
    private int bgrChosen = R.drawable.custom_corner_background_outline_chosen;

    @DrawableRes
    int imageResources[] = {R.drawable.go_ic_num10,
            R.drawable.go_ic_num11, R.drawable.go_ic_num12, R.drawable.go_ic_num13,
            R.drawable.go_ic_num14, R.drawable.go_ic_num15, R.drawable.go_ic_num16,
            R.drawable.go_ic_num17, R.drawable.go_ic_num18, R.drawable.go_ic_num19,
            R.drawable.go_ic_num20, R.drawable.go_ic_num21, R.drawable.go_ic_num22,
            R.drawable.go_ic_num23, R.drawable.go_ic_num24, R.drawable.go_ic_num25};

    private ImageView[] imageViews;
    private boolean isChosen[] = new boolean[8];
    private int matched;
    private int[] coreArray;

    public ObserverTwo() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.game_observer_two, container, false);
        settingThingsUp(view);

        return view;
    }

    protected void settingThingsUp(View view) {
        ButterKnife.bind(this, view);
        addListeners();

        startGame();
    }

    protected void startGame() {
        gameStatusLayout.updateValues(100, 5000, 5000, 0, NUMBER_QUIZ, 0);
        going = score = 0;

        imageViews = new ImageView[]{imageView1, imageView2, imageView3, imageView4,
                imageView5, imageView6, imageView7, imageView8};
        for (ImageView view : imageViews) {
            view.setBackgroundResource(bgrNormal);
            view.setImageResource(srcFirst);
            view.setVisibility(View.VISIBLE);
        }
        goStartAnimation();
    }

    protected void goPrepare() {
        ScaleAnimation scaleOne = new ScaleAnimation(1, 0, 1, 0, 1, 0.5f, 1, 0.5f);
        scaleOne.setDuration(250);
        ScaleAnimation scaleTwo = new ScaleAnimation(0, 1, 0, 1, 1, 0.5f, 1, 0.5f);
        scaleTwo.setDuration(250);
        scaleOne.setAnimationListener(new AnimationAdapter() {
            public void onAnimationEnd(Animation animation) {
                for (ImageView view : imageViews) {
                    view.setBackgroundResource(bgrNormal);
                    view.setImageResource(srcHided);
                    view.setVisibility(View.VISIBLE);
                }
                for (ImageView view : imageViews) view.startAnimation(scaleTwo);
            }
        });
        scaleTwo.setAnimationListener(new AnimationAdapter() {
            public void onAnimationEnd(Animation animation) {
                prepareQuiz();
            }
        });
        for (ImageView view : imageViews)
            if (view.getVisibility() == View.VISIBLE) view.startAnimation(scaleOne);
    }

    protected void prepareQuiz() {
        matched = 0;
        for (int i = 0; i < 8; i++) isChosen[i] = false;
        new CountDownTimerAdapter(1000, 1) {
            public void onFinish() {
                goShow();
            }
        }.start();
    }

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
                coreArray = Gogo.getArrayObserTwo(imageResources.length);
                for (int i = 0; i < 8; i++) {
                    imageViews[i].setImageResource(imageResources[coreArray[i]]);
                }
                for (ImageView view : imageViews) view.startAnimation(scaleTwo);
            }
        });
        scaleTwo.setAnimationListener(new AnimationAdapter() {
            public void onAnimationEnd(Animation animation) {
                onShowing = false;
                clickable = true;
                showQuiz();
                counter = new CountDownTimer(5000, 1) {
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
            }
        });
        if (going >= NUMBER_QUIZ) goEndGame(Gogo.OBSERVATION, 2);
        else for (ImageView view : imageViews) view.startAnimation(scaleOne);
    }

    protected void showQuiz() {
        going++;
        gameStatusLayout.setGoingCount(going);
        gameStatusLayout.setGoingProgress(going);
        for (int i = 0; i < 8; i++) {
            int one = i;
            imageViews[one].setOnClickListener(v -> {
                if (!clickable) return;
                if (isChosen[one]) imageViews[one].setBackgroundResource(bgrNormal);
                else imageViews[one].setBackgroundResource(bgrChosen);
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
                        imageViews[one].setOnClickListener(null);
                        imageViews[two].setOnClickListener(null);
                        int zoo = two;
                        new CountDownTimerAdapter(300, 1) {
                            public void onFinish() {
                                imageViews[one].setVisibility(View.INVISIBLE);
                                imageViews[zoo].setVisibility(View.INVISIBLE);
                            }
                        }.start();
                        if (matched == 2) {
                            goNext(true);
                        }
                    } else new CountDownTimerAdapter(300, 1) {
                        public void onFinish() {
                            for (int e = 0; e < 8; e++) {
                                isChosen[e] = false;
                                imageViews[e].setBackgroundResource(bgrNormal);
                            }
                        }
                    }.start();
                }
            });
        }
    }
}
