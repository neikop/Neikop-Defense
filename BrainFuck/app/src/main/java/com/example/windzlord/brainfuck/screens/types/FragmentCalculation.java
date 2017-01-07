package com.example.windzlord.brainfuck.screens.types;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.windzlord.brainfuck.R;
import com.example.windzlord.brainfuck.adapters.CountDownTimerAdapter;
import com.example.windzlord.brainfuck.layout.GameLayout;
import com.example.windzlord.brainfuck.managers.Gogo;
import com.example.windzlord.brainfuck.managers.ManagerPreference;
import com.example.windzlord.brainfuck.objects.FragmentChanger;
import com.example.windzlord.brainfuck.screens.games.calculation.CalcuOne;
import com.example.windzlord.brainfuck.screens.games.calculation.CalcuThree;
import com.example.windzlord.brainfuck.screens.games.calculation.CalcuTwo;

import org.greenrobot.eventbus.EventBus;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * A simple {@link Fragment} subclass.
 */
public class FragmentCalculation extends Fragment {

    @BindView(R.id.game_calcu_one)
    GameLayout gameCalcuOne;

    @BindView(R.id.game_calcu_two)
    GameLayout gameCalcuTwo;

    @BindView(R.id.game_calcu_three)
    GameLayout gameCalcuThree;


    public FragmentCalculation() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment

        View view = inflater.inflate(R.layout.game_zipe_calculation, container, false);

        settingThingsUp(view);

        return view;
    }

    private void settingThingsUp(View view) {
        ButterKnife.bind(this, view);

        new CountDownTimerAdapter(10, 1) {
            public void onFinish() {
                getInfo();
            }
        }.start();
    }

    private void getInfo() {
        GameLayout[] games = {gameCalcuOne, gameCalcuTwo, gameCalcuThree};

        for (int i = 0; i < games.length; i++) {
            games[i].setScore(ManagerPreference.getInstance().getScore(Gogo.CALCULATION, i + 1));
            games[i].setLevel(ManagerPreference.getInstance().getLevel(Gogo.CALCULATION, i + 1));
            games[i].setExpNextLvl(ManagerPreference.getInstance().getExpNext(Gogo.CALCULATION, i + 1));
            games[i].setExpCurrent(ManagerPreference.getInstance().getExpCurrent(Gogo.CALCULATION, i + 1));
            games[i].setUnlocked(ManagerPreference.getInstance().isUnlocked(Gogo.CALCULATION, i + 1));
        }


    }

    @OnClick(R.id.button_back)
    public void onBackPressed() {
        getActivity().onBackPressed();
    }

    @OnClick(R.id.game_calcu_one)
    public void goGameMemoryOne() {
        if (gameCalcuOne.isUnlocked())
            EventBus.getDefault().post(new FragmentChanger(new CalcuOne(), true));
    }

    @OnClick(R.id.game_calcu_two)
    public void goGameMemoryTwo() {
        if (gameCalcuTwo.isUnlocked())
            EventBus.getDefault().post(new FragmentChanger(new CalcuTwo(), true));
    }

    @OnClick(R.id.game_calcu_three)
    public void goGameMemoryThree() {
        if (gameCalcuThree.isUnlocked())
            EventBus.getDefault().post(new FragmentChanger(new CalcuThree(), true));
    }

}