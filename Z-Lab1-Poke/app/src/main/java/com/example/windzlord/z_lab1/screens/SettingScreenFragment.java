package com.example.windzlord.z_lab1.screens;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.ToggleButton;

import com.example.windzlord.z_lab1.R;
import com.example.windzlord.z_lab1.controllers.Gogo;
import com.example.windzlord.z_lab1.managers.Preference;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnCheckedChanged;
import butterknife.OnClick;

/**
 * A simple {@link Fragment} subclass.
 */
public class SettingScreenFragment extends Fragment {

    @BindView(R.id.toggleButton_sound)
    ToggleButton toggleButtonSound;

    @BindView(R.id.toggleButton_music)
    ToggleButton toggleButtonMusic;

    @BindView(R.id.textView_quiz)
    TextView textViewQuiz;

    @BindView(R.id.imageView_background_gen1)
    ImageView imageViewBackgroundGene1;

    @BindView(R.id.imageView_background_gen2)
    ImageView imageViewBackgroundGene2;

    @BindView(R.id.imageView_background_gen3)
    ImageView imageViewBackgroundGene3;

    @BindView(R.id.imageView_background_gen4)
    ImageView imageViewBackgroundGene4;

    @BindView(R.id.imageView_background_gen5)
    ImageView imageViewBackgroundGene5;

    @BindView(R.id.imageView_background_gen6)
    ImageView imageViewBackgroundGene6;

    @BindView(R.id.textView_gen1)
    TextView textViewGene1;

    @BindView(R.id.textView_gen2)
    TextView textViewGene2;

    @BindView(R.id.textView_gen3)
    TextView textViewGene3;

    @BindView(R.id.textView_gen4)
    TextView textViewGene4;

    @BindView(R.id.textView_gen5)
    TextView textViewGene5;

    @BindView(R.id.textView_gen6)
    TextView textViewGene6;

    private List<ImageView> imageViewPokeCovers;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the custom_toast_xml for this fragment
        View view = inflater.inflate(R.layout.fragment_setting_screen, container, false);
        settingThingsUp(view);

        return view;
    }

    private void settingThingsUp(View view) {
        ButterKnife.bind(this, view);
        getContent();
    }

    private void getContent() {

        Gogo.goSetFontTextView(getActivity(), textViewQuiz, "fonts/PoplarStd.ttf");
        Gogo.goSetFontTextView(getActivity(), textViewGene1, "fonts/PoplarStd.ttf");
        Gogo.goSetFontTextView(getActivity(), textViewGene2, "fonts/PoplarStd.ttf");
        Gogo.goSetFontTextView(getActivity(), textViewGene3, "fonts/PoplarStd.ttf");
        Gogo.goSetFontTextView(getActivity(), textViewGene4, "fonts/PoplarStd.ttf");
        Gogo.goSetFontTextView(getActivity(), textViewGene5, "fonts/PoplarStd.ttf");
        Gogo.goSetFontTextView(getActivity(), textViewGene6, "fonts/PoplarStd.ttf");

        toggleButtonSound.setChecked(Preference.getInstance().getStatusSound());
        toggleButtonMusic.setChecked(Preference.getInstance().getStatusMusic());

        imageViewPokeCovers = new ArrayList<>(Arrays.asList(null,
                imageViewBackgroundGene1, imageViewBackgroundGene2, imageViewBackgroundGene3,
                imageViewBackgroundGene4, imageViewBackgroundGene5, imageViewBackgroundGene6
        ));

        for (int i = 1; i <= 6; i++)
            Gogo.goSetBackgroundView(getActivity(), imageViewPokeCovers.get(i),
                    Preference.getInstance().getStatusGene(i)
                            ? R.drawable.circle_background_active
                            : R.drawable.circle_background_inactive
            );
    }

    @OnCheckedChanged(R.id.toggleButton_sound)
    public void changeSound() {
        Preference.getInstance().putStatusSound(toggleButtonSound.isChecked());
    }

    @OnCheckedChanged(R.id.toggleButton_music)
    public void changeMusic() {
        Preference.getInstance().putStatusMusic(toggleButtonMusic.isChecked());
    }

    private boolean cannotDeactivateTheLastGene(int gene) {
        for (int i = 1; i <= 6; i++)
            if (i != gene & Preference.getInstance().getStatusGene(i))
                return false;
        return true;
    }

    @OnClick(R.id.imageView_background_gen1)
    public void changeBackgroundGene1() {
        if (cannotDeactivateTheLastGene(1)) return;
        boolean isActivated = Preference.getInstance().getStatusGene(1);
        if (isActivated) Gogo.goSetBackgroundView(
                getActivity(), imageViewBackgroundGene1, R.drawable.circle_background_inactive);
        else Gogo.goSetBackgroundView(
                getActivity(), imageViewBackgroundGene1, R.drawable.circle_background_active);
        Preference.getInstance().putStatusGene(1, !isActivated);
    }

    @OnClick(R.id.imageView_background_gen2)
    public void changeBackgroundGene2() {
        if (cannotDeactivateTheLastGene(2)) return;
        boolean isActivated = Preference.getInstance().getStatusGene(2);
        if (isActivated) Gogo.goSetBackgroundView(
                getActivity(), imageViewBackgroundGene2, R.drawable.circle_background_inactive);
        else Gogo.goSetBackgroundView(
                getActivity(), imageViewBackgroundGene2, R.drawable.circle_background_active);
        Preference.getInstance().putStatusGene(2, !isActivated);
    }

    @OnClick(R.id.imageView_background_gen3)
    public void changeBackgroundGene3() {
        if (cannotDeactivateTheLastGene(3)) return;
        boolean isActivated = Preference.getInstance().getStatusGene(3);
        if (isActivated) Gogo.goSetBackgroundView(
                getActivity(), imageViewBackgroundGene3, R.drawable.circle_background_inactive);
        else Gogo.goSetBackgroundView(
                getActivity(), imageViewBackgroundGene3, R.drawable.circle_background_active);
        Preference.getInstance().putStatusGene(3, !isActivated);
    }

    @OnClick(R.id.imageView_background_gen4)
    public void changeBackgroundGene4() {
        if (cannotDeactivateTheLastGene(4)) return;
        boolean isActivated = Preference.getInstance().getStatusGene(4);
        if (isActivated) Gogo.goSetBackgroundView(
                getActivity(), imageViewBackgroundGene4, R.drawable.circle_background_inactive);
        else Gogo.goSetBackgroundView(
                getActivity(), imageViewBackgroundGene4, R.drawable.circle_background_active);
        Preference.getInstance().putStatusGene(4, !isActivated);
    }

    @OnClick(R.id.imageView_background_gen5)
    public void changeBackgroundGene5() {
        if (cannotDeactivateTheLastGene(5)) return;
        boolean isActivated = Preference.getInstance().getStatusGene(5);
        if (isActivated) Gogo.goSetBackgroundView(
                getActivity(), imageViewBackgroundGene5, R.drawable.circle_background_inactive);
        else Gogo.goSetBackgroundView(
                getActivity(), imageViewBackgroundGene5, R.drawable.circle_background_active);
        Preference.getInstance().putStatusGene(5, !isActivated);
    }

    @OnClick(R.id.imageView_background_gen6)
    public void changeBackgroundGene6() {
        if (cannotDeactivateTheLastGene(6)) return;
        boolean isActivated = Preference.getInstance().getStatusGene(6);
        if (isActivated) Gogo.goSetBackgroundView(
                getActivity(), imageViewBackgroundGene6, R.drawable.circle_background_inactive);
        else Gogo.goSetBackgroundView(
                getActivity(), imageViewBackgroundGene6, R.drawable.circle_background_active);
        Preference.getInstance().putStatusGene(6, !isActivated);
    }

}
