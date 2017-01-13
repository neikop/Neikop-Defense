package com.example.windzlord.brainfuck.screens.types;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.windzlord.brainfuck.R;
import com.example.windzlord.brainfuck.adapters.CountDownTimerAdapter;
import com.example.windzlord.brainfuck.layout.GameCoverLayout;
import com.example.windzlord.brainfuck.managers.ManagerBrain;
import com.example.windzlord.brainfuck.managers.ManagerPreference;
import com.example.windzlord.brainfuck.objects.FragmentChanger;
import com.example.windzlord.brainfuck.screens.games.concentration.ConcenOne;
import com.example.windzlord.brainfuck.screens.games.concentration.ConcenThree;
import com.example.windzlord.brainfuck.screens.games.concentration.ConcenTwo;

import org.greenrobot.eventbus.EventBus;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * A simple {@link Fragment} subclass.
 */
public class FragmentConcentration extends Fragment {

    @BindView(R.id.game_concen_one)
    GameCoverLayout gameConcenOne;

    @BindView(R.id.game_concen_two)
    GameCoverLayout gameConcenTwo;

    @BindView(R.id.game_concen_three)
    GameCoverLayout gameConcenThree;


    public FragmentConcentration() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.game_zipe_concentration, container, false);
        settingThingsUp(view);

        return view;
    }

    private void settingThingsUp(View view) {
        ButterKnife.bind(this, view);

        new CountDownTimerAdapter(10) {
            public void onFinish() {
                getInfo();
            }
        }.start();
    }

    private void getInfo() {
        GameCoverLayout[] games = {gameConcenOne, gameConcenTwo, gameConcenThree};
        for (int i = 0; i < games.length; i++) {
            games[i].setScore(ManagerPreference.getInstance().getScore(ManagerBrain.CONCENTRATION, i + 1));
            games[i].setLevel(ManagerPreference.getInstance().getLevel(ManagerBrain.CONCENTRATION, i + 1));
            games[i].setExpNextLvl(ManagerPreference.getInstance().getExpNext(ManagerBrain.CONCENTRATION, i + 1));
            games[i].setExpCurrent(ManagerPreference.getInstance().getExpCurrent(ManagerBrain.CONCENTRATION, i + 1));
            games[i].setUnlocked(ManagerPreference.getInstance().isUnlocked(ManagerBrain.CONCENTRATION, i + 1));
        }
    }

    @OnClick(R.id.button_back)
    public void onBackPressed() {
        getActivity().onBackPressed();
    }

    @OnClick(R.id.game_concen_one)
    public void goGameConcenOne() {
        if (gameConcenOne.isUnlocked())
            EventBus.getDefault().post(new FragmentChanger(new ConcenOne(), true));
    }

    @OnClick(R.id.game_concen_two)
    public void goGameConcenTwo() {
        if (gameConcenTwo.isUnlocked())
            EventBus.getDefault().post(new FragmentChanger(new ConcenTwo(), true));
    }

    @OnClick(R.id.game_concen_three)
    public void goGameConcenThree() {
        if (gameConcenThree.isUnlocked())
            EventBus.getDefault().post(new FragmentChanger(new ConcenThree(), true));
    }

}