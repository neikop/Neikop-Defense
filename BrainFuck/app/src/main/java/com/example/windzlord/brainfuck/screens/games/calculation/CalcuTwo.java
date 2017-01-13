package com.example.windzlord.brainfuck.screens.games.calculation;


import android.os.Bundle;
import android.os.CountDownTimer;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.ScaleAnimation;
import android.widget.TextView;

import com.example.windzlord.brainfuck.R;
import com.example.windzlord.brainfuck.adapters.AnimationAdapter;
import com.example.windzlord.brainfuck.adapters.CountDownTimerAdapter;
import com.example.windzlord.brainfuck.managers.ManagerGameData;
import com.example.windzlord.brainfuck.managers.ManagerBrain;
import com.example.windzlord.brainfuck.objects.models.Calculator;
import com.example.windzlord.brainfuck.screens.games.GameDaddy;

import butterknife.BindView;

/**
 * A simple {@link Fragment} subclass.
 */
public class CalcuTwo extends GameDaddy {

    @BindView(R.id.textView_game_top)
    TextView textViewTop;

    @BindView(R.id.textView_game_bottom)
    TextView textViewBottom;

    private int bgrChosen = R.drawable.custom_corner_background_6_outline_chosen;
    private int bgrNormal = R.drawable.custom_corner_background_6_outline;

    private int resultCalTop = 0;
    private int resultCalBottom = 0;

    public CalcuTwo() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return createView(createView(inflater.inflate(R.layout.game_calcu_two, container, false)));
    }

    @Override
    protected void goPrepare() {
        prepareQuiz();
    }

    @Override
    protected void prepareQuiz() {
        new CountDownTimerAdapter(500) {
            public void onFinish() {
                textViewTop.setBackgroundResource(bgrNormal);
                textViewBottom.setBackgroundResource(bgrNormal);
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
                goStartAnimation(scaleTwo, textViewTop, textViewBottom);
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
                going++;
                gameStatusLayout.setGoingCount(going);
                gameStatusLayout.setGoingProgress(going);
            }
        });
        goStartAnimation(scaleOne, textViewTop, textViewBottom);
        if (going >= QUIZ) goEndGame(ManagerBrain.CALCULATION, 2);
    }

    @Override
    protected void showQuiz() {
        Calculator[] core = ManagerGameData.getInstance().getCalculatorTwo();
        textViewTop.setText(core[0].getCalculator());
        textViewBottom.setText(core[1].getCalculator());

        boolean soloTop = core[0].getResult() > core[1].getResult();
        textViewTop.setOnClickListener(view -> goClickTop(soloTop));
        textViewBottom.setOnClickListener(view -> goClickBottom(!soloTop));
    }

    private void goClickTop(boolean completed) {
        if (!clickable) return;
        textViewTop.setBackgroundResource(bgrChosen);
        goNext(completed);
    }

    private void goClickBottom(boolean completed) {
        if (!clickable) return;
        textViewBottom.setBackgroundResource(bgrChosen);
        goNext(completed);
    }

    @Override
    protected void goPause() {
        super.goPause();
        clickable = false;
    }

    @Override
    protected void onButtonResume() {
        super.onButtonResume();
        new CountDownTimerAdapter(450) {
            public void onFinish() {
                clickable = true;
            }
        }.start();
    }

}
