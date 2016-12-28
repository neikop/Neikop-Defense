package com.example.windzlord.brainfuck.screens.type_fragment;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.windzlord.brainfuck.R;
import com.example.windzlord.brainfuck.objects.even_bus.FragmentChanger;
import com.example.windzlord.brainfuck.screens.game_fragment.MemoryOne;
import com.example.windzlord.brainfuck.screens.game_fragment.MemoryThree;
import com.example.windzlord.brainfuck.screens.game_fragment.MemoryTwo;
import com.example.windzlord.brainfuck.screens.game_fragment.ObserverOne;
import com.example.windzlord.brainfuck.screens.game_fragment.ObserverThree;
import com.example.windzlord.brainfuck.screens.game_fragment.ObserverTwo;

import org.greenrobot.eventbus.EventBus;

import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * A simple {@link Fragment} subclass.
 */
public class FragmentObservation extends Fragment {


    public FragmentObservation() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.e_type_fragment_observation, container, false);
        settingThingsUp(view);

        return view;
    }

    private void settingThingsUp(View view) {
        ButterKnife.bind(this, view);
    }

    @OnClick(R.id.button_back)
    public void onBackPressed() {
        getActivity().onBackPressed();
    }

    @OnClick(R.id.game_obser_one)
    public void goGameObserOne() {
        EventBus.getDefault().post(new FragmentChanger(new ObserverOne(), true));
    }

    @OnClick(R.id.game_obser_two)
    public void goGameObserTwo() {
        EventBus.getDefault().post(new FragmentChanger(new ObserverTwo(), true));
    }

    @OnClick(R.id.game_obser_three)
    public void goGameObserThree() {
        EventBus.getDefault().post(new FragmentChanger(new ObserverThree(), true));
    }

    @OnClick(R.id.game_obser_four)
    public void goGameObserFour() {

    }

    @OnClick(R.id.game_obser_five)
    public void goGameObserFive() {

    }

}