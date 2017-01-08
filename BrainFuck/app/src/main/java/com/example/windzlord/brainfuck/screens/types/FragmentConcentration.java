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
    GameLayout gameConcenOne;

    @BindView(R.id.game_concen_two)
    GameLayout gameConcenTwo;

    @BindView(R.id.game_concen_three)
    GameLayout gameConcenThree;


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

        new CountDownTimerAdapter(10, 1) {
            public void onFinish() {
                getInfo();
            }
        }.start();
    }

    private void getInfo() {
        GameLayout[] games = {gameConcenOne, gameConcenTwo, gameConcenThree};

        for (int i = 0; i < games.length; i++) {
            games[i].setScore(ManagerPreference.getInstance().getScore(Gogo.CONCENTRATION, i + 1));
            games[i].setLevel(ManagerPreference.getInstance().getLevel(Gogo.CONCENTRATION, i + 1));
            games[i].setExpNextLvl(ManagerPreference.getInstance().getExpNext(Gogo.CONCENTRATION, i + 1));
            games[i].setExpCurrent(ManagerPreference.getInstance().getExpCurrent(Gogo.CONCENTRATION, i + 1));
            games[i].setUnlocked(ManagerPreference.getInstance().isUnlocked(Gogo.CONCENTRATION, i + 1));
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