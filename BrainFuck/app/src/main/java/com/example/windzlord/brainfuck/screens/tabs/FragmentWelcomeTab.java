package com.example.windzlord.brainfuck.screens.tabs;


import android.graphics.Color;
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

import org.eazegraph.lib.charts.PieChart;
import org.eazegraph.lib.models.PieModel;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * A simple {@link Fragment} subclass.
 */
public class FragmentWelcomeTab extends Fragment {

    @BindView(R.id.piechart)
    PieChart mPieChart;
    @BindView(R.id.piechart_calcul)
    PieChart pieChart_Calcu;
    @BindView(R.id.piechart_concen)
    PieChart pieChart_Concen;
    @BindView(R.id.piechart_obser)
    PieChart pieChart_obser;
    @BindView(R.id.piechart_memory)
    PieChart pieChart_memory;

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
        setPieChart();
    }

    private void setPieChart() {
//    PieChart mPieChart = (PieChart) findViewById(R.id.piechart);
        pieChart_Calcu.addPieSlice(new PieModel("Small_PieChart", 1, Color.parseColor("#ffff4444")));
        pieChart_Concen.addPieSlice(new PieModel("Small_Concen", 1, Color.parseColor("#ff99cc00")));
        pieChart_obser.addPieSlice(new PieModel("Small_Obser", 1, Color.parseColor("#ffff8800")));
        pieChart_memory.addPieSlice(new PieModel("Small_Memory", 1, Color.parseColor("#ff0099cc")));

        mPieChart.addPieSlice(new PieModel("Calculation", 15, Color.parseColor("#ffff4444")));
        mPieChart.addPieSlice(new PieModel("Concen", 25, Color.parseColor("#ff99cc00")));
        mPieChart.addPieSlice(new PieModel("Obser", 35, Color.parseColor("#ffff8800")));
        mPieChart.addPieSlice(new PieModel("Memory", 9, Color.parseColor("#ff0099cc")));

        pieChart_memory.startAnimation();
        pieChart_Concen.startAnimation();
        pieChart_obser.startAnimation();
        pieChart_Calcu.startAnimation();
        mPieChart.startAnimation();
    }


}
