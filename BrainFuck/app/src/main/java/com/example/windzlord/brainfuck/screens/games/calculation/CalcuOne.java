package com.example.windzlord.brainfuck.screens.games.calculation;


import android.os.Bundle;
import android.os.CountDownTimer;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.view.animation.RotateAnimation;
import android.view.animation.ScaleAnimation;
import android.widget.TextView;

import com.example.windzlord.brainfuck.R;
import com.example.windzlord.brainfuck.adapters.AnimationAdapter;
import com.example.windzlord.brainfuck.adapters.CountDownTimerAdapter;
import com.example.windzlord.brainfuck.managers.ManagerBrain;
import com.example.windzlord.brainfuck.managers.ManagerGameData;
import com.example.windzlord.brainfuck.objects.models.Calculator;
import com.example.windzlord.brainfuck.screens.games.GameDaddy;

import java.util.Random;

import at.markushi.ui.CircleButton;
import butterknife.BindView;

/**
 * A simple {@link Fragment} subclass.
 */
public class CalcuOne extends GameDaddy {

    @BindView(R.id.textView_question)
    TextView textViewQuestion;

    @BindView(R.id.layout_answer)
    ViewGroup layoutAnswer;

    @BindView(R.id.layout_a)
    ViewGroup layoutA;

    @BindView(R.id.button_a)
    CircleButton buttonA;

    @BindView(R.id.textView_answer_a)
    TextView answerA;

    @BindView(R.id.layout_b)
    ViewGroup layoutB;

    @BindView(R.id.button_b)
    CircleButton buttonB;

    @BindView(R.id.textView_answer_b)
    TextView answerB;

    @BindView(R.id.layout_c)
    ViewGroup layoutC;

    @BindView(R.id.button_c)
    CircleButton buttonC;

    @BindView(R.id.textView_answer_c)
    TextView answerC;

    private CircleButton[] buttons;
    private ViewGroup[] layouts;

    private int bgrCorrect = R.drawable.custom_oval_background_outline_correct;
    private int bgrWrong = R.drawable.custom_oval_background_outline_wrong;
    private int bgrNormal = R.drawable.custom_oval_background_outline;

    private int answer;
    private int[] coreArrayAnswer;

    public CalcuOne() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return createView(inflater.inflate(R.layout.game_calcu_one, container, false));
    }

    @Override
    protected void startGame() {
        super.startGame();
        buttons = new CircleButton[]{buttonA, buttonB, buttonC};
        layouts = new ViewGroup[]{layoutA, layoutB, layoutC};
        layoutAnswer.setVisibility(View.INVISIBLE);
    }

    @Override
    protected void addListeners() {
        super.addListeners();
        for (int i = 0; i < 3; i++) {
            int clicked = i;
            buttons[i].setOnClickListener(v -> goNext(clicked));
        }
    }

    @Override
    protected void goPrepare() {
        prepareQuiz();
    }

    @Override
    protected void prepareQuiz() {
        new CountDownTimerAdapter(500) {
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
        ScaleAnimation scaleThree = new ScaleAnimation(0, 1, 0, 1, 1, 0.5f, 1, 0.5f);
        scaleThree.setDuration(250);
        scaleOne.setAnimationListener(new AnimationAdapter() {
            public void onAnimationStart(Animation animation) {
                onShowing = true;
            }

            public void onAnimationEnd(Animation animation) {
                layoutAnswer.setVisibility(View.VISIBLE);
                for (ViewGroup group : layouts) group.startAnimation(scaleThree);

                showQuiz();
                textViewQuestion.startAnimation(scaleTwo);
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
                        goNext(3);
                    }
                }.start();
                going++;
                gameStatusLayout.setGoingCount(going);
                gameStatusLayout.setGoingProgress(going);
            }
        });
        textViewQuestion.startAnimation(scaleOne);
        if (going >= QUIZ) goEndGame(ManagerBrain.CALCULATION, 1);
    }

    @Override
    protected void showQuiz() {
        Calculator core = ManagerGameData.getInstance().getCalculatorOne();
        answer = core.getResult();

        coreArrayAnswer = getAnswerArray(answer);
        answerA.setText("" + coreArrayAnswer[0]);
        answerB.setText("" + coreArrayAnswer[1]);
        answerC.setText("" + coreArrayAnswer[2]);

        textViewQuestion.setText(core.getCalculator());
    }

    private void goNext(int clicked) {
        if (!clickable) return;
        clickable = false;
        boolean completed = answer == coreArrayAnswer[clicked];
        if (clicked < 3) buttons[clicked].setBackgroundResource(bgrWrong); // When Time Out
        buttons[coreArrayAnswer[3]].setBackgroundResource(bgrCorrect);
        new CountDownTimerAdapter(1000) {
            public void onFinish() {
                ScaleAnimation scale = new ScaleAnimation(1, 0, 1, 0, 1, 0.5f, 1, 0.5f);
                scale.setDuration(250);
                scale.setAnimationListener(new AnimationAdapter() {
                    public void onAnimationEnd(Animation animation) {
                        layoutAnswer.setVisibility(View.INVISIBLE);
                    }
                });
                new CountDownTimerAdapter(500) {
                    @Override
                    public void onFinish() {
                        for (int i = 0; i < 3; i++) {
                            buttons[i].setBackgroundResource(bgrNormal);
                            layouts[i].startAnimation(scale);
                        }
                    }
                }.start();
            }
        }.start();
        goNext(completed);
    }

    @Override
    protected void goPause() {
        super.goPause();
        layoutAnswer.setVisibility(View.INVISIBLE);
    }

    @Override
    protected void onButtonResume() {
        super.onButtonResume();
        new CountDownTimerAdapter(450) {
            public void onFinish() {
                layoutAnswer.setVisibility(View.VISIBLE);
                AlphaAnimation goFadeShow = new AlphaAnimation(0, 1);
                goFadeShow.setDuration(300);
                layoutAnswer.startAnimation(goFadeShow);
            }
        }.start();
    }

    private int[] getAnswerArray(int x) {
        switch (new Random().nextInt(3)) {
            case 1:
                return new int[]{x, x + 1, x + 2, 0};
            case 2:
                return new int[]{x - 1, x, x + 1, 1};
            default:
                return new int[]{x - 2, x - 1, x, 2};
        }
    }
}
