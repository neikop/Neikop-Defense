package com.example.windzlord.brainfuck.screens.tabs;


import android.annotation.TargetApi;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.LinearInterpolator;
import android.view.animation.RotateAnimation;
import android.view.animation.ScaleAnimation;
import android.widget.ImageView;

import com.example.windzlord.brainfuck.R;
import com.example.windzlord.brainfuck.adapters.AnimationAdapter;
import com.example.windzlord.brainfuck.objects.FragmentChanger;
import com.example.windzlord.brainfuck.screens.types.FragmentCalculation;
import com.example.windzlord.brainfuck.screens.types.FragmentConcentration;
import com.example.windzlord.brainfuck.screens.types.FragmentMemory;
import com.example.windzlord.brainfuck.screens.types.FragmentObservation;
import com.ogaclejapan.arclayout.ArcLayout;

import org.greenrobot.eventbus.EventBus;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * A simple {@link Fragment} subclass.
 */
public class FragmentPractice extends Fragment {

    @BindView(R.id.spin_layout)
    ArcLayout spinerView;

    public FragmentPractice() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.tab_fragment_practice, container, false);
        settingThingsUp(view);

        return view;
    }

    private void settingThingsUp(View view) {
        ButterKnife.bind(this, view);

        goSpin();
        goAnimation(view);
    }

    @TargetApi(Build.VERSION_CODES.JELLY_BEAN)
    private void goSpin() {
        ArcLayout.LayoutParams params = new ArcLayout.LayoutParams(15, 15);
        while (spinerView.getChildCount() < 20) {
            ImageView child = new ImageView(getContext());
            child.setBackground(getResources().getDrawable(R.drawable.custom_oval_view_10dip_size));
            spinerView.addView(child, params);
        }
        RotateAnimation rotate = new RotateAnimation(0, 360, 1, 0.5f, 1, 0.5f);
        rotate.setDuration(5000);
        rotate.setFillAfter(true);
        rotate.setRepeatCount(Animation.INFINITE);
        rotate.setInterpolator(new LinearInterpolator());
        spinerView.startAnimation(rotate);
    }

    private void goAnimation(View view) {
        ScaleAnimation scaleOne = new ScaleAnimation(0.9f, 1, 0.9f, 1, 1, 0.5f, 1, 0.5f);
        ScaleAnimation scaleTwo = new ScaleAnimation(1, 0.9f, 1, 0.9f, 1, 0.5f, 1, 0.5f);
        scaleOne.setDuration(500);
        scaleOne.setFillAfter(true);
        scaleOne.setAnimationListener(new AnimationAdapter() {
            @Override
            public void onAnimationEnd(Animation animation) {
                startAnimation(view, scaleTwo);
            }
        });

        scaleTwo.setDuration(500);
        scaleTwo.setFillAfter(true);
        scaleTwo.setAnimationListener(new AnimationAdapter() {

            @Override
            public void onAnimationEnd(Animation animation) {
                startAnimation(view, scaleOne);
            }
        });

        startAnimation(view, scaleOne);
    }

    private void startAnimation(View view, Animation amiAnimation) {
        view.findViewById(R.id.button_calcu).startAnimation(amiAnimation);
        view.findViewById(R.id.button_concen).startAnimation(amiAnimation);
        view.findViewById(R.id.button_memory).startAnimation(amiAnimation);
        view.findViewById(R.id.button_obser).startAnimation(amiAnimation);
    }

    @OnClick(R.id.button_memory)
    public void goMemory() {
        EventBus.getDefault().post(new FragmentChanger(new FragmentMemory(), true));
    }

    @OnClick(R.id.button_concen)
    public void goConcentration() {
        EventBus.getDefault().post(new FragmentChanger(new FragmentConcentration(), true));
    }

    @OnClick(R.id.button_calcu)
    public void goCalculation() {
        EventBus.getDefault().post(new FragmentChanger(new FragmentCalculation(), true));
    }

    @OnClick(R.id.button_obser)
    public void goObservation() {
        EventBus.getDefault().post(new FragmentChanger(new FragmentObservation(), true));
    }

}
