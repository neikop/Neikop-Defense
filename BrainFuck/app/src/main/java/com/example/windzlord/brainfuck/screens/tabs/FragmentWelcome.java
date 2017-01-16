package com.example.windzlord.brainfuck.screens.tabs;


import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.ScaleAnimation;
import android.widget.TextView;

import com.example.windzlord.brainfuck.R;
import com.example.windzlord.brainfuck.adapters.CountDownTimerAdapter;
import com.example.windzlord.brainfuck.managers.ManagerBrain;
import com.example.windzlord.brainfuck.managers.ManagerPreference;
import com.example.windzlord.brainfuck.objects.FragmentChanger;
import com.example.windzlord.brainfuck.objects.TabLayoutChanger;
import com.example.windzlord.brainfuck.screens.types.FragmentCalculation;
import com.example.windzlord.brainfuck.screens.types.FragmentConcentration;
import com.example.windzlord.brainfuck.screens.types.FragmentMemory;
import com.example.windzlord.brainfuck.screens.types.FragmentObservation;

import org.eazegraph.lib.charts.PieChart;
import org.eazegraph.lib.models.PieModel;
import org.greenrobot.eventbus.EventBus;

import at.markushi.ui.CircleButton;
import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * A simple {@link Fragment} subclass.
 */
public class FragmentWelcome extends Fragment {

    @BindView(R.id.pieChart_daddy)
    PieChart pieChartDaddy;

    @BindView(R.id.pieChart_calcu)
    PieChart pieChartCalcu;

    @BindView(R.id.pieChart_concen)
    PieChart pieChartConcen;

    @BindView(R.id.pieChart_memory)
    PieChart pieChartMemory;

    @BindView(R.id.pieChart_obser)
    PieChart pieChartObser;

    @BindView(R.id.textView_calcu_welcome)
    TextView textViewCalcu;

    @BindView(R.id.textView_concen_welcome)
    TextView textViewConcen;

    @BindView(R.id.textView_memory_welcome)
    TextView textViewMemory;

    @BindView(R.id.textView_obser_welcome)
    TextView textViewObser;

    @BindView(R.id.button_daddy_welcome)
    CircleButton buttonDaddyWelcome;

    @BindView(R.id.button_calcu_welcome)
    CircleButton buttonCalcuWelcome;

    @BindView(R.id.button_concen_welcome)
    CircleButton buttonConcenWelcome;

    @BindView(R.id.button_memory_welcome)
    CircleButton buttonMemoryWelcome;

    @BindView(R.id.button_obser_welcome)
    CircleButton buttonObserWelcome;

    private final int TIME = 600;

    public FragmentWelcome() {
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

        getChart();
        getAnimation();
        getListener();
    }

    private void getChart() {
        int neuronCalcu = 0, neuronConcen = 0, neuronMemory = 0, neuronObser = 0;
        for (String game : ManagerBrain.GAME_LIST)
            for (int i = 1; i < 4; i++) {
                int level = ManagerPreference.getInstance().getLevel(game, i);
                int exp = ManagerPreference.getInstance().getExpCurrent(game, i);
                if (game.equals(ManagerBrain.CALCULATION))
                    neuronCalcu += (level * (level - 1) / 2) * 300 + exp;
                if (game.equals(ManagerBrain.CONCENTRATION))
                    neuronConcen += (level * (level - 1) / 2) * 300 + exp;
                if (game.equals(ManagerBrain.MEMORY))
                    neuronMemory += (level * (level - 1) / 2) * 300 + exp;
                if (game.equals(ManagerBrain.OBSERVATION))
                    neuronObser += (level * (level - 1) / 2) * 300 + exp;
            }
        textViewCalcu.setText(neuronCalcu + "");
        textViewConcen.setText(neuronConcen + "");
        textViewMemory.setText(neuronMemory + "");
        textViewObser.setText(neuronObser + "");

        if (neuronCalcu + neuronConcen + neuronMemory + neuronObser == 0)
            neuronCalcu = neuronConcen = neuronMemory = neuronObser = 1;
        pieChartDaddy.addPieSlice(new PieModel("Calcu", neuronCalcu, Color.parseColor("#ffff4444")));
        pieChartDaddy.addPieSlice(new PieModel("Concen", neuronConcen, Color.parseColor("#ff99cc00")));
        pieChartDaddy.addPieSlice(new PieModel("Memory", neuronMemory, Color.parseColor("#ffffbb33")));
        pieChartDaddy.addPieSlice(new PieModel("Obser", neuronObser, Color.parseColor("#ff33b5e5")));

        pieChartCalcu.addPieSlice(new PieModel("Small_Calcu", 1, Color.parseColor("#ffff4444")));
        pieChartConcen.addPieSlice(new PieModel("Small_Concen", 1, Color.parseColor("#ff99cc00")));
        pieChartMemory.addPieSlice(new PieModel("Small_Memory", 1, Color.parseColor("#ffffbb33")));
        pieChartObser.addPieSlice(new PieModel("Small_Obser", 1, Color.parseColor("#ff33b5e5")));
    }

    private void getAnimation() {
        PieChart[] charts = new PieChart[]{
                pieChartDaddy, pieChartCalcu, pieChartConcen, pieChartMemory, pieChartObser};
        for (PieChart pie : charts) {
            pie.setAnimationTime(TIME);
            pie.startAnimation();
        }

        CircleButton[] buttons = new CircleButton[]{
                buttonCalcuWelcome, buttonConcenWelcome, buttonMemoryWelcome, buttonObserWelcome};
        new CountDownTimerAdapter(TIME / 2) {
            @Override
            public void onFinish() {
                ScaleAnimation scale = new ScaleAnimation(0, 1, 0, 1, 1, 0.5f, 1, 0.5f);
                scale.setDuration(TIME / 2);
                for (CircleButton button : buttons) {
                    button.setVisibility(View.VISIBLE);
                    button.startAnimation(scale);
                }
                ScaleAnimation scaleDaddy = new ScaleAnimation(0, 1, 0, 1, 1, 0.5f, 1, 0.5f);
                scaleDaddy.setDuration(TIME / 2);
                buttonDaddyWelcome.setVisibility(View.VISIBLE);
                buttonDaddyWelcome.startAnimation(scaleDaddy);
            }
        }.start();
    }

    private void getListener() {
        new CountDownTimerAdapter(TIME) {
            @Override
            public void onFinish() {
                buttonCalcuWelcome.setOnClickListener((v) -> EventBus.getDefault().post(
                        new FragmentChanger(new FragmentCalculation(), true)));
                buttonConcenWelcome.setOnClickListener((v) -> EventBus.getDefault().post(
                        new FragmentChanger(new FragmentConcentration(), true)));
                buttonMemoryWelcome.setOnClickListener((v) -> EventBus.getDefault().post(
                        new FragmentChanger(new FragmentMemory(), true)));
                buttonObserWelcome.setOnClickListener((v) -> EventBus.getDefault().post(
                        new FragmentChanger(new FragmentObservation(), true)));
                buttonDaddyWelcome.setOnClickListener((v) -> EventBus.getDefault().post(
                        new TabLayoutChanger(1)));
            }
        }.start();
    }

}
