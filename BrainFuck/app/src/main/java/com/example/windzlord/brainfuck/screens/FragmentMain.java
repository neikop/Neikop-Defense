package com.example.windzlord.brainfuck.screens;


import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.TranslateAnimation;

import com.example.windzlord.brainfuck.R;
import com.example.windzlord.brainfuck.adapters.PagerAdapter;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * A simple {@link Fragment} subclass.
 */
public class FragmentMain extends Fragment {

    @BindView(R.id.tab_layout)
    TabLayout myTabLayout;

    @BindView(R.id.pager_layout)
    ViewPager myViewPager;

    public FragmentMain() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout1 for this fragment
        View view = inflater.inflate(R.layout.fragment_main, container, false);
        settingThingsUp(view);

        return view;
    }

    private void settingThingsUp(View view) {
        ButterKnife.bind(this, view);

        goTabLayout(view);
    }

    private void goTabLayout(View view) {

        myTabLayout.addTab(myTabLayout.newTab().setCustomView(R.layout.tab_view_profile));
        myTabLayout.addTab(myTabLayout.newTab().setCustomView(R.layout.tab_view_practice));
        myTabLayout.addTab(myTabLayout.newTab().setCustomView(R.layout.tab_view_ranking));
        myTabLayout.addTab(myTabLayout.newTab().setCustomView(R.layout.tab_view_feedback));

        myTabLayout.setTabGravity(TabLayout.GRAVITY_FILL);
        myTabLayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                goTabSelected(view, tab.getPosition());
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {
                goTabUnselected(view, tab.getPosition());
            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {
                goTabUnselected(view, tab.getPosition());
                goTabSelected(view, tab.getPosition());
            }
        });
        myViewPager.setAdapter(new PagerAdapter(
                getChildFragmentManager(), myTabLayout.getTabCount()));

        myViewPager.addOnPageChangeListener(
                new TabLayout.TabLayoutOnPageChangeListener(myTabLayout));

        goTabSelected(view, 0);
    }

    private void goTabSelected(View view, int position) {
        myViewPager.setCurrentItem(position);
        TranslateAnimation goDown = new TranslateAnimation(
                Animation.RELATIVE_TO_SELF, 0, Animation.RELATIVE_TO_SELF, 0,
                Animation.RELATIVE_TO_SELF, -0.5f, Animation.RELATIVE_TO_SELF, 0);
        goDown.setFillAfter(true);
        goDown.setDuration(100);
        TranslateAnimation goUp = new TranslateAnimation(
                Animation.RELATIVE_TO_SELF, 0, Animation.RELATIVE_TO_SELF, 0,
                Animation.RELATIVE_TO_SELF, 0.5f, Animation.RELATIVE_TO_SELF, 0);
        goUp.setFillAfter(true);
        goUp.setDuration(100);
        switch (position) {
            case 0:
                view.findViewById(R.id.indicator_profile).setVisibility(View.VISIBLE);
                view.findViewById(R.id.indicator_profile).startAnimation(goDown);
                view.findViewById(R.id.indicator_profile2).setVisibility(View.VISIBLE);
                view.findViewById(R.id.indicator_profile2).startAnimation(goUp);
                break;
            case 1:
                view.findViewById(R.id.indicator_practice).setVisibility(View.VISIBLE);
                view.findViewById(R.id.indicator_practice).startAnimation(goDown);
                view.findViewById(R.id.indicator_practice2).setVisibility(View.VISIBLE);
                view.findViewById(R.id.indicator_practice2).startAnimation(goUp);
                break;
            case 2:
                view.findViewById(R.id.indicator_ranking).setVisibility(View.VISIBLE);
                view.findViewById(R.id.indicator_ranking).startAnimation(goDown);
                view.findViewById(R.id.indicator_ranking2).setVisibility(View.VISIBLE);
                view.findViewById(R.id.indicator_ranking2).startAnimation(goUp);
                break;
            case 3:
                view.findViewById(R.id.indicator_feedback).setVisibility(View.VISIBLE);
                view.findViewById(R.id.indicator_feedback).startAnimation(goDown);
                view.findViewById(R.id.indicator_feedback2).setVisibility(View.VISIBLE);
                view.findViewById(R.id.indicator_feedback2).startAnimation(goUp);
                break;
        }
    }

    private void goTabUnselected(View view, int position) {
        switch (position) {
            case 0:
                view.findViewById(R.id.indicator_profile).setVisibility(View.INVISIBLE);
                view.findViewById(R.id.indicator_profile).clearAnimation();
                view.findViewById(R.id.indicator_profile2).setVisibility(View.INVISIBLE);
                view.findViewById(R.id.indicator_profile2).clearAnimation();
                break;
            case 1:
                view.findViewById(R.id.indicator_practice).setVisibility(View.INVISIBLE);
                view.findViewById(R.id.indicator_practice).clearAnimation();
                view.findViewById(R.id.indicator_practice2).setVisibility(View.INVISIBLE);
                view.findViewById(R.id.indicator_practice2).clearAnimation();
                break;
            case 2:
                view.findViewById(R.id.indicator_ranking).setVisibility(View.INVISIBLE);
                view.findViewById(R.id.indicator_ranking).clearAnimation();
                view.findViewById(R.id.indicator_ranking2).setVisibility(View.INVISIBLE);
                view.findViewById(R.id.indicator_ranking2).clearAnimation();
                break;
            case 3:
                view.findViewById(R.id.indicator_feedback).setVisibility(View.INVISIBLE);
                view.findViewById(R.id.indicator_feedback).clearAnimation();
                view.findViewById(R.id.indicator_feedback2).setVisibility(View.INVISIBLE);
                view.findViewById(R.id.indicator_feedback2).clearAnimation();
                break;
        }
    }

}
