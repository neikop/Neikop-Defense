package com.example.windzlord.brainfuck.screens.game_fragment;


import android.media.MediaPlayer;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.view.animation.ScaleAnimation;
import android.widget.Button;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.example.windzlord.brainfuck.R;
import com.example.windzlord.brainfuck.adapters.AnimationAdapter;
import com.example.windzlord.brainfuck.adapters.OnCountDownTimerFinish;
import com.example.windzlord.brainfuck.managers.DBHelper;
import com.example.windzlord.brainfuck.objects.models.CalculationOne;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * A simple {@link Fragment} subclass.
 */
public class CalcuOne extends Fragment {

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

    @BindView(R.id.result)
    TextView tv_result;

    @BindView(R.id.score)
    TextView tv_score;

    @BindView(R.id.layout_welcome)
    RelativeLayout layoutWelcome;

    @BindView(R.id.textView_welcome)
    TextView textViewWelcome;

    @BindView(R.id.layout_blur)
    RelativeLayout rl_blur;
    String results = "";
    int scores = 0;

    public CalcuOne() {

    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.f_game_calcu_one, container, false);
        goStartGame();

        settingThingsUp(v);
        return v;
    }

    private void random(int num) {
        CalculationOne cal1 = new CalculationOne();
        if (num == 1) {
            cal1 = DBHelper.getInstance().getrandomCalculation(1);
        } else {
            cal1 = DBHelper.getInstance().getrandomCalculation(2);
        }
        setCalculation(cal1);
    }


    private void goStartGame() {

        ScaleAnimation countOne = new ScaleAnimation(1, 1.3f, 1, 1.3f, 1, 0.5f, 1, 0.5f);
        ScaleAnimation countTwo = new ScaleAnimation(1, 1.3f, 1, 1.3f, 1, 0.5f, 1, 0.5f);
        ScaleAnimation countThree = new ScaleAnimation(1, 1.3f, 1, 1.3f, 1, 0.5f, 1, 0.5f);
        countThree.setDuration(300);
        countThree.setAnimationListener(new AnimationAdapter() {
            @Override
            public void onAnimationEnd(Animation animation) {
                textViewWelcome.setText("0");
                new OnCountDownTimerFinish(700, 1) {
                    @Override
                    public void onFinish() {
                        layoutWelcome.setVisibility(View.INVISIBLE);
                        rl_blur.setVisibility(View.INVISIBLE);
                    }
                }.start();
            }
        });
        countTwo.setDuration(300);
        countTwo.setAnimationListener(new AnimationAdapter() {
            @Override
            public void onAnimationEnd(Animation animation) {
                textViewWelcome.setText("1");
                new OnCountDownTimerFinish(700, 1) {
                    @Override
                    public void onFinish() {
                        textViewWelcome.startAnimation(countThree);
                    }
                }.start();
            }
        });
        countOne.setDuration(300);
        countOne.setAnimationListener(new AnimationAdapter() {
            @Override
            public void onAnimationEnd(Animation animation) {
                textViewWelcome.setText("2");
                new OnCountDownTimerFinish(700, 1) {
                    @Override
                    public void onFinish() {
                        textViewWelcome.startAnimation(countTwo);
                    }
                }.start();
            }
        });
        new OnCountDownTimerFinish(500, 1) {
            @Override
            public void onFinish() {
                AlphaAnimation alpha = new AlphaAnimation(0, 1);
                alpha.setDuration(300);

                alpha.setAnimationListener(new AnimationAdapter() {
                    @Override
                    public void onAnimationEnd(Animation animation) {
                        new OnCountDownTimerFinish(400, 1) {
                            @Override
                            public void onFinish() {
                                textViewWelcome.startAnimation(countOne);

                            }
                        }.start();
                        layoutWelcome.setVisibility(View.VISIBLE);

                    }
                });
                layoutWelcome.startAnimation(alpha);

            }
        }.start();
    }

    private void settingThingsUp(View view) {
        ButterKnife.bind(this, view);

        addListener();
        random(1);
    }

    private void setCalculation(CalculationOne cal) {
        tv_cal.setText(cal.getCalculation());

        btn_equals.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                MediaPlayer mediaPlayer = new MediaPlayer();
                int cal_results;
                cal_results = cal.getResults();
                if (results == "") {
                    tv_result.setText("" + scores);
                } else if (cal_results == Integer.valueOf(results)) {
                    mediaPlayer = MediaPlayer.create(getContext(), R.raw.true_sound);
                    mediaPlayer.start();
                    scores += 100;
                    tv_score.setText("" + scores);
                } else {
                    mediaPlayer = MediaPlayer.create(getContext(), R.raw.wrong_sound);
                    mediaPlayer.start();
                    tv_score.setText("" + scores);
                }
                try {
                    Thread.sleep(500);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }

                tv_result.setText("");
                results = "";
                if (scores >= 1000) {
                    random(2);
                } else {
                    random(1);
                }
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
