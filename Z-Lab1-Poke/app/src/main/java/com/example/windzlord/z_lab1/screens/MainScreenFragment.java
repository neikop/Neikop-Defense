package com.example.windzlord.z_lab1.screens;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.windzlord.z_lab1.objects.FragmentEvent;
import com.example.windzlord.z_lab1.R;
import com.example.windzlord.z_lab1.controllers.Gogo;
import com.example.windzlord.z_lab1.managers.Preference;

import org.greenrobot.eventbus.EventBus;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;


/**
 * A simple {@link Fragment} subclass.
 */
public class MainScreenFragment extends Fragment {

    @BindView(R.id.imageView_playButton)
    ImageView buttonPlay;

    @BindView(R.id.imageView_settingButton)
    ImageView buttonSetting;

    @BindView(R.id.textView_main_currentScore)
    TextView textViewMainCurrentScore;

    @BindView(R.id.textView_main_highScore)
    TextView textViewMainHighScore;

    @BindView(R.id.textView_main_highScoreLabel)
    TextView textViewMainHighScoreLabel;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the custom_toast_xml for this fragment
        View view = inflater.inflate(R.layout.fragment_main_screen, container, false);
        settingThingsUp(view);

        return view;
    }

    private void settingThingsUp(View view) {
        ButterKnife.bind(this, view);
        getContent();
    }

    private void getContent() {

        Gogo.goSetFontTextView(getActivity(), textViewMainCurrentScore, "fonts/StencilStd.ttf");
        Gogo.goSetFontTextView(getActivity(), textViewMainHighScore, "fonts/StencilStd.ttf");
//        Gogo.goSetFontTextView(getActivity(), textViewMainHighScoreLabel, "fonts/PoplarStd.ttf");

        textViewMainCurrentScore.setText(Preference.getInstance().getScore() + "");
        textViewMainHighScore.setText(Preference.getInstance().getHighScore() + "");
    }

    @OnClick(R.id.imageView_playButton)
    public void onClickPlay() {
        EventBus.getDefault().post(new FragmentEvent(
                new GameScreenFragment(), true
        ));
    }

    @OnClick(R.id.imageView_settingButton)
    public void onClickSetting() {
        EventBus.getDefault().post(new FragmentEvent(
                new SettingScreenFragment(), true
        ));
    }

}
