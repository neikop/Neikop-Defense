package com.example.windzlord.brainfuck.screens.game_fragment;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.windzlord.brainfuck.R;

/**
 * A simple {@link Fragment} subclass.
 */
public class MemoryTwo extends Fragment {


    public MemoryTwo() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.f_game_memory_two, container, false);
    }

}
