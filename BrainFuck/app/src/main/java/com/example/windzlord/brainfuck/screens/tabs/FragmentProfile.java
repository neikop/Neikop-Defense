package com.example.windzlord.brainfuck.screens.tabs;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.windzlord.brainfuck.R;
import com.example.windzlord.brainfuck.managers.Gogo;
import com.example.windzlord.brainfuck.managers.ManagerPreference;
import com.google.common.eventbus.EventBus;

import org.greenrobot.eventbus.Subscribe;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * A simple {@link Fragment} subclass.
 */
public class FragmentProfile extends Fragment {
    @BindView(R.id.user_name)
    TextView user_name;
    @BindView(R.id.user_id)
    TextView user_id;

    public FragmentProfile() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout1 for this fragment
        View v = inflater.inflate(R.layout.tab_fragment_profile, container, false);
        ButterKnife.bind(this, v);
        getUser();
        return v;
    }

    public void getUser() {
        user_id.setText(ManagerPreference.getInstance().getUserID());
        user_name.setText(Gogo.NAME);
    }

    @Subscribe
    public void getUser(String x) {
        getUser();
    }
}
