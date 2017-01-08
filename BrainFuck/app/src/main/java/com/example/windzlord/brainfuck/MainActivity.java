package com.example.windzlord.brainfuck;

import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.example.windzlord.brainfuck.managers.ManagerDatabaseGame;
import com.example.windzlord.brainfuck.objects.FragmentChanger;
import com.example.windzlord.brainfuck.screens.FragmentMain;
import com.example.windzlord.brainfuck.screens.FragmentWelcome;
import com.example.windzlord.brainfuck.screens.types.FragmentCalculation;
import com.example.windzlord.brainfuck.screens.types.FragmentConcentration;
import com.example.windzlord.brainfuck.screens.types.FragmentMemory;
import com.example.windzlord.brainfuck.screens.types.FragmentObservation;
import com.facebook.CallbackManager;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;

import butterknife.ButterKnife;

public class MainActivity extends AppCompatActivity {

    public CallbackManager callbackManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        callbackManager = CallbackManager.Factory.create();
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

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        callbackManager.onActivityResult(requestCode, resultCode, data);
    }

    public CallbackManager getCallbackManager(){
        return callbackManager;
    }
}
