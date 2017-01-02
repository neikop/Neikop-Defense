package com.example.windzlord.brainfuck.screens.games.concentration;


import android.os.Bundle;
import android.os.CountDownTimer;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.view.animation.AnimationSet;
import android.view.animation.ScaleAnimation;
import android.view.animation.TranslateAnimation;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.Switch;
import android.widget.TextView;

import com.example.windzlord.brainfuck.R;
import com.example.windzlord.brainfuck.adapters.AnimationAdapter;
import com.example.windzlord.brainfuck.adapters.CountDownTimerAdapter;
import com.example.windzlord.brainfuck.managers.Gogo;
import com.example.windzlord.brainfuck.screens.games.NeikopzGame;

import java.util.Random;

import butterknife.BindView;

/**
 * A simple {@link Fragment} subclass.
 */
public class ConcenOne extends NeikopzGame {

    private static final String TAG = ConcenOne.class.getSimpleName();
    @BindView(R.id.rl_yes)
    RelativeLayout rl_yes;

    @BindView(R.id.rl_no)
    RelativeLayout rl_no;

    @BindView(R.id.tv_left_card)
    TextView tv_left_card;

    @BindView(R.id.tv_right_card)
    TextView tv_right_card;

    @BindView(R.id.rl_top)
    RelativeLayout rl_top;

    @BindView(R.id.rl_bot)
    RelativeLayout rl_bot;

    String VOWEL = "AEIOUY";
    String CONSONANT = "BCDFGHJKLMNPQRSTVWXZ";
    String tmpText = "";


    public String getRandom() {
        switch (Gogo.getRandom(4)) {
            case 0:
                return VOWEL.charAt(Gogo.getRandom(VOWEL.length())) + (Gogo.getRandom(10) + "");
            case 1:
                return (Gogo.getRandom(10) + "") + VOWEL.charAt(Gogo.getRandom(VOWEL.length()));
            case 2:
                return CONSONANT.charAt(Gogo.getRandom(CONSONANT.length())) + (Gogo.getRandom(10) + "");
            case 3:
                return (Gogo.getRandom(10) + "") + CONSONANT.charAt(Gogo.getRandom(CONSONANT.length()));
        }
        return "";
    }


    public ConcenOne() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return createView(inflater.inflate(R.layout.game_concen_one, container, false));
    }

    @Override
    protected void goPrepare() {
        prepareQuiz();
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
        ScaleAnimation scaleOne = new ScaleAnimation(1, 0, 1, 0, 1, 0.5f, 1, 0.5f);
        scaleOne.setDuration(250);
        ScaleAnimation scaleTwo = new ScaleAnimation(0, 1, 0, 1, 1, 0.5f, 1, 0.5f);
        scaleTwo.setDuration(250);
        scaleOne.setAnimationListener(new AnimationAdapter() {
            public void onAnimationStart(Animation animation) {
                onShowing = true;
            }

            public void onAnimationEnd(Animation animation) {
                tv_right_card.setText("");
                tv_left_card.setText("");
                goStartAnimation(scaleTwo, rl_bot, rl_top);
            }
        });
        scaleTwo.setAnimationListener(new AnimationAdapter() {
            public void onAnimationEnd(Animation animation) {
                showQuiz();
                onShowing = false;
                clickable = true;
                counter = new CountDownTimer(5000, 1) {
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
        goStartAnimation(scaleOne, rl_bot, rl_top);
        if (going >= NUMBER_QUIZ) goEndGame(Gogo.CONCENTRATION, 1);

    }

    @Override
    protected void showQuiz() {
        tmpText = getRandom();
        Log.d(TAG, "tmp Text: " + tmpText);
        boolean left = Gogo.getRandom(2) == 0;
        boolean answer = false;
        if (left) {
            tv_left_card.setText(tmpText);
            tv_right_card.setText("");
            for (int i = 0; i < tmpText.length(); i++) {
                if (Character.isDigit(tmpText.charAt(i))) {
                    answer = Integer.parseInt(tmpText.charAt(i) + "") % 2 == 0;
                    break;
                }
            }
        } else {
            tv_right_card.setText(tmpText);
            tv_left_card.setText("");
            for (int i = 0; i < tmpText.length(); i++) {
                if (VOWEL.contains(tmpText.charAt(i) + "")) {
                    answer = true;
                    break;
                }
            }
        }

        boolean finalAnswer = answer;
        rl_yes.setOnClickListener(v -> goClick(finalAnswer));
        rl_no.setOnClickListener(v -> goClick(!finalAnswer));

    }

    private void goClick(boolean completed) {
        if (!clickable) return;
        goNext(completed);
    }

}
