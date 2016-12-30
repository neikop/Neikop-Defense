package com.example.windzlord.brainfuck.screens.game_fragment;


import android.os.Bundle;
import android.os.CountDownTimer;
import android.support.annotation.DrawableRes;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.view.animation.ScaleAnimation;
import android.view.animation.TranslateAnimation;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.example.windzlord.brainfuck.R;
import com.example.windzlord.brainfuck.adapters.AnimationAdapter;
import com.example.windzlord.brainfuck.adapters.OnCountDownTimerFinish;
import com.example.windzlord.brainfuck.layout.GameStatusLayout;
import com.example.windzlord.brainfuck.managers.Gogo;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * A simple {@link Fragment} subclass.
 */
public class MemoryOne extends Fragment {

    @BindView(R.id.layout_welcome)
    RelativeLayout layoutWelcome;

    @BindView(R.id.textView_welcome)
    TextView textViewWelcome;

    @BindView(R.id.game_status_layout)
    GameStatusLayout gameStatusLayout;

    @BindView(R.id.textView_score)
    TextView textViewScore;

    @BindView(R.id.textView_bonus)
    TextView textViewBonus;

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

    @DrawableRes
    int src0x = R.drawable.go_ic_num0x;

    @DrawableRes
    int bgrNormal = R.drawable.custom_corner_outline_bg;

    @DrawableRes
    int bgrChosen = R.drawable.custom_corner_outline_bg_2;

    @DrawableRes
    int src[] = {R.drawable.go_ic_num0c,
            R.drawable.go_ic_num11, R.drawable.go_ic_num12, R.drawable.go_ic_num13,
            R.drawable.go_ic_num14, R.drawable.go_ic_num15, R.drawable.go_ic_num16,
            R.drawable.go_ic_num17, R.drawable.go_ic_num18, R.drawable.go_ic_num19,
            R.drawable.go_ic_num20, R.drawable.go_ic_num21, R.drawable.go_ic_num22,
            R.drawable.go_ic_num23, R.drawable.go_ic_num24, R.drawable.go_ic_num25};

    private ImageView imageViews[];
    private CountDownTimer counter;

    private boolean isPlaying = false;
    private int onGoing;
    private int score;
    private int[] array;
    private boolean isChosen[] = new boolean[8];
    private int matched;

    public MemoryOne() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.f_game_memory_one, container, false);
        settingThingsUp(view);

        return view;
    }

    private void settingThingsUp(View view) {
        ButterKnife.bind(this, view);
        addListeners();

        startGame();
    }

    private void addListeners() {
        gameStatusLayout.setPauseListener(v -> goPause());
    }

    private void startGame() {
        imageViews = new ImageView[]{imageView1, imageView2, imageView3, imageView4,
                imageView5, imageView6, imageView7, imageView8};
        gameStatusLayout.updateValues(100, 5000, 5000, 0, 10, 0);
        onGoing = 0;
        score = 0;
        textViewScore.setText("" + score);

        goStartGame();
    }

    private void goStartGame() {
        for (ImageView view : imageViews) {
            view.setBackgroundResource(bgrNormal);
            view.setImageResource(src0x);
            view.setVisibility(View.VISIBLE);
        }

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
                        goPrepare();
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

    private void goPrepare() {
        ScaleAnimation scaleOne = new ScaleAnimation(1, 0, 1, 0, 1, 0.5f, 1, 0.5f);
        scaleOne.setDuration(250);
        ScaleAnimation scaleTwo = new ScaleAnimation(0, 1, 0, 1, 1, 0.5f, 1, 0.5f);
        scaleTwo.setDuration(250);
        scaleOne.setAnimationListener(new AnimationAdapter() {
            @Override
            public void onAnimationEnd(Animation animation) {
                for (ImageView view : imageViews) {
                    view.setBackgroundResource(bgrNormal);
                    view.setImageResource(src0x);
                    view.setVisibility(View.VISIBLE);
                }
                for (ImageView view : imageViews) view.startAnimation(scaleTwo);
            }
        });
        scaleTwo.setAnimationListener(new AnimationAdapter() {
            @Override
            public void onAnimationEnd(Animation animation) {
                prepareQuiz();
            }
        });
        for (ImageView view : imageViews)
            if (view.getVisibility() == View.VISIBLE)
                view.startAnimation(scaleOne);
    }

    private void prepareQuiz() {
        matched = 0;
        for (int i = 0; i < 8; i++) isChosen[i] = false;
        new OnCountDownTimerFinish(1000, 1) {
            @Override
            public void onFinish() {
                goShow();
            }
        }.start();
    }

    private void goShow() {
        ScaleAnimation scaleOne = new ScaleAnimation(1, 0, 1, 0, 1, 0.5f, 1, 0.5f);
        scaleOne.setDuration(250);
        ScaleAnimation scaleTwo = new ScaleAnimation(0, 1, 0, 1, 1, 0.5f, 1, 0.5f);
        scaleTwo.setDuration(250);
        scaleOne.setAnimationListener(new AnimationAdapter() {
            @Override
            public void onAnimationEnd(Animation animation) {
                array = Gogo.getArrayMemoryOne(8);
                for (int i = 0; i < 8; i++) {
                    imageViews[i].setImageResource(src[array[i]]);
                }
                for (ImageView view : imageViews) view.startAnimation(scaleTwo);
            }
        });
        scaleTwo.setAnimationListener(new AnimationAdapter() {
            @Override
            public void onAnimationEnd(Animation animation) {
                isPlaying = true;
                showQuiz();

                counter = new CountDownTimer(5000, 1) {
                    @Override
                    public void onTick(long millisUntilFinished) {
                        gameStatusLayout.setTimeProgress((int) millisUntilFinished);
                        gameStatusLayout.setTimeCount((int) (millisUntilFinished / 50));
                    }

                    @Override
                    public void onFinish() {
                        isPlaying = false;
                        new OnCountDownTimerFinish(1000, 1) {
                            @Override
                            public void onFinish() {
                                textViewBonus.setText("+0");
                                textViewBonus.setVisibility(View.VISIBLE);
                                TranslateAnimation goUp = new TranslateAnimation(
                                        1, 0, 1, 0, 1, 0, 1, -1);
                                goUp.setDuration(500);
                                goUp.setAnimationListener(new AnimationAdapter() {
                                    @Override
                                    public void onAnimationEnd(Animation animation) {
                                        textViewBonus.setVisibility(View.INVISIBLE);
                                    }
                                });
                                textViewBonus.startAnimation(goUp);
                                goPrepare();
                            }
                        }.start();
                    }
                }.start();
            }
        });
        if (onGoing > 9) goEndGame();
        else for (ImageView view : imageViews) view.startAnimation(scaleOne);
    }

    private void showQuiz() {
        onGoing++;
        gameStatusLayout.setGoingCount(onGoing);
        gameStatusLayout.setGoingProgress(onGoing);

        for (int i = 0; i < 8; i++) {
            int b = i;
            imageViews[b].setOnClickListener(v -> {
                if (!isPlaying) return;
                if (isChosen[b]) imageViews[b].setBackgroundResource(bgrNormal);
                else imageViews[b].setBackgroundResource(bgrChosen);
                isChosen[b] ^= 1 + 1 == 2;

                boolean someoneWasChosen = true;
                for (boolean e : isChosen) someoneWasChosen ^= e;
                if (someoneWasChosen) {
                    boolean match = false;
                    int c = -1;
                    for (c = 0; c < 8; c++) {
                        if (c == b) continue;
                        if (isChosen[c]) match = array[b] == array[c];
                        if (match) break;
                    }
                    if (match) {
                        imageViews[b].setVisibility(View.INVISIBLE);
                        imageViews[c].setVisibility(View.INVISIBLE);
                        matched++;
                        if (matched == 2) {
                            isPlaying = false;
                            counter.cancel();
                            new OnCountDownTimerFinish(1000, 1) {
                                @Override
                                public void onFinish() {
                                    int bonus = (gameStatusLayout.getTimeCount() + 9) / 10;
                                    score += bonus;
                                    textViewBonus.setText("+" + bonus);
                                    textViewBonus.setVisibility(View.VISIBLE);
                                    TranslateAnimation goUp = new TranslateAnimation(
                                            1, 0, 1, 0, 1, 0, 1, -1);
                                    goUp.setDuration(500);
                                    goUp.setAnimationListener(new AnimationAdapter() {
                                        @Override
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
                    } else
                        for (int e = 0; e < 8; e++) {
                            isChosen[e] = false;
                            imageViews[e].setBackgroundResource(bgrNormal);
                        }
                }
            });
        }
    }

    private void goEndGame() {
        System.out.println("End Game");

    }

    private void goPause() {
        System.out.println("Pause Game");

    }

}
