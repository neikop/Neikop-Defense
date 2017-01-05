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
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.example.windzlord.brainfuck.R;
import com.example.windzlord.brainfuck.adapters.AnimationAdapter;
import com.example.windzlord.brainfuck.adapters.CountDownTimerAdapter;
import com.example.windzlord.brainfuck.managers.DBHelper;
import com.example.windzlord.brainfuck.managers.Gogo;
import com.example.windzlord.brainfuck.objects.models.CalculationOne;
import com.example.windzlord.brainfuck.screens.games.NeikopzGame;

import butterknife.BindView;

/**
 * A simple {@link Fragment} subclass.
 */
public class CalcuTwo extends NeikopzGame {

    @BindView(R.id.cal_1)
    TextView tv_cal1;
    @BindView(R.id.cal_2)
    TextView tv_cal2;

    @BindView(R.id.layout_welcome)
    RelativeLayout layoutWelcome;

    @BindView(R.id.textView_welcome)
    TextView textViewWelcome;
    MediaPlayer mediaPlayer = new MediaPlayer();
    int result_cal1 = 0;
    int result_cal2 = 0;

    public CalcuTwo() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v = createView(inflater.inflate(R.layout.game_calcu_two, container, false));
        return v;
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
                goStartAnimation(scaleTwo, tv_cal1, tv_cal2);
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
        goStartAnimation(scaleOne, tv_cal1, tv_cal2);
        if (going >= NUMBER_QUIZ) goEndGame(Gogo.CALCULATION, 2);
    }

    @Override
    protected void showQuiz() {
        random();
    }

    private void random() {
        CalculationOne cal1 = new CalculationOne();
        cal1 = DBHelper.getInstance().getrandomCalculation2(2);
        CalculationOne cal2 = new CalculationOne();
        cal2 = DBHelper.getInstance().getrandomCalculation2(2);
        setCalculation(cal1, cal2);
    }

    private void setCalculation(CalculationOne cal1, CalculationOne cal2) {
        tv_cal1.setText(cal1.getCalculation());
        tv_cal2.setText(cal2.getCalculation());
        result_cal1 = cal1.getResults();
        result_cal2 = cal2.getResults();

        tv_cal1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (result_cal1 > result_cal2) {
                    mediaPlayer = MediaPlayer.create(getContext(), R.raw.true_sound);
                    mediaPlayer.start();
                    goClick(true);
                } else {
                    mediaPlayer = MediaPlayer.create(getContext(), R.raw.wrong_sound);
                    mediaPlayer.start();
                    goClick(false);
                }
            }
        });
        tv_cal2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (result_cal1 < result_cal2) {
                    mediaPlayer = MediaPlayer.create(getContext(), R.raw.true_sound);
                    mediaPlayer.start();
                    goClick(true);
                } else {
                    mediaPlayer = MediaPlayer.create(getContext(), R.raw.wrong_sound);
                    mediaPlayer.start();
                    goClick(false);
                }
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
