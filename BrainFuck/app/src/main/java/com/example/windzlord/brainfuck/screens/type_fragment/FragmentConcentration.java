package com.example.windzlord.brainfuck.screens.type_fragment;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.windzlord.brainfuck.R;
import com.example.windzlord.brainfuck.objects.even_bus.FragmentChanger;
import com.example.windzlord.brainfuck.screens.FragmentMain;
import com.example.windzlord.brainfuck.screens.game_fragment.ConcenOne;
import com.example.windzlord.brainfuck.screens.game_fragment.ConcenThree;
import com.example.windzlord.brainfuck.screens.game_fragment.ConcenTwo;

import org.greenrobot.eventbus.EventBus;

import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * A simple {@link Fragment} subclass.
 */
public class FragmentConcentration extends Fragment {


    public FragmentConcentration() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.e_type_fragment_concentration, container, false);
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

    @OnClick(R.id.game_concen_one)
    public void goGameConcenOne() {
        EventBus.getDefault().post(new FragmentChanger(new ConcenOne(), true));
    }

    @OnClick(R.id.game_concen_two)
    public void goGameConcenTwo() {
        EventBus.getDefault().post(new FragmentChanger(new ConcenTwo(), true));
    }

    @OnClick(R.id.game_concen_three)
    public void goGameConcenThree() {
        EventBus.getDefault().post(new FragmentChanger(new ConcenThree(), true));
    }

}