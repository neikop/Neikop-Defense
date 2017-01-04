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
import android.widget.Button;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.example.windzlord.brainfuck.R;
import com.example.windzlord.brainfuck.adapters.AnimationAdapter;
import com.example.windzlord.brainfuck.adapters.CountDownTimerAdapter;
import com.example.windzlord.brainfuck.managers.DBHelper;
import com.example.windzlord.brainfuck.managers.Gogo;
import com.example.windzlord.brainfuck.managers.ManagerPreference;
import com.example.windzlord.brainfuck.objects.models.CalculationOne;
import com.example.windzlord.brainfuck.screens.games.NeikopzGame;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * A simple {@link Fragment} subclass.
 */
public class CalcuOne extends NeikopzGame {
    @BindView(R.id.btn_0)
    Button btn_0;

    @BindView(R.id.equals)
    Button btn_equals;

    @BindView(R.id.btn_1)
    Button btn_1;

    @BindView(R.id.btn_2)
    Button btn_2;

    @BindView(R.id.btn_3)
    Button btn_3;

    @BindView(R.id.btn_4)
    Button btn_4;

    @BindView(R.id.btn_5)
    Button btn_5;

    @BindView(R.id.btn_6)
    Button btn_6;

    @BindView(R.id.btn_7)
    Button btn_7;

    @BindView(R.id.btn_8)
    Button btn_8;

    @BindView(R.id.btn_9)
    Button btn_9;

    @BindView(R.id.tv_cal)
    TextView tv_cal;

    @BindView(R.id.results)
    TextView tv_result;

    @BindView(R.id.layout_welcome)
    RelativeLayout layoutWelcome;

    @BindView(R.id.textView_welcome)
    TextView textViewWelcome;


    String results ="";

    public CalcuOne() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragmen

        View v = createView(inflater.inflate(R.layout.game_calcu_one, container, false));
//        stt(v);
        addListener();
        return  v;
    }
    public void stt(View v){

        addListener();
    }

    @Override
    protected void goPrepare() {
                prepareQuiz();
    }

    @Override
    protected void prepareQuiz() {
        new CountDownTimerAdapter(200, 1) {
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
                goStartAnimation(scaleTwo, tv_cal);
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
        goStartAnimation(scaleOne, tv_cal);
        if (going >= NUMBER_QUIZ) goEndGame(Gogo.CALCULATION, 1);
    }

    @Override
    protected void showQuiz() {
        random();
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

    private void random() {
        CalculationOne cal1 = new CalculationOne();
        cal1 = DBHelper.getInstance().getrandomCalculation(2);
        setCalculation(cal1);
    }

    private void setCalculation(CalculationOne cal) {
        tv_cal.setText(cal.getCalculation());
        boolean answer =false;
        btn_equals.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                MediaPlayer mediaPlayer = new MediaPlayer();
                int cal_results;
                cal_results = cal.getResults();
                if (results == "") {

                } else if (cal_results == Integer.valueOf(results)) {
                    mediaPlayer = MediaPlayer.create(getContext(), R.raw.true_sound);
                    mediaPlayer.start();
                    tv_result.setText("");
                    results = "";
                     goClick(true);
                } else {
                    mediaPlayer = MediaPlayer.create(getContext(), R.raw.wrong_sound);
                    mediaPlayer.start();
                    tv_result.setText("");
                    results = "";
                    goClick(false);
                }
                tv_result.setText("");
                results = "";
//                random();
            }
        });
    }

    private void addListener() {
            btn_0.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    results += "0";
                    tv_result.setText(results);
                }
            });
            btn_1.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    results += "1";
                    tv_result.setText(results);
                }
            });
            btn_2.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    results += "2";
                    tv_result.setText(results);
                }
            });
            btn_3.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    results += "3";
                    tv_result.setText(results);
                }
            });
            btn_4.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    results += "4";
                    tv_result.setText(results);
                }
            });
            btn_5.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    results += "5";
                    tv_result.setText(results);
                }
            });
            btn_6.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    results += "6";
                    tv_result.setText(results);
                }
            });
            btn_7.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    results += "7";
                    tv_result.setText(results);
                }
            });
            btn_8.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    results += "8";
                    tv_result.setText(results);
                }
            });
            btn_9.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    results += "9";
                    tv_result.setText(results);
                }
            });


    }
}
