package com.example.windzlord.brainfuck.screens.game_fragment;


import android.graphics.Color;
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
import android.widget.TextView;

import com.example.windzlord.brainfuck.R;
import com.example.windzlord.brainfuck.adapters.AnimationAdapter;
import com.example.windzlord.brainfuck.adapters.CountDownTimerAdapter;
import com.example.windzlord.brainfuck.layout.GameStatusLayout;
import com.example.windzlord.brainfuck.managers.Gogo;
import com.example.windzlord.brainfuck.managers.ManagerPreference;

import at.markushi.ui.CircleButton;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * A simple {@link Fragment} subclass.
 */
public class MemoryOne extends Fragment {

    @BindView(R.id.layout_welcome)
    ViewGroup layoutWelcome;

    @BindView(R.id.textView_welcome)
    TextView textViewWelcome;

    @BindView(R.id.layout_game)
    ViewGroup layoutGame;

    @BindView(R.id.layout_pause)
    ViewGroup layoutPause;

    @BindView(R.id.view_right)
    View viewRight;

    @BindView(R.id.view_left)
    View viewLeft;

    @BindView(R.id.textView_pause)
    TextView textViewPause;

    @BindView(R.id.button_back)
    CircleButton buttonBack;

    @BindView(R.id.button_resume)
    CircleButton buttonResume;

    @BindView(R.id.button_exit)
    CircleButton buttonExit;

    @BindView(R.id.layout_game_status)
    GameStatusLayout gameStatusLayout;

    @BindView(R.id.imageView_score)
    ImageView imageViewScore;

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

    int srcHided = R.drawable.go_ic_num0x;
    int srcFirst = R.drawable.go_ic_num0z;
    int bgrNormal = R.drawable.custom_corner_outline_bg;
    int bgrChosen = R.drawable.custom_corner_outline_bg_2;

    @DrawableRes
    int imageResources[] = {R.drawable.go_ic_num10,
            R.drawable.go_ic_num11, R.drawable.go_ic_num12, R.drawable.go_ic_num13,
            R.drawable.go_ic_num14, R.drawable.go_ic_num15, R.drawable.go_ic_num16,
            R.drawable.go_ic_num17, R.drawable.go_ic_num18, R.drawable.go_ic_num19,
            R.drawable.go_ic_num20, R.drawable.go_ic_num21, R.drawable.go_ic_num22,
            R.drawable.go_ic_num23, R.drawable.go_ic_num24, R.drawable.go_ic_num25};

    private int[] imageArray;
    private ImageView[] imageViews;
    private CountDownTimer counter;
    private long remain;

    private boolean gameGoing = false;
    private boolean isPlaying = false;
    private int going;
    private int score;
    private int matched;
    private boolean isChosen[] = new boolean[8];

    // Debugger
    private boolean debugGoShow;
    private boolean debugPauseOnGoShow;
    private final int NUMBER_QUIZ = Gogo.NUMBER_QUIZ;

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
        gameStatusLayout.updateValues(100, 5000, 5000, 0, NUMBER_QUIZ, 0);
        imageViews = new ImageView[]{imageView1, imageView2, imageView3, imageView4,
                imageView5, imageView6, imageView7, imageView8};
        going = score = 0;
        textViewScore.setText("" + score);

        for (ImageView view : imageViews) {
            view.setBackgroundResource(bgrNormal);
            view.setImageResource(srcFirst);
            view.setVisibility(View.VISIBLE);
        }
        goStartAnimation();
    }

    private void goStartAnimation() {
        ScaleAnimation countOne = new ScaleAnimation(1, 1.3f, 1, 1.3f, 1, 0.5f, 1, 0.5f);
        ScaleAnimation countTwo = new ScaleAnimation(1, 1.3f, 1, 1.3f, 1, 0.5f, 1, 0.5f);
        ScaleAnimation countThree = new ScaleAnimation(1, 1.3f, 1, 1.3f, 1, 0.5f, 1, 0.5f);
        countThree.setDuration(200);
        countThree.setAnimationListener(new AnimationAdapter() {
            public void onAnimationEnd(Animation animation) {
                textViewWelcome.setText("1");
                new CountDownTimerAdapter(800, 1) {
                    public void onFinish() {
                        layoutWelcome.setVisibility(View.INVISIBLE);
                        gameGoing = true;
                        goPrepare();
                    }
                }.start();
            }
        });
        countTwo.setDuration(200);
        countTwo.setAnimationListener(new AnimationAdapter() {
            public void onAnimationEnd(Animation animation) {
                textViewWelcome.setText("2");
                new CountDownTimerAdapter(800, 1) {
                    public void onFinish() {
                        textViewWelcome.startAnimation(countThree);
                    }
                }.start();
            }
        });
        countOne.setDuration(200);
        countOne.setAnimationListener(new AnimationAdapter() {
            public void onAnimationEnd(Animation animation) {
                textViewWelcome.setText("3");
                new CountDownTimerAdapter(800, 1) {
                    public void onFinish() {
                        textViewWelcome.startAnimation(countTwo);
                    }
                }.start();
            }
        });
        textViewWelcome.setText("  ");
        new CountDownTimerAdapter(300, 1) {
            public void onFinish() {
                ScaleAnimation scale = new ScaleAnimation(0, 1, 0, 1, 1, 0.5f, 1, 0.5f);
                scale.setDuration(400);
                scale.setAnimationListener(new AnimationAdapter() {
                    public void onAnimationEnd(Animation animation) {
                        textViewWelcome.startAnimation(countOne);
                        new CountDownTimerAdapter(100, 2) {
                            public void onFinish() {
                                textViewWelcome.setText("3");
                            }
                        }.start();
                    }
                });
                layoutWelcome.setVisibility(View.VISIBLE);
                layoutWelcome.startAnimation(scale);
            }
        }.start();
    }

    private void goPrepare() {
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

    private void prepareQuiz() {
        matched = 0;
        for (int i = 0; i < 8; i++) isChosen[i] = false;
        new CountDownTimerAdapter(1000, 1) {
            public void onFinish() {
                goShow();
            }
        }.start();
    }

    private void goShow() {
        debugGoShow = true;
        ScaleAnimation scaleOne = new ScaleAnimation(1, 0, 1, 0, 1, 0.5f, 1, 0.5f);
        scaleOne.setDuration(250);
        ScaleAnimation scaleTwo = new ScaleAnimation(0, 1, 0, 1, 1, 0.5f, 1, 0.5f);
        scaleTwo.setDuration(250);
        scaleOne.setAnimationListener(new AnimationAdapter() {
            public void onAnimationEnd(Animation animation) {
                imageArray = Gogo.getArrayMemoryOne(imageResources.length);
                for (int i = 0; i < 8; i++) {
                    imageViews[i].setImageResource(imageResources[imageArray[i]]);
                }
                for (ImageView view : imageViews) view.startAnimation(scaleTwo);
            }
        });
        scaleTwo.setAnimationListener(new AnimationAdapter() {
            public void onAnimationEnd(Animation animation) {
                debugGoShow = false;
                isPlaying = true;
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
                        isPlaying = false;
                        nextQuiz(false);
                    }
                }.start();
            }
        });
        if (going >= NUMBER_QUIZ) goEndGame();
        else for (ImageView view : imageViews) view.startAnimation(scaleOne);
    }

    private void showQuiz() {
        going++;
        gameStatusLayout.setGoingCount(going);
        gameStatusLayout.setGoingProgress(going);

        for (int i = 0; i < 8; i++) {
            int one = i;
            imageViews[one].setOnClickListener(v -> {
                if (!isPlaying) return;
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
                        if (isChosen[two]) match = imageArray[one] == imageArray[two];
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
                            counter.cancel();
                            isPlaying = false;
                            nextQuiz(true);
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

    private void nextQuiz(boolean completed) {
        new CountDownTimerAdapter(1000, 1) {
            public void onFinish() {
                int bonus = completed ? (gameStatusLayout.getTimeCount() + 9) / 10 : 0;
                score += bonus;
                textViewBonus.setText("+" + bonus);
                textViewBonus.setVisibility(View.VISIBLE);
                TranslateAnimation goUp = new TranslateAnimation(
                        1, 0, 1, 0, 1, 0, 1, -1);
                goUp.setDuration(500);
                goUp.setAnimationListener(new AnimationAdapter() {
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

    private void goPause() {
        if (!gameGoing) return;
        boolean isPausing = layoutPause.getVisibility() == View.VISIBLE;
        if (isPausing) return;
        debugPauseOnGoShow = debugGoShow;
        layoutGame.setVisibility(View.INVISIBLE);
        layoutPause.setVisibility(View.VISIBLE);
        if (counter != null) counter.cancel();

        goPauseAnimation();
    }

    private void goPauseAnimation() {
        TranslateAnimation goRight = new TranslateAnimation(1, -1, 1, 0, 1, 0, 1, 0);
        goRight.setDuration(150);
        TranslateAnimation goLeft = new TranslateAnimation(1, 1, 1, 0, 1, 0, 1, 0);
        goLeft.setDuration(150);
        AlphaAnimation goFadeShow = new AlphaAnimation(0, 1);
        goFadeShow.setDuration(300);
        goRight.setAnimationListener(new AnimationAdapter() {
            public void onAnimationEnd(Animation animation) {
                goVisibility(View.VISIBLE, textViewPause, buttonBack, buttonResume);
                goStartAnimation(goFadeShow, textViewPause, buttonBack, buttonResume);
            }
        });
        goVisibility(View.INVISIBLE, textViewPause, buttonBack, buttonResume, buttonExit);
        viewLeft.startAnimation(goRight);
        viewRight.startAnimation(goLeft);
    }

    @OnClick(R.id.button_back)
    public void onButtonBack() {
        getActivity().onBackPressed();
    }

    @OnClick(R.id.button_resume)
    public void onButtonResume() {
        TranslateAnimation goLeft = new TranslateAnimation(1, 0, 1, -1, 1, 0, 1, 0);
        goLeft.setDuration(150);
        TranslateAnimation goRight = new TranslateAnimation(1, 0, 1, 1, 1, 0, 1, 0);
        goRight.setDuration(150);
        AlphaAnimation goFadeHide = new AlphaAnimation(1, 0);
        goFadeHide.setDuration(300);
        goFadeHide.setAnimationListener(new AnimationAdapter() {
            public void onAnimationEnd(Animation animation) {
                goVisibility(View.INVISIBLE, textViewPause, buttonBack, buttonResume);
                viewLeft.startAnimation(goLeft);
                viewRight.startAnimation(goRight);
            }
        });
        goRight.setAnimationListener(new AnimationAdapter() {
            public void onAnimationEnd(Animation animation) {
                layoutPause.setVisibility(View.INVISIBLE);
                layoutGame.setVisibility(View.VISIBLE);
                AlphaAnimation goFadeShow = new AlphaAnimation(0, 1);
                goFadeShow.setDuration(300);
                goFadeShow.setAnimationListener(new AnimationAdapter() {
                    public void onAnimationEnd(Animation animation) {
                        if (isPlaying & !debugPauseOnGoShow) goResume();
                    }
                });
                layoutGame.startAnimation(goFadeShow);
            }
        });
        goStartAnimation(goFadeHide, textViewPause, buttonBack, buttonResume);
    }

    private void goResume() {
        counter = new CountDownTimer(remain, 1) {
            @Override
            public void onTick(long millisUntilFinished) {
                remain = millisUntilFinished;
                gameStatusLayout.setTimeProgress((int) millisUntilFinished);
                gameStatusLayout.setTimeCount((int) (millisUntilFinished / 50));
            }

            @Override
            public void onFinish() {
                isPlaying = false;
                new CountDownTimerAdapter(1000, 1) {
                    public void onFinish() {
                        textViewBonus.setText("+0");
                        textViewBonus.setVisibility(View.VISIBLE);
                        TranslateAnimation goUp = new TranslateAnimation(
                                1, 0, 1, 0, 1, 0, 1, -1);
                        goUp.setDuration(500);
                        goUp.setAnimationListener(new AnimationAdapter() {
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

    private void goStartAnimation(Animation animation, View... views) {
        for (View view : views) view.startAnimation(animation);
    }

    private void goVisibility(int visibility, View... views) {
        for (View view : views) view.setVisibility(visibility);
    }

    private void goEndGame() {
        gameGoing = false;
        layoutGame.setVisibility(View.INVISIBLE);
        layoutPause.setVisibility(View.VISIBLE);

        updateInfo(1);
    }

    private void updateInfo(int game) {
        goEndAnimation(score > ManagerPreference.getInstance().getScore(Gogo.MEMORY, game));

        int level = ManagerPreference.getInstance().getLevel(Gogo.MEMORY, game);
        int expNext = ManagerPreference.getInstance().getExpNext(Gogo.MEMORY, game);
        int expCurrent = ManagerPreference.getInstance().getExpCurrent(Gogo.MEMORY, game);

        expCurrent += score;
        if (expCurrent >= expNext) {
            expCurrent = expCurrent - expNext;
            level++;
        }

        ManagerPreference.getInstance().putLevel(Gogo.MEMORY, game, level);
        ManagerPreference.getInstance().putExpCurrent(Gogo.MEMORY, game, expCurrent);
        ManagerPreference.getInstance().putScore(Gogo.MEMORY, game,
                Math.max(score, ManagerPreference.getInstance().getScore(Gogo.MEMORY, game)));
    }

    private void goEndAnimation(boolean getHigh) {
        TranslateAnimation goRight = new TranslateAnimation(1, -1, 1, 0, 1, 0, 1, 0);
        goRight.setDuration(150);
        TranslateAnimation goLeft = new TranslateAnimation(1, 1, 1, 0, 1, 0, 1, 0);
        goLeft.setDuration(150);
        AlphaAnimation goFadeShow = new AlphaAnimation(0, 1);
        goFadeShow.setDuration(300);
        goRight.setAnimationListener(new AnimationAdapter() {
            public void onAnimationEnd(Animation animation) {
                goVisibility(View.VISIBLE, textViewPause, buttonExit);
                goStartAnimation(goFadeShow, textViewPause, buttonExit);
                if (getHigh) {
                    imageViewScore.setImageResource(R.color.colorCyanLight);
                    textViewScore.setTextColor(Color.parseColor("#FFFFFF"));
                }
            }
        });
        textViewPause.setText("GAME  END");
        goVisibility(View.INVISIBLE, textViewPause, buttonExit, buttonBack, buttonResume);
        viewLeft.startAnimation(goRight);
        viewRight.startAnimation(goLeft);
    }

    @OnClick(R.id.button_exit)
    public void onButtonExit() {
        getActivity().onBackPressed();
    }
}
