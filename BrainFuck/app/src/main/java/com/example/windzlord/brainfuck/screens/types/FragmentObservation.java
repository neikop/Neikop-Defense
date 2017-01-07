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
import com.example.windzlord.brainfuck.screens.games.observation.ObserverOne;
import com.example.windzlord.brainfuck.screens.games.observation.ObserverThree;
import com.example.windzlord.brainfuck.screens.games.observation.ObserverTwo;

import org.greenrobot.eventbus.EventBus;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * A simple {@link Fragment} subclass.
 */
public class FragmentObservation extends Fragment {

    @BindView(R.id.game_obser_one)
    GameLayout gameObserOne;

    @BindView(R.id.game_obser_two)
    GameLayout gameObserTwo;

    @BindView(R.id.game_obser_three)
    GameLayout gameObserThree;


    public FragmentObservation() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.game_zipe_observation, container, false);
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
        GameLayout[] games = {gameObserOne, gameObserTwo, gameObserThree};

        for (int i = 0; i < games.length; i++) {
            games[i].setScore(ManagerPreference.getInstance().getScore(Gogo.OBSERVATION, i + 1));
            games[i].setLevel(ManagerPreference.getInstance().getLevel(Gogo.OBSERVATION, i + 1));
            games[i].setExpNextLvl(ManagerPreference.getInstance().getExpNext(Gogo.OBSERVATION, i + 1));
            games[i].setExpCurrent(ManagerPreference.getInstance().getExpCurrent(Gogo.OBSERVATION, i + 1));
            games[i].setUnlocked(ManagerPreference.getInstance().isUnlocked(Gogo.OBSERVATION, i + 1));
        }

    }

    @OnClick(R.id.button_back)
    public void onBackPressed() {
        getActivity().onBackPressed();
    }

    @OnClick(R.id.game_obser_one)
    public void goGameObserOne() {
        if (gameObserOne.isUnlocked())
            EventBus.getDefault().post(new FragmentChanger(new ObserverOne(), true));
    }

    @OnClick(R.id.game_obser_two)
    public void goGameObserTwo() {
        if (gameObserTwo.isUnlocked())
            EventBus.getDefault().post(new FragmentChanger(new ObserverTwo(), true));
    }

    @OnClick(R.id.game_obser_three)
    public void goGameObserThree() {
        if (gameObserThree.isUnlocked())
            EventBus.getDefault().post(new FragmentChanger(new ObserverThree(), true));
    }

}