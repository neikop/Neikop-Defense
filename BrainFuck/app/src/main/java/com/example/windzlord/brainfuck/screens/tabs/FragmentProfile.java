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
import com.example.windzlord.brainfuck.managers.FileManager;
import com.example.windzlord.brainfuck.managers.Gogo;
import com.example.windzlord.brainfuck.managers.ManagerPreference;
import com.example.windzlord.brainfuck.managers.ManagerServer;
import com.example.windzlord.brainfuck.managers.ManagerUserData;
import com.example.windzlord.brainfuck.objects.models.HighScore;
import com.nostra13.universalimageloader.core.ImageLoader;

import java.io.File;
import java.util.List;
import java.util.Random;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * A simple {@link Fragment} subclass.
 */
public class FragmentProfile extends Fragment {
    @BindView(R.id.progressbar_calculation)
    IconRoundCornerProgressBar progressbar_calculation;
    @BindView(R.id.progressbar_concen)
    IconRoundCornerProgressBar progressbar_concen;
    @BindView(R.id.progressbar_memory)
    IconRoundCornerProgressBar progressbar_memory;
    @BindView(R.id.progressbar_observation)
    IconRoundCornerProgressBar progressbar_observation;


    @BindView(R.id.textView_user_name)
    TextView user_name;

    @BindView(R.id.tv_high_score)
    TextView tv_high_score;

    @BindView(R.id.image_user)
    ImageView image_user;

    @BindView(R.id.tv_calcu_score)
    TextView tv_calcu_score;
    @BindView(R.id.tv_concen_score)
    TextView tv_concen_score;
    @BindView(R.id.tv_observer_score)
    TextView tv_observer_score;
    @BindView(R.id.tv_memory_score)
    TextView tv_memory_score;

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

    public void getProgressbar() {
        int maxCalcu = 0;
        int maxConcen = 0;
        int maxMemo = 0;
        int maxObser = 0;

        for (String game : Gogo.GAME_LIST) {
            for (int i = 1; i < 4; i++) {
                switch (game) {
                    case Gogo.CALCULATION:
                        maxCalcu += ManagerPreference.getInstance().getScore(game, i);
                        break;
                    case Gogo.CONCENTRATION:
                        maxConcen += ManagerPreference.getInstance().getScore(game, i);
                        break;
                    case Gogo.MEMORY:
                        maxMemo += ManagerPreference.getInstance().getScore(game, i);
                        break;
                    case Gogo.OBSERVATION:
                        maxObser += ManagerPreference.getInstance().getScore(game, i);
                        break;
                }
            }
        }


        int finalMaxCalcu = maxCalcu * 10;
        int finalMaxConcen = maxConcen * 10;
        int finalMaxMemo = maxMemo * 10;
        int finalMaxObser = maxObser * 10;
        new CountDownTimer(2000, 1) {
            @Override
            public void onTick(long millisUntilFinished) {
                progressbar_calculation.setProgress((int) (finalMaxCalcu - millisUntilFinished));
                progressbar_concen.setProgress((int) (finalMaxConcen - millisUntilFinished));
                progressbar_memory.setProgress((int) (finalMaxMemo - millisUntilFinished));
                progressbar_observation.setProgress((int) (finalMaxObser - millisUntilFinished));
            }

            @Override
            public void onFinish() {

            }
        }.start();

        tv_calcu_score.setText(maxCalcu + "");
        tv_concen_score.setText(maxConcen + "");
        tv_observer_score.setText(maxObser + "");
        tv_memory_score.setText(maxMemo + "");
    }


    public void setupUI() {
        Typeface font = Typeface.createFromAsset(getContext().getAssets(), "fonts/BreeSerif.otf");
        user_name.setTypeface(font);
        user_name.setText(ManagerPreference.getInstance().getUserName());
        String userID = ManagerPreference.getInstance().getUserID();
        if (!userID.equals("")) {
            File file = FileManager.getInstance().loadImage(userID);
            ImageLoader.getInstance().displayImage(
                    Uri.fromFile(file).toString(),
                    image_user
            );
        } else {
            image_user.setImageResource(R.drawable.z_character_guest);
        }

        int total = 0;
        for (String game : Gogo.GAME_LIST) {
            for (int i = 1; i < 4; i++) {
                int lv = ManagerPreference.getInstance().getLevel(game, i);
                total += (lv * (lv - 1) / 2) * 300 + ManagerPreference.getInstance().getScore(game, i);
            }
        }

        tv_high_score.setText("High Score: " + total);
    }
}
