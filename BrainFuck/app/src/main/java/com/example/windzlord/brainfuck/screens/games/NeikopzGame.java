package com.example.windzlord.brainfuck.screens.games;


import android.graphics.Color;
import android.os.CountDownTimer;
import android.support.annotation.DrawableRes;
import android.support.v4.app.Fragment;
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

import java.util.ArrayList;
import java.util.List;

import at.markushi.ui.CircleButton;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import de.hdodenhof.circleimageview.CircleImageView;

/**
 * A simple {@link Fragment} subclass.
 */
public abstract class NeikopzGame extends Fragment {

    protected
    @BindView(R.id.layout_welcome)
    ViewGroup layoutWelcome;

    protected
    @BindView(R.id.textView_welcome)
    TextView textViewWelcome;

    protected
    @BindView(R.id.layout_next)
    ViewGroup layoutNext;

    protected
    @BindView(R.id.imageView_next)
    ImageView imageViewNext;

    protected
    @BindView(R.id.layout_pause)
    ViewGroup layoutPause;

    protected
    @BindView(R.id.view_pause_right)
    View viewPauseRight;

    protected
    @BindView(R.id.view_pause_left)
    View viewPauseLeft;

    protected
    @BindView(R.id.textView_pause)
    TextView textViewPause;

    protected
    @BindView(R.id.button_back)
    CircleButton buttonBack;

    protected
    @BindView(R.id.button_resume)
    CircleButton buttonResume;

    protected
    @BindView(R.id.button_exit)
    CircleButton buttonExit;

    protected
    @BindView(R.id.layout_game_status)
    GameStatusLayout gameStatusLayout;

    protected
    @BindView(R.id.layout_score)
    ViewGroup layoutScore;

    protected
    @BindView(R.id.imageView_score)
    ImageView imageViewScore;

    protected
    @BindView(R.id.textView_score)
    TextView textViewScore;

    protected
    @BindView(R.id.textView_bonus)
    TextView textViewBonus;

    protected
    @BindView(R.id.layout_game)
    ViewGroup layoutGame;

    protected boolean onShowing = false;
    protected boolean isPauseOnShowing;

    protected CountDownTimer counter;
    protected long remain;
    protected boolean canPause = false;
    protected boolean clickable = false;
    protected int going;
    protected int score;
    protected final int NUMBER_QUIZ = Gogo.NUMBER_QUIZ;

    protected abstract void settingThingsUp(View view);

    protected void addListeners() {
        gameStatusLayout.setPauseListener(v -> goPause());
    }

    protected abstract void startGame();

    protected void goStartAnimation() {
        ScaleAnimation countOne = new ScaleAnimation(1, 1.3f, 1, 1.3f, 1, 0.5f, 1, 0.5f);
        ScaleAnimation countTwo = new ScaleAnimation(1, 1.3f, 1, 1.3f, 1, 0.5f, 1, 0.5f);
        ScaleAnimation countThree = new ScaleAnimation(1, 1.3f, 1, 1.3f, 1, 0.5f, 1, 0.5f);
        countThree.setDuration(200);
        countThree.setAnimationListener(new AnimationAdapter() {
            public void onAnimationEnd(Animation animation) {
                textViewWelcome.setText("1");
                new CountDownTimerAdapter(800, 1) {
                    public void onFinish() {
                        ScaleAnimation scale = new ScaleAnimation(1, 0, 1, 0, 1, 0.5f, 1, 0.5f);
                        scale.setDuration(250);
                        scale.setAnimationListener(new AnimationAdapter() {
                            public void onAnimationEnd(Animation animation) {
                                layoutWelcome.setVisibility(View.INVISIBLE);
                                canPause = true;
                            }
                        });
                        layoutWelcome.startAnimation(scale);
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

    protected abstract void goPrepare();

    protected abstract void prepareQuiz();

    protected abstract void goShow();

    protected abstract void showQuiz();

    protected void goNext(boolean completed) {
        clickable = false;
        counter.cancel();
        new CountDownTimerAdapter(300, 1) {
            public void onFinish() {
                imageViewNext.setImageResource(completed ?
                        R.drawable.ga_ic_color_correct : R.drawable.ga_ic_color_cross);
                layoutNext.setVisibility(View.VISIBLE);
                ScaleAnimation scale = new ScaleAnimation(0, 1, 0, 1, 1, 0.5f, 1, 0.5f);
                scale.setDuration(250);
                layoutNext.startAnimation(scale);
            }
        }.start();
        new CountDownTimerAdapter(1000, 1) {
            public void onFinish() {
                ScaleAnimation scale = new ScaleAnimation(1, 0, 1, 0, 1, 0.5f, 1, 0.5f);
                scale.setDuration(250);
                scale.setAnimationListener(new AnimationAdapter() {
                    public void onAnimationEnd(Animation animation) {
                        layoutNext.setVisibility(View.INVISIBLE);
                    }
                });
                layoutNext.startAnimation(scale);
            }
        }.start();
        nextQuiz(completed);
    }

    protected void nextQuiz(boolean completed) {
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

    protected void goPause() {
        if (!canPause) return;
        boolean isPausing = layoutPause.getVisibility() == View.VISIBLE;
        if (isPausing) return;
        isPauseOnShowing = onShowing;
        layoutGame.setVisibility(View.INVISIBLE);
        layoutPause.setVisibility(View.VISIBLE);
        if (counter != null) counter.cancel();
        goPauseAnimation();
    }

    protected void goPauseAnimation() {
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
        viewPauseLeft.startAnimation(goRight);
        viewPauseRight.startAnimation(goLeft);
    }

    @OnClick(R.id.button_back)
    protected void onButtonBack() {
        getActivity().onBackPressed();
    }

    @OnClick(R.id.button_resume)
    protected void onButtonResume() {
        TranslateAnimation goLeft = new TranslateAnimation(1, 0, 1, -1, 1, 0, 1, 0);
        goLeft.setDuration(150);
        TranslateAnimation goRight = new TranslateAnimation(1, 0, 1, 1, 1, 0, 1, 0);
        goRight.setDuration(150);
        AlphaAnimation goFadeHide = new AlphaAnimation(1, 0);
        goFadeHide.setDuration(300);
        goFadeHide.setAnimationListener(new AnimationAdapter() {
            public void onAnimationEnd(Animation animation) {
                goVisibility(View.INVISIBLE, textViewPause, buttonBack, buttonResume);
                viewPauseLeft.startAnimation(goLeft);
                viewPauseRight.startAnimation(goRight);
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
                        if (clickable & !isPauseOnShowing) goResume();
                    }
                });
                layoutGame.startAnimation(goFadeShow);
            }
        });
        goStartAnimation(goFadeHide, textViewPause, buttonBack, buttonResume);
    }

    protected void goResume() {
        counter = new CountDownTimer(remain, 1) {
            @Override
            public void onTick(long millisUntilFinished) {
                remain = millisUntilFinished;
                gameStatusLayout.setTimeProgress((int) millisUntilFinished);
                gameStatusLayout.setTimeCount((int) (millisUntilFinished / 50));
            }

            @Override
            public void onFinish() {
                clickable = false;
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

    protected void goEndGame(String name, int index) {
        canPause = false;
        layoutGame.setVisibility(View.INVISIBLE);
        layoutPause.setVisibility(View.VISIBLE);

        updateInfo(name, index);
    }

    protected void updateInfo(String name, int index) {
        goEndAnimation(score > ManagerPreference.getInstance().getScore(name, index));

        int level = ManagerPreference.getInstance().getLevel(name, index);
        int expNext = ManagerPreference.getInstance().getExpNext(name, index);
        int expCurrent = ManagerPreference.getInstance().getExpCurrent(name, index);

        expCurrent += score;
        if (expCurrent >= expNext) {
            expCurrent = expCurrent - expNext;
            level++;
        }

        ManagerPreference.getInstance().putLevel(name, index, level);
        ManagerPreference.getInstance().putExpCurrent(name, index, expCurrent);
        ManagerPreference.getInstance().putScore(name, index,
                Math.max(score, ManagerPreference.getInstance().getScore(name, index)));
    }

    protected void goEndAnimation(boolean getHigh) {
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
                    ScaleAnimation scale = new ScaleAnimation(1, 1.3f, 1, 1.3f, 1, 0.5f, 1, 0.5f);
                    scale.setDuration(300);
                    scale.setFillAfter(true);
                    layoutScore.startAnimation(scale);
                    imageViewScore.setImageResource(R.color.colorCyanLight);
                    textViewScore.setTextColor(Color.parseColor("#FFFFFF"));
                }
            }
        });
        textViewPause.setText("GAME  END");
        goVisibility(View.INVISIBLE, textViewPause, buttonExit, buttonBack, buttonResume);
        viewPauseLeft.startAnimation(goRight);
        viewPauseRight.startAnimation(goLeft);
    }

    @OnClick(R.id.button_exit)
    protected void onButtonExit() {
        getActivity().onBackPressed();
    }

    protected void goStartAnimation(Animation animation, View... views) {
        for (View view : views) view.startAnimation(animation);
    }

    protected void goStartAnimation(Animation animation, List<View> views) {
        for (View view : views) view.startAnimation(animation);
    }

    protected void goVisibility(int visibility, View... views) {
        for (View view : views) view.setVisibility(visibility);
    }

    protected List<View> goChildGroup(ViewGroup group) {
        List<View> ret = new ArrayList<>();
        for (int i = 0; i < group.getChildCount(); i++) {
            ret.add(group.getChildAt(i));
        }
        return ret;
    }

    protected void goImageResou(View view, @DrawableRes int res) {
        ((ImageView) view).setImageResource(res);
    }

}
