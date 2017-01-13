package com.example.windzlord.brainfuck.screens;


import android.content.res.AssetFileDescriptor;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.SeekBar;

import com.example.windzlord.brainfuck.R;
import com.example.windzlord.brainfuck.objects.FragmentChanger;

import org.greenrobot.eventbus.EventBus;

import java.io.IOException;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * A simple {@link Fragment} subclass.
 */
public class FragmentWelcome extends Fragment {

    @BindView(R.id.progressBar_welcome)
    SeekBar progressBarWelcome;

    @BindView(R.id.imageView_welcome)
    ImageView imageViewWelcome;

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
//        MediaPlayer.create(getContext(), R.raw.welcome).start();
        progressBarWelcome.setMax(3000);
        startSound("sounds/welcome.mp3", false);
        progressBarWelcome.setMax(2800);
        progressBarWelcome.setProgress(0);
        new CountDownTimer(3200, 1) {

            @Override
            public void onTick(long millisUntilFinished) {
                progressBarWelcome.setProgress((int) (3000 - millisUntilFinished));
            }

            @Override
            public void onFinish() {
                imageViewWelcome.setVisibility(View.INVISIBLE);
                EventBus.getDefault().post(new FragmentChanger(new FragmentMain(), false));
            }
        }.start();
    }

    public void startSound(String filename, boolean loop) {
        AssetFileDescriptor afd = null;
        try {
            afd = getActivity().getAssets().openFd(filename);
            MediaPlayer player = new MediaPlayer();
            player.setDataSource(afd.getFileDescriptor(), afd.getStartOffset(), afd.getLength());
            player.prepare();
            player.setLooping(loop);
            player.start();
        } catch (IOException e) {
        }

    }
}
