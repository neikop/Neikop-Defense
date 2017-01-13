package com.example.windzlord.brainfuck.screens.games.concentration;


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
import com.example.windzlord.brainfuck.managers.ManagerBrain;
import com.example.windzlord.brainfuck.screens.games.GameDaddy;

import java.util.Random;

import butterknife.BindView;

/**
 * A simple {@link Fragment} subclass.
 */
public class ConcenOne extends GameDaddy {

    @BindView(R.id.answer_yes)
    ViewGroup answerYes;

    @BindView(R.id.answer_no)
    ViewGroup answerNo;

    @BindView(R.id.textView_left_card)
    TextView textViewLeftCard;

    @BindView(R.id.textView_right_card)
    TextView textViewRightCard;

    @BindView(R.id.layout_left_card)
    ViewGroup layoutLeftCard;

    @BindView(R.id.layout_right_card)
    ViewGroup layoutRightCard;

    @BindView(R.id.layout_left_hint)
    View layoutLeftHint;

    @BindView(R.id.layout_right_hint)
    View layoutRightHint;

    private int bgrYesChosen = R.drawable.custom_corner_background_4_answer_yes_chosen;
    private int bgrYesNormal = R.drawable.custom_corner_background_4_answer_yes;
    private int bgrNoChosen = R.drawable.custom_corner_background_4_answer_no_chosen;
    private int bgrNoNormal = R.drawable.custom_corner_background_4_answer_no;

    private final String VOWEL = "AEIUY";
    private final String CONSONANT = "BCDFGHJKLMNPQRSTVWXZ";

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
        new CountDownTimerAdapter(500) {
            public void onFinish() {
                answerYes.setBackgroundResource(bgrYesNormal);
                answerNo.setBackgroundResource(bgrNoNormal);
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
                goVisibility(View.INVISIBLE, layoutLeftHint, layoutRightHint);

                showQuiz();
                goStartAnimation(scaleTwo, layoutRightCard, layoutLeftCard);
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
        ScaleAnimation scaleOneDebug = new ScaleAnimation(1, 0, 1, 0, 1, 0.5f, 1, 0.5f);
        scaleOneDebug.setDuration(250);
        if (layoutLeftHint.getVisibility() == View.VISIBLE)
            layoutLeftHint.startAnimation(scaleOneDebug);
        if (layoutRightHint.getVisibility() == View.VISIBLE)
            layoutRightHint.startAnimation(scaleOneDebug);

        goStartAnimation(scaleOne, layoutRightCard, layoutLeftCard);
        if (going >= QUIZ) goEndGame(ManagerBrain.CONCENTRATION, 1);

    }

    @Override
    protected void showQuiz() {
        String quiz = getRandom();
        boolean left = new Random().nextBoolean();
        boolean answer = false;

        ScaleAnimation scaleTwo = new ScaleAnimation(0, 1, 0, 1, 1, 0.5f, 1, 0.5f);
        scaleTwo.setDuration(250);

        if (left) {
            layoutLeftHint.setVisibility(View.VISIBLE);
            layoutLeftHint.startAnimation(scaleTwo);
            textViewLeftCard.setText(quiz);
            textViewRightCard.setText("");
            for (int i = 0; i < quiz.length(); i++)
                if (Character.isDigit(quiz.charAt(i))) {
                    answer = Integer.parseInt(quiz.charAt(i) + "") % 2 == 0;
                    break;
                }
        } else {
            layoutRightHint.setVisibility(View.VISIBLE);
            layoutRightHint.startAnimation(scaleTwo);
            textViewRightCard.setText(quiz);
            textViewLeftCard.setText("");
            for (int i = 0; i < quiz.length(); i++)
                if (VOWEL.contains(quiz.charAt(i) + "")) {
                    answer = true;
                    break;
                }
        }
        boolean completed = answer;
        answerYes.setOnClickListener(v -> goClickYes(completed));
        answerNo.setOnClickListener(v -> goClickNo(!completed));
    }

    private void goClickYes(boolean completed) {
        if (!clickable) return;
        answerYes.setBackgroundResource(bgrYesChosen);
        goNext(completed);
    }

    private void goClickNo(boolean completed) {
        if (!clickable) return;
        answerNo.setBackgroundResource(bgrNoChosen);
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

    private String getRandom() {
        switch (new Random().nextInt(4)) {
            case 1:
                return VOWEL.charAt(new Random().nextInt(VOWEL.length())) + " " + (new Random().nextInt(10));
            case 2:
                return (new Random().nextInt(10) + "") + " " + VOWEL.charAt(new Random().nextInt(VOWEL.length()));
            case 3:
                return CONSONANT.charAt(new Random().nextInt(CONSONANT.length())) + " " + (new Random().nextInt(10) + "");
            default:
                return (new Random().nextInt(10) + "") + " " + CONSONANT.charAt(new Random().nextInt(CONSONANT.length()));
        }
    }
}
