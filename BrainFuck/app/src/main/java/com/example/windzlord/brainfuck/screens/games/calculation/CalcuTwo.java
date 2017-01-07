package com.example.windzlord.brainfuck.screens.games.calculation;


import android.media.MediaPlayer;
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
import com.example.windzlord.brainfuck.managers.ManagerDatabaseGame;
import com.example.windzlord.brainfuck.managers.Gogo;
import com.example.windzlord.brainfuck.objects.models.Calculation;
import com.example.windzlord.brainfuck.screens.games.NeikopzGame;

import butterknife.BindView;

/**
 * A simple {@link Fragment} subclass.
 */
public class CalcuTwo extends NeikopzGame {

    @BindView(R.id.textView_game_top)
    TextView textViewTop;

    @BindView(R.id.textView_game_bottom)
    TextView textViewBottom;

    MediaPlayer mediaPlayer = new MediaPlayer();

    int resultCalTop = 0;
    int resultCalBottom = 0;

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
                random();
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
                        goClick(false);
                    }
                }.start();
                going++;
                gameStatusLayout.setGoingCount(going);
                gameStatusLayout.setGoingProgress(going);
            }
        });
        goStartAnimation(scaleOne, textViewTop, textViewBottom);
        if (going >= NUMBER_QUIZ) goEndGame(Gogo.CALCULATION, 2);
    }

    @Override
    protected void showQuiz() {
        random();
    }

    private void random() {
        Calculation calcuOne = ManagerDatabaseGame.getInstance().getRandomCalculation(2);
        Calculation calcuTwo = ManagerDatabaseGame.getInstance().getRandomCalculation(2);
        setCalculation(calcuOne, calcuTwo);
    }

    private void setCalculation(Calculation calcuOne, Calculation calcuTwo) {
        textViewTop.setText(calcuOne.getCalculation());
        textViewBottom.setText(calcuTwo.getCalculation());
        resultCalTop = calcuOne.getResults();
        resultCalBottom = calcuTwo.getResults();

        textViewTop.setOnClickListener(view -> {
            if (resultCalTop > resultCalBottom) {
//                mediaPlayer = MediaPlayer.create(getContext(), R.raw.true_sound);
//                mediaPlayer.start();
                goClick(true);
            } else {
//                mediaPlayer = MediaPlayer.create(getContext(), R.raw.wrong_sound);
//                mediaPlayer.start();
                goClick(false);
            }
        });

        textViewBottom.setOnClickListener(view -> {
            if (resultCalTop < resultCalBottom) {
//                mediaPlayer = MediaPlayer.create(getContext(), R.raw.true_sound);
//                mediaPlayer.start();
                goClick(true);
            } else {
//                mediaPlayer = MediaPlayer.create(getContext(), R.raw.wrong_sound);
//                mediaPlayer.start();
                goClick(false);
            }
        });

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

}
