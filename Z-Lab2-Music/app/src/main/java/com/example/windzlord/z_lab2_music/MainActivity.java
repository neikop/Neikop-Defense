package com.example.windzlord.z_lab2_music;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;

import com.example.windzlord.z_lab2_music.managers.NetworkManager;
import com.example.windzlord.z_lab2_music.managers.RealmManager;
import com.example.windzlord.z_lab2_music.objects.event_bus.FragmentEvent;
import com.example.windzlord.z_lab2_music.screens.GenresFragment;
import com.example.windzlord.z_lab2_music.screens.GenresMediaFragment;
import com.example.windzlord.z_lab2_music.screens.PlaylistFragment;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;

import butterknife.ButterKnife;

import static android.R.attr.type;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        settingThingsUp();
    }

    private void settingThingsUp() {
        ButterKnife.bind(this);
        EventBus.getDefault().register(this);

        openFragment(this.getClass().getSimpleName(), new GenresFragment(), false);
}

    @Subscribe
    public void onFragmentEvent(FragmentEvent event) {
        openFragment(event.getClassName(), event.getFragment(), event.isAddToBackStack());
    }

    private void openFragment(String className, Fragment fragment, boolean addToBackStack) {
        FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();

        if (className.equals(GenresMediaFragment.class.getSimpleName())) {
            fragmentTransaction.setCustomAnimations(R.anim.fade_in, R.anim.fade_out,
                    R.anim.go_right_in, R.anim.go_right_out);
        }
        if (className.equals(PlaylistFragment.class.getSimpleName())) {
            fragmentTransaction.setCustomAnimations(R.anim.fade_in, R.anim.fade_out,
                    R.anim.fade_in, R.anim.fade_out);
        }

        fragmentTransaction.replace(R.id.main_layout, fragment);
        if (addToBackStack) fragmentTransaction.addToBackStack(null);
        fragmentTransaction.commit();
    }

    @Override
    protected void onStop() {
        super.onStop();
        EventBus.getDefault().unregister(this);
    }
}
