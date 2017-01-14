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
import com.nostra13.universalimageloader.core.ImageLoader;

import org.greenrobot.eventbus.EventBus;

import java.io.File;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * A simple {@link Fragment} subclass.
 */
public class FragmentProfile extends Fragment {


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

        addListener();
        setupUI();
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
        for (String game : ManagerBrain.GAME_LIST) {
            for (int i = 1; i < 4; i++) {
                int level = ManagerPreference.getInstance().getLevel(game, i);
                int exp = ManagerPreference.getInstance().getExpCurrent(game, i);
                neuron += (level * (level - 1) / 2) * 300 + exp;
            }
        }
        textViewScore.setText("Neuron " + neuron);
    }
    private void addListener() {
        buttonSetting.setOnClickListener(view ->
                EventBus.getDefault().post(new FragmentChanger(new FragmentSetting(), true)));
    }
}
