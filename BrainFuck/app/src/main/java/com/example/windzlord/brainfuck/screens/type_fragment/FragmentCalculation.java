package com.example.windzlord.brainfuck.screens.type_fragment;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationSet;
import android.view.animation.AnimationUtils;
import android.view.animation.ScaleAnimation;
import android.view.animation.TranslateAnimation;
import android.widget.FrameLayout;

import com.example.windzlord.brainfuck.R;
import com.example.windzlord.brainfuck.objects.even_bus.FragmentChanger;
import com.example.windzlord.brainfuck.screens.FragmentMain;
import com.example.windzlord.brainfuck.screens.game_fragment.CalcuOne;
import com.example.windzlord.brainfuck.screens.game_fragment.CalcuThree;
import com.example.windzlord.brainfuck.screens.game_fragment.CalcuTwo;

import org.greenrobot.eventbus.EventBus;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * A simple {@link Fragment} subclass.
 */
public class FragmentCalculation extends Fragment {

    @BindView(R.id.game_calcu_one)
    View gameCalcuOne;

    @BindView(R.id.game_calcu_two)
    View gameCalcuTwo;

    @BindView(R.id.game_calcu_three)
    View gameCalcuThree;


    public FragmentCalculation() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.e_type_fragment_calculation, container, false);
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

    @OnClick(R.id.game_calcu_one)
    public void goGameMemoryOne() {
        EventBus.getDefault().post(new FragmentChanger(new CalcuOne(), true));
    }

    @OnClick(R.id.game_calcu_two)
    public void goGameMemoryTwo() {
        EventBus.getDefault().post(new FragmentChanger(new CalcuTwo(), true));
    }

    @OnClick(R.id.game_calcu_three)
    public void goGameMemoryThree() {
        EventBus.getDefault().post(new FragmentChanger(new CalcuThree(), true));
    }

}