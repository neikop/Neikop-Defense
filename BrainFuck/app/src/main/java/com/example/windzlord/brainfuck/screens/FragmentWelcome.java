package com.example.windzlord.brainfuck.screens;


import android.media.MediaPlayer;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.SeekBar;

import com.example.windzlord.brainfuck.R;
import com.example.windzlord.brainfuck.objects.FragmentChanger;

import org.greenrobot.eventbus.EventBus;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * A simple {@link Fragment} subclass.
 */
public class FragmentWelcome extends Fragment {

    @BindView(R.id.progressBar_welcome)
    SeekBar progressBarWelcome;
    MediaPlayer mediaPlayer = new MediaPlayer();

    public FragmentWelcome() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_welcome, container, false);
        settingThingsUp(view);

        return view;
    }

    private void settingThingsUp(View view) {
        ButterKnife.bind(this, view);

        getContent();
    }

    private void getContent() {
//        mediaPlayer = MediaPlayer.create(getContext(), R.raw.welcome);
//        mediaPlayer.start();
        progressBarWelcome.setMax(3200);
        progressBarWelcome.setProgress(0);
        new CountDownTimer(3500, 1) {

            @Override
            public void onTick(long millisUntilFinished) {
                progressBarWelcome.setProgress((int) (3200 - millisUntilFinished));
            }

            @Override
            public void onFinish() {
                EventBus.getDefault().post(new FragmentChanger(new FragmentMain(), false));
            }
        }.start();
    }

}
