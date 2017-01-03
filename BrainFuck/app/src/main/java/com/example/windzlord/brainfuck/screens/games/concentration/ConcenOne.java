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
import com.example.windzlord.brainfuck.managers.Gogo;
import com.example.windzlord.brainfuck.screens.games.NeikopzGame;

import butterknife.BindView;

/**
 * A simple {@link Fragment} subclass.
 */
public class ConcenOne extends NeikopzGame {

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
                textViewRightCard.setText("");
                textViewLeftCard.setText("");
                goStartAnimation(scaleTwo, layoutRightCard, layoutLeftCard);
            }
        });
        scaleTwo.setAnimationListener(new AnimationAdapter() {
            public void onAnimationEnd(Animation animation) {
                showQuiz();
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
        goStartAnimation(scaleOne, layoutRightCard, layoutLeftCard);
        if (going >= NUMBER_QUIZ) goEndGame(Gogo.CONCENTRATION, 1);

    }

    @Override
    protected void showQuiz() {
        String quiz = getRandom();
        boolean left = Gogo.getRandom(2) == 0;
        boolean answer = false;
        if (left) {
            textViewLeftCard.setText(quiz);
            textViewRightCard.setText("");
            for (int i = 0; i < quiz.length(); i++)
                if (Character.isDigit(quiz.charAt(i))) {
                    answer = Integer.parseInt(quiz.charAt(i) + "") % 2 == 0;
                    break;
                }
        } else {
            textViewRightCard.setText(quiz);
            textViewLeftCard.setText("");
            for (int i = 0; i < quiz.length(); i++)
                if (VOWEL.contains(quiz.charAt(i) + "")) {
                    answer = true;
                    break;
                }
        }
        boolean completed = answer;
        answerYes.setOnClickListener(v -> goClick(completed));
        answerNo.setOnClickListener(v -> goClick(!completed));
    }

    private void goClick(boolean completed) {
        if (!clickable) return;
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
        new CountDownTimerAdapter(450, 1) {
            public void onFinish() {
                clickable = true;
            }
        }.start();
    }

    @Override
    protected void goHighScoreColor() {
        imageViewScore.setImageResource(R.color.colorOrangeLight);
        textViewScore.setTextColor(getResources().getColor(R.color.colorWhite));
    }

    private String getRandom() {
        switch (Gogo.getRandom(4)) {
            case 1:
                return VOWEL.charAt(Gogo.getRandom(VOWEL.length())) + " " + (Gogo.getRandom(10));
            case 2:
                return (Gogo.getRandom(10) + "") + " " + VOWEL.charAt(Gogo.getRandom(VOWEL.length()));
            case 3:
                return CONSONANT.charAt(Gogo.getRandom(CONSONANT.length())) + " " + (Gogo.getRandom(10) + "");
            default:
                return (Gogo.getRandom(10) + "") + " " + CONSONANT.charAt(Gogo.getRandom(CONSONANT.length()));
        }
    }
}
