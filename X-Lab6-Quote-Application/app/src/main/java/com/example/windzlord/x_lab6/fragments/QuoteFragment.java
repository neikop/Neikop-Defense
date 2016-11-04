package com.example.windzlord.x_lab6.fragments;


import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.text.Html;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import butterknife.BindView;
import butterknife.ButterKnife;

import com.example.windzlord.x_lab6.MainActivity;
import com.example.windzlord.x_lab6.R;
import com.example.windzlord.x_lab6.constant.Constant;
import com.example.windzlord.x_lab6.jsonmodels.QuoteJSONModel;
import com.example.windzlord.x_lab6.managers.FileManager;
import com.example.windzlord.x_lab6.managers.Preferences;
import com.example.windzlord.x_lab6.models.FragmentEvent;
import com.example.windzlord.x_lab6.models.Quote;
import com.google.gson.Gson;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.assist.FailReason;
import com.nostra13.universalimageloader.core.listener.ImageLoadingListener;

import org.greenrobot.eventbus.EventBus;

import java.io.File;
import java.io.IOException;

import butterknife.OnClick;
import okhttp3.*;

/**
 * A simple {@link Fragment} subclass.
 */
public class QuoteFragment extends Fragment {

    @BindView(R.id.imageView_background)
    ImageView imageViewBackGround;

    @BindView(R.id.textView_content)
    TextView textViewContent;

    @BindView(R.id.textView_title)
    TextView textViewTitle;

    @BindView(R.id.textView_username)
    TextView textViewUsername;

    @BindView(R.id.button_getOut)
    Button buttonGetOut;

    public QuoteFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_qoute, container, false);

        settingThingsUp(view);
        setupUI();

        return view;
    }

    private void settingThingsUp(View view) {
        ButterKnife.bind(this, view);
    }

    private void setupUI() {

        String user = "Hello " + Preferences.getInstance().getUsername().replaceAll("\n", " ");
        textViewUsername.setText(user);

        File background = ((MainActivity) getActivity()).getBackground();
        ImageLoader.getInstance().displayImage(Uri.fromFile(background).toString(), imageViewBackGround);

        // Get quote
        Quote quote = ((MainActivity) getActivity()).getQuote();
        textViewTitle.setText(quote.getTitle());
        textViewContent.setText("        " + Html.fromHtml(quote.getContent()));
    }

    @OnClick(R.id.button_getOut)
    public void onClick(View view) {
        ((MainActivity) getActivity()).nextGoGo();
        Preferences.getInstance().clearCache();
        EventBus.getDefault().post(new FragmentEvent(new RegisterFragment(), false));
    }

}
