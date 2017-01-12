package com.example.windzlord.brainfuck.screens.tabs;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;

import com.example.windzlord.brainfuck.R;

import butterknife.BindView;
import butterknife.ButterKnife;


/**
 * A simple {@link Fragment} subclass.
 */
public class FragmentSetting extends Fragment {

    @BindView(R.id.ic_sound_effect)
    ImageView ic_sound_effect;
    @BindView(R.id.ic_sound_background)
    ImageView ic_sound_background;
    @BindView(R.id.ic_login)
    ImageView ic_login;
    @BindView(R.id.btn_sound_background)
    Button btn_sound_background;
    @BindView(R.id.btn_sound_effect)
    Button btn_sound_effect;

    Boolean sound_effect = true;
    Boolean sound_background = true;
    public FragmentSetting() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.tab_fragment_setting, container, false);
        ButterKnife.bind(this,v);
        addListener();
        return v;
    }
    public void addListener(){

        btn_sound_effect.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(sound_effect){
                    ic_sound_effect.setImageResource(R.drawable.icon_music);
                    btn_sound_effect.setText("SOUND EFFECT ON");
                    sound_effect =false;
                }else{
                    sound_effect =true;
                    ic_sound_effect.setImageResource(R.drawable.icon_music_on);
                    btn_sound_effect.setText("SOUND EFFECT OFF");
                }
            }
        });
        btn_sound_background.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(sound_background){
                    ic_sound_background.setImageResource(R.drawable.icon_sound);
                    sound_background =false;
                }else{
                    ic_sound_background.setImageResource(R.drawable.icon_sound_on);
                    sound_background =true;
                }
            }
        });
    }
}
