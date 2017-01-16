package com.example.windzlord.brainfuck.screens.tabs;


import android.graphics.Typeface;
import android.net.Uri;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.akexorcist.roundcornerprogressbar.IconRoundCornerProgressBar;
import com.example.windzlord.brainfuck.R;
import com.example.windzlord.brainfuck.managers.ManagerBrain;
import com.example.windzlord.brainfuck.managers.ManagerFile;
import com.example.windzlord.brainfuck.managers.ManagerPreference;
import com.example.windzlord.brainfuck.objects.FragmentChanger;
import com.example.windzlord.brainfuck.screens.types.FragmentCalculation;
import com.example.windzlord.brainfuck.screens.types.FragmentConcentration;
import com.example.windzlord.brainfuck.screens.types.FragmentMemory;
import com.example.windzlord.brainfuck.screens.types.FragmentObservation;
import com.nostra13.universalimageloader.core.ImageLoader;

import org.greenrobot.eventbus.EventBus;

import java.io.File;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * A simple {@link Fragment} subclass.
 */
public class FragmentProfile extends Fragment {

    @BindView(R.id.progressbar_calcu)
    IconRoundCornerProgressBar barCalcu;

    @BindView(R.id.progressbar_concen)
    IconRoundCornerProgressBar barConcen;

    @BindView(R.id.progressbar_memory)
    IconRoundCornerProgressBar barMemory;

    @BindView(R.id.progressbar_obser)
    IconRoundCornerProgressBar barObser;

    @BindView(R.id.textView_calcu_score)
    TextView textViewCalcu;

    @BindView(R.id.textView_concen_score)
    TextView textViewConcen;

    @BindView(R.id.textView_memory_score)
    TextView textViewMemory;

    @BindView(R.id.textView_obser_score)
    TextView textViewObser;

    @BindView(R.id.imageView_user_avatar)
    ImageView imageViewUser;

    @BindView(R.id.textView_user_name)
    TextView textViewUser;

    @BindView(R.id.textView_high_score)
    TextView textViewScore;

    @BindView(R.id.button_setting)
    ImageView buttonSetting;

    public FragmentProfile() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout1 for this fragment
        View view = inflater.inflate(R.layout.tab_fragment_profile, container, false);
        settingThingsUp(view);

        return view;
    }

    private void settingThingsUp(View view) {
        ButterKnife.bind(this, view);

        setupUI();
        addListener();
    }

    public void setupUI() {
        Typeface font = Typeface.createFromAsset(getContext().getAssets(), "fonts/BreeSerif.otf");
        textViewUser.setTypeface(font);
        String userName = ManagerPreference.getInstance().getUserName();
        textViewUser.setText(userName.substring(2, userName.length() - 1));

        String userID = ManagerPreference.getInstance().getUserID();
        if (!userID.isEmpty()) {
            File file = ManagerFile.getInstance().loadImage(userID);
            ImageLoader.getInstance().displayImage(Uri.fromFile(file).toString(), imageViewUser);
        } else imageViewUser.setImageResource(R.drawable.z_character_guest);

        int neuron = 0;
        for (String game : ManagerBrain.GAME_LIST)
            for (int i = 1; i < 4; i++) {
                int level = ManagerPreference.getInstance().getLevel(game, i);
                int exp = ManagerPreference.getInstance().getExpCurrent(game, i);
                neuron += (level * (level - 1) / 2) * 300 + exp;
            }
        textViewScore.setText("Neuron " + neuron);

        getProgressbar();
    }

    private int scoreCalcu, scoreConcen, scoreMemory, scoreObser;

    public void getProgressbar() {
        scoreCalcu = scoreConcen = scoreMemory = scoreObser = 0;
        for (String game : ManagerBrain.GAME_LIST)
            for (int i = 1; i <= 3; i++) {
                scoreCalcu += game.equals(ManagerBrain.CALCULATION) ?
                        ManagerPreference.getInstance().getScore(game, i) : 0;
                scoreConcen += game.equals(ManagerBrain.CONCENTRATION) ?
                        ManagerPreference.getInstance().getScore(game, i) : 0;
                scoreMemory += game.equals(ManagerBrain.MEMORY) ?
                        ManagerPreference.getInstance().getScore(game, i) : 0;
                scoreObser += game.equals(ManagerBrain.OBSERVATION) ?
                        ManagerPreference.getInstance().getScore(game, i) : 0;
            }
        textViewCalcu.setText(scoreCalcu + "");
        textViewConcen.setText(scoreConcen + "");
        textViewObser.setText(scoreObser + "");
        textViewMemory.setText(scoreMemory + "");

        float f = 500;
        new CountDownTimer((long) f, 1) {
            @Override
            public void onTick(long l) {
                barCalcu.setProgress((f - l) / f * scoreCalcu * 10);
                barConcen.setProgress((f - l) / f * scoreConcen * 10);
                barMemory.setProgress((f - l) / f * scoreMemory * 10);
                barObser.setProgress((f - l) / f * scoreObser * 10);
            }

            @Override
            public void onFinish() {

            }
        }.start();
    }

    private void addListener() {
        buttonSetting.setOnClickListener((v) ->
                EventBus.getDefault().post(new FragmentChanger(new FragmentSetting(), true)));

        barCalcu.setOnIconClickListener(() ->
                EventBus.getDefault().post(new FragmentChanger(new FragmentCalculation(), true)));
        barConcen.setOnIconClickListener(() ->
                EventBus.getDefault().post(new FragmentChanger(new FragmentConcentration(), true)));
        barMemory.setOnIconClickListener(() ->
                EventBus.getDefault().post(new FragmentChanger(new FragmentMemory(), true)));
        barObser.setOnIconClickListener(() ->
                EventBus.getDefault().post(new FragmentChanger(new FragmentObservation(), true)));

        barCalcu.setOnClickListener((v) ->
                EventBus.getDefault().post(new FragmentChanger(new FragmentCalculation(), true)));
        barConcen.setOnClickListener((v) ->
                EventBus.getDefault().post(new FragmentChanger(new FragmentConcentration(), true)));
        barMemory.setOnClickListener((v) ->
                EventBus.getDefault().post(new FragmentChanger(new FragmentMemory(), true)));
        barObser.setOnClickListener((v) ->
                EventBus.getDefault().post(new FragmentChanger(new FragmentObservation(), true)));
    }
}
