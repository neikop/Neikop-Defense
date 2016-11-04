package com.example.windzlord.x_lab6.fragments;


import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;

import com.example.windzlord.x_lab6.MainActivity;
import com.example.windzlord.x_lab6.R;
import com.example.windzlord.x_lab6.managers.FileManager;
import com.example.windzlord.x_lab6.managers.Preferences;
import com.example.windzlord.x_lab6.models.FragmentEvent;
import com.nostra13.universalimageloader.core.ImageLoader;

import org.greenrobot.eventbus.EventBus;

import java.io.File;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * A simple {@link Fragment} subclass.
 */
public class RegisterFragment extends Fragment {

    @BindView(R.id.imageView_background)
    ImageView imageViewBackGround;

    @BindView(R.id.editText_username)
    EditText editTextUsername;

    @BindView(R.id.button_register)
    Button buttonRegister;

    public RegisterFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_register, container, false);
        settingThingsUp(view);

        File background = ((MainActivity) getActivity()).getBackground();
        ImageLoader.getInstance().displayImage(Uri.fromFile(background).toString(), imageViewBackGround);

        return view;
    }

    private void settingThingsUp(View view) {
        ButterKnife.bind(this, view);
    }

    @OnClick(R.id.button_register)
    public void onClick(View view) {
        ((MainActivity) getActivity()).nextGoGo();
        String username = editTextUsername.getText().toString();
        Preferences.getInstance().putUsername(username);
        EventBus.getDefault().post(new FragmentEvent(new QuoteFragment(), false));
    }

}
