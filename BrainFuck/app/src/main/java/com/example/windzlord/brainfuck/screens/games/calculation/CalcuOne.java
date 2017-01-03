package com.example.windzlord.brainfuck.screens.games.calculation;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.windzlord.brainfuck.R;
import com.example.windzlord.brainfuck.adapters.CountDownTimerAdapter;
import com.example.windzlord.brainfuck.managers.ManagerPreference;
import com.example.windzlord.brainfuck.screens.games.NeikopzGame;

/**
 * A simple {@link Fragment} subclass.
 */
public class CalcuOne extends NeikopzGame {


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
    protected void goPrepare() {
        new CountDownTimerAdapter(500, 1) {
            public void onFinish() {
                goShow();
            }
        }.start();
    }

    @Override
    protected void prepareQuiz() {

    }

    @Override
    protected void goShow() {

    }

    @Override
    protected void showQuiz() {

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

}
