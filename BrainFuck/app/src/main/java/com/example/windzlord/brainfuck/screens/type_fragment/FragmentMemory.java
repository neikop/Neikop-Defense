package com.example.windzlord.brainfuck.screens.type_fragment;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.windzlord.brainfuck.R;
import com.example.windzlord.brainfuck.objects.even_bus.FragmentChanger;
import com.example.windzlord.brainfuck.screens.FragmentMain;
import com.example.windzlord.brainfuck.screens.game_fragment.MemoryOne;
import com.example.windzlord.brainfuck.screens.game_fragment.MemoryThree;
import com.example.windzlord.brainfuck.screens.game_fragment.MemoryTwo;

import org.greenrobot.eventbus.EventBus;

import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * A simple {@link Fragment} subclass.
 */
public class FragmentMemory extends Fragment {


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
    }

    @OnClick(R.id.button_back)
    public void onBackPressed() {
        getActivity().onBackPressed();
    }

    @OnClick(R.id.game_memory_one)
    public void goGameMemoryOne() {
        EventBus.getDefault().post(new FragmentChanger(new MemoryOne(), true));
    }

    @OnClick(R.id.game_memory_two)
    public void goGameMemoryTwo() {
        EventBus.getDefault().post(new FragmentChanger(new MemoryTwo(), true));
    }

    @OnClick(R.id.game_memory_three)
    public void goGameMemoryThree() {
        EventBus.getDefault().post(new FragmentChanger(new MemoryThree(), true));
    }

    @OnClick(R.id.game_memory_four)
    public void goGameMemoryFour() {

    }

}
