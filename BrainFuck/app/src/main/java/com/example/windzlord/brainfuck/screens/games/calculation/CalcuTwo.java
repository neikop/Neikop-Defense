package com.example.windzlord.brainfuck.screens.games.calculation;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.windzlord.brainfuck.R;
import com.example.windzlord.brainfuck.adapters.CountDownTimerAdapter;
import com.example.windzlord.brainfuck.screens.games.NeikopzGame;

/**
 * A simple {@link Fragment} subclass.
 */
public class CalcuTwo extends NeikopzGame {


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
        new CountDownTimerAdapter(500, 1) {
            public void onFinish() {
                goShow();
            }
        }.start();
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

    }

    @Override
    protected void showQuiz() {

    }

}
