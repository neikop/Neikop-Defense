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

import org.greenrobot.eventbus.Subscribe;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * A simple {@link Fragment} subclass.
 */
public class FragmentProfile extends Fragment {

    @BindView(R.id.textView_user_name)
    TextView user_name;

    public FragmentProfile() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout1 for this fragment
        View view = inflater.inflate(R.layout.tab_fragment_profile, container, false);
        settingThingsUp(view);
        return view;
    }

    private void settingThingsUp(View view) {
        ButterKnife.bind(this, view);

        getUser();
    }

    public void getUser() {
        user_name.setText(ManagerPreference.getInstance().getUserName());
    }
}
