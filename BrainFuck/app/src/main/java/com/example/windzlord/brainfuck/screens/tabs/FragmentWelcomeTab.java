package com.example.windzlord.brainfuck.screens.tabs;


import android.os.Bundle;
import android.os.CountDownTimer;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.akexorcist.roundcornerprogressbar.IconRoundCornerProgressBar;
import com.example.windzlord.brainfuck.R;
import com.example.windzlord.brainfuck.managers.ManagerBrain;
import com.example.windzlord.brainfuck.managers.ManagerPreference;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * A simple {@link Fragment} subclass.
 */
public class FragmentWelcomeTab extends Fragment {

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

    @BindView(R.id.textView_obser_score)
    TextView textViewObser;

    @BindView(R.id.textView_memory_score)
    TextView textViewMemory;

    public FragmentWelcomeTab() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.tab_fragment_welcome, container, false);
        settingThingsUp(view);

        return view;
    }

    private void settingThingsUp(View view) {
        ButterKnife.bind(this, view);
        getProgressbar();
    }

    private int scoreCalcu = 0;
    private int scoreConcen = 0;
    private int scoreMemo = 0;
    private int scoreObser = 0;

    public void getProgressbar() {
        for (String game : ManagerBrain.GAME_LIST)
            for (int i = 1; i <= 3; i++) {
                scoreCalcu += game.equals(ManagerBrain.CALCULATION) ?
                        ManagerPreference.getInstance().getScore(game, i) : 0;
                scoreConcen += game.equals(ManagerBrain.CONCENTRATION) ?
                        ManagerPreference.getInstance().getScore(game, i) : 0;
                scoreMemo += game.equals(ManagerBrain.MEMORY) ?
                        ManagerPreference.getInstance().getScore(game, i) : 0;
                scoreObser += game.equals(ManagerBrain.OBSERVATION) ?
                        ManagerPreference.getInstance().getScore(game, i) : 0;
            }
        textViewCalcu.setText(scoreCalcu + "");
        textViewConcen.setText(scoreConcen + "");
        textViewObser.setText(scoreObser + "");
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
