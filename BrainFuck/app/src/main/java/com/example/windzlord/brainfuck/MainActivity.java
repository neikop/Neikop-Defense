package com.example.windzlord.brainfuck;

import android.content.pm.ActivityInfo;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.example.windzlord.brainfuck.objects.even_bus.FragmentChanger;
import com.example.windzlord.brainfuck.screens.FragmentMain;
import com.example.windzlord.brainfuck.screens.FragmentWelcome;
import com.example.windzlord.brainfuck.screens.type_fragment.FragmentCalculation;
import com.example.windzlord.brainfuck.screens.type_fragment.FragmentConcentration;
import com.example.windzlord.brainfuck.screens.type_fragment.FragmentMemory;
import com.example.windzlord.brainfuck.screens.type_fragment.FragmentObservation;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;

import butterknife.ButterKnife;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.a_activity_main);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);

        settingThingsUp();
    }

    private void settingThingsUp() {
        ButterKnife.bind(this);
        EventBus.getDefault().register(this);

        getContent();
    }

    private void getContent() {
        onFragmentEvent(new FragmentChanger(new FragmentWelcome(), false));

        getAnimation();
    }

    private void getAnimation() {

    }

    @Subscribe
    public void onFragmentEvent(FragmentChanger event) {
        openFragment(event.getClassName(), event.getFragment(), event.isAddToBackStack());
    }

    private void openFragment(String name, Fragment fragment, boolean addToBackStack) {
        FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();

        if (name.equals(FragmentWelcome.class.getSimpleName())) {
            fragmentTransaction.setCustomAnimations(R.anim.go_fade_in, R.anim.nothing,
                    R.anim.nothing, R.anim.nothing);
        } else if (name.equals(FragmentMain.class.getSimpleName())) {
            fragmentTransaction.setCustomAnimations(R.anim.go_fade_in, R.anim.go_fade_out,
                    R.anim.nothing, R.anim.nothing);
        } else if (name.equals(FragmentMemory.class.getSimpleName())
                | name.equals(FragmentConcentration.class.getSimpleName())
                | name.equals(FragmentCalculation.class.getSimpleName())
                | name.equals(FragmentObservation.class.getSimpleName())) {
            fragmentTransaction.setCustomAnimations(R.anim.go_right_in, R.anim.nothing,
                    R.anim.nothing, R.anim.go_left_out);
        } else {
            fragmentTransaction.setCustomAnimations(R.anim.zoom_in, R.anim.nothing,
                    R.anim.nothing, R.anim.zoom_out);
        }

        fragmentTransaction.replace(R.id.layout_daddy, fragment);
        if (addToBackStack) fragmentTransaction.addToBackStack(name);
        fragmentTransaction.commit();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        EventBus.getDefault().unregister(this);
    }

}
