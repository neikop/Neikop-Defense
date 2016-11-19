package com.example.windzlord.z_lab1;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.example.windzlord.z_lab1.objects.FragmentEvent;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;

import butterknife.ButterKnife;

import com.example.windzlord.z_lab1.screens.MainScreenFragment;

public class MainActivity extends AppCompatActivity {

    private Toast goToast;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        settingThingsUp();

        openFragment(new MainScreenFragment(), false);

    }

    private void settingThingsUp() {
        ButterKnife.bind(this);
        EventBus.getDefault().register(this);
        getContent();
    }

    private TextView getContent() {
        LayoutInflater inflater = getLayoutInflater();
        View layout = inflater.inflate(R.layout.custom_toast,
                (ViewGroup) findViewById(R.id.custom_toast_container));

        TextView text = (TextView) layout.findViewById(R.id.custom_toast_text);

        goToast = new Toast(getApplicationContext());
        goToast.setGravity(Gravity.BOTTOM, 0, 0);
        goToast.setDuration(Toast.LENGTH_LONG);
        goToast.setView(layout);

        return text;
    }

    @Subscribe
    public void onFragmentEvent(FragmentEvent fragmentEvent) {
        openFragment(fragmentEvent.getFragment(), fragmentEvent.isAddToBackStack());
    }

    @Subscribe
    public void showMessage(String message) {
        getContent().setText(message);
        goToast.show();
    }

    private void openFragment(Fragment fragment, boolean addToBackStack) {
        if (addToBackStack) goToast.cancel();
        FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
        fragmentTransaction.replace(R.id.main_frame, fragment);
        if (addToBackStack) fragmentTransaction.addToBackStack(null);
        fragmentTransaction.commit();
    }

    @Override
    protected void onStop() {
        super.onStop();
        EventBus.getDefault().unregister(this);
    }
}
