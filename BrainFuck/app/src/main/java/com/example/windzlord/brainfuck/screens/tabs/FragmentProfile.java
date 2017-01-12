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
import com.example.windzlord.brainfuck.managers.Gogo;
import com.example.windzlord.brainfuck.managers.ManagerFile;
import com.example.windzlord.brainfuck.managers.ManagerPreference;
import com.nostra13.universalimageloader.core.ImageLoader;

import java.io.File;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * A simple {@link Fragment} subclass.
 */
public class FragmentProfile extends Fragment {

    @BindView(R.id.progressbar_calculation)
    IconRoundCornerProgressBar barCalcu;

    @BindView(R.id.progressbar_concen)
    IconRoundCornerProgressBar barConcen;

    @BindView(R.id.progressbar_memory)
    IconRoundCornerProgressBar barMemory;

    @BindView(R.id.progressbar_observation)
    IconRoundCornerProgressBar barObser;

    @BindView(R.id.imageView_user_avatar)
    ImageView imageViewUser;

    @BindView(R.id.textView_user_name)
    TextView textViewUser;

    @BindView(R.id.textView_high_score)
    TextView textViewScore;

    @BindView(R.id.textView_calcu_score)
    TextView textViewCalcu;

    @BindView(R.id.textView_concen_score)
    TextView textViewConcen;

    @BindView(R.id.textView_observer_score)
    TextView textViewObserver;

    @BindView(R.id.textView_memory_score)
    TextView textViewMemory;

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
        getProgressbar();
    }

    public void setupUI() {
        Typeface font = Typeface.createFromAsset(getContext().getAssets(), "fonts/BreeSerif.otf");
        textViewUser.setTypeface(font);
        String userName = ManagerPreference.getInstance().getUserName();
        if (userName.contains("N'"))
            textViewUser.setText(userName.substring(2, userName.length() - 1));
        else textViewUser.setText(userName);

        String userID = ManagerPreference.getInstance().getUserID();
        if (!userID.equals("")) {
            File file = ManagerFile.getInstance().loadImage(userID);
            ImageLoader.getInstance().displayImage(Uri.fromFile(file).toString(), imageViewUser);
        } else imageViewUser.setImageResource(R.drawable.z_character_guest);

        int score = 0;
        for (String game : Gogo.GAME_LIST) {
            for (int i = 1; i < 4; i++) {
                int level = ManagerPreference.getInstance().getLevel(game, i);
                int exp = ManagerPreference.getInstance().getExpCurrent(game, i);
                score += (level * (level - 1) / 2) * 300 + exp;
            }
        }
        textViewScore.setText("Neuron: " + score);
    }

    private int scoreCalcu = 0;
    private int scoreConcen = 0;
    private int scoreMemo = 0;
    private int scoreObser = 0;

    public void getProgressbar() {
        for (String game : Gogo.GAME_LIST)
            for (int i = 1; i <= 3; i++) {
                scoreCalcu += game.equals(Gogo.CALCULATION) ?
                        ManagerPreference.getInstance().getScore(game, i) : 0;
                scoreConcen += game.equals(Gogo.CONCENTRATION) ?
                        ManagerPreference.getInstance().getScore(game, i) : 0;
                scoreMemo += game.equals(Gogo.MEMORY) ?
                        ManagerPreference.getInstance().getScore(game, i) : 0;
                scoreObser += game.equals(Gogo.OBSERVATION) ?
                        ManagerPreference.getInstance().getScore(game, i) : 0;
            }
        textViewCalcu.setText(scoreCalcu + "");
        textViewConcen.setText(scoreConcen + "");
        textViewObserver.setText(scoreObser + "");
        textViewMemory.setText(scoreMemo + "");

        float f = 500;
        new CountDownTimer((long) f, 1) {
            @Override
            public void onTick(long l) {
                barCalcu.setProgress((f - l) / f * scoreCalcu * 10);
                barConcen.setProgress((f - l) / f * scoreConcen * 10);
                barMemory.setProgress((f - l) / f * scoreMemo * 10);
                barObser.setProgress((f - l) / f * scoreObser * 10);
            }

            @Override
            public void onFinish() {

            }
        }.start();
    }

}
