package com.example.windzlord.brainfuck.screens.type_fragment;


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
import com.example.windzlord.brainfuck.screens.game_fragment.MemoryFour;
import com.example.windzlord.brainfuck.screens.game_fragment.MemoryOne;
import com.example.windzlord.brainfuck.screens.game_fragment.MemoryThree;
import com.example.windzlord.brainfuck.screens.game_fragment.MemoryTwo;

import org.greenrobot.eventbus.EventBus;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * A simple {@link Fragment} subclass.
 */
public class FragmentMemory extends Fragment {

    @BindView(R.id.game_memory_one)
    GameLayout gameMemoryOne;

    @BindView(R.id.game_memory_two)
    GameLayout gameMemoryTwo;

    @BindView(R.id.game_memory_three)
    GameLayout gameMemoryThree;

    @BindView(R.id.game_memory_four)
    GameLayout gameMemoryFour;


    public FragmentMemory() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.e_type_fragment_memory, container, false);
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
        GameLayout[] games = {gameMemoryOne, gameMemoryTwo, gameMemoryThree, gameMemoryFour};
        for (int i = 0; i < games.length; i++) {
            games[i].setScore(ManagerPreference.getInstance().getScore(Gogo.MEMORY, i + 1));
            games[i].setLevel(ManagerPreference.getInstance().getLevel(Gogo.MEMORY, i + 1));
            games[i].setExpNextLvl(ManagerPreference.getInstance().getExpNext(Gogo.MEMORY, i + 1));
            games[i].setExpCurrent(ManagerPreference.getInstance().getExpCurrent(Gogo.MEMORY, i + 1));
            games[i].setUnlocked(ManagerPreference.getInstance().isUnlocked(Gogo.MEMORY, i + 1));
        }
    }

    @OnClick(R.id.button_back)
    public void onBackPressed() {
        getActivity().onBackPressed();
    }

    @OnClick(R.id.game_memory_one)
    public void goGameMemoryOne() {
        if (gameMemoryOne.isUnlocked()) {
            EventBus.getDefault().post(new FragmentChanger(new MemoryOne(), true));
        }
    }

    @OnClick(R.id.game_memory_two)
    public void goGameMemoryTwo() {
        if (gameMemoryTwo.isUnlocked())
            EventBus.getDefault().post(new FragmentChanger(new MemoryTwo(), true));
    }

    @OnClick(R.id.game_memory_three)
    public void goGameMemoryThree() {
        if (gameMemoryThree.isUnlocked())
            EventBus.getDefault().post(new FragmentChanger(new MemoryThree(), true));
    }

    @OnClick(R.id.game_memory_four)
    public void goGameMemoryFour() {
        if (gameMemoryFour.isUnlocked())
            EventBus.getDefault().post(new FragmentChanger(new MemoryFour(), true));
    }

}
