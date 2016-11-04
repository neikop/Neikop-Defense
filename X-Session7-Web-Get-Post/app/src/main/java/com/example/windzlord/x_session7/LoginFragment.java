package com.example.windzlord.x_session7;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

import com.example.windzlord.x_session7.models.AccountJSONModel;
import com.example.windzlord.x_session7.models.LoginResultJSONModel;
import com.google.gson.Gson;

import org.greenrobot.eventbus.EventBus;

import java.io.IOException;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.FormBody;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;
import retrofit2.GsonConverterFactory;
import retrofit2.Retrofit;


/**
 * A simple {@link Fragment} subclass.
 */
public class LoginFragment extends Fragment {

    @BindView(R.id.button_login)
    Button buttonLogin;

    @BindView(R.id.editText_username)
    EditText editTextUsername;

    @BindView(R.id.editText_password)
    EditText editTextPassword;

    private String URL = "https://a5-tumblelog.herokuapp.com/api/login";
    private String URL_BASE = "https://a5-tumblelog.herokuapp.com";
    private String responseBodyString;

    public LoginFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_login, container, false);

        ButterKnife.bind(this, view);

        return view;
    }

    @OnClick(R.id.button_login)
    public void login() {
        String username = editTextUsername.getText().toString();
        String password = editTextPassword.getText().toString();

//        sendPOSTRequest("json", username, password);
        sendPOSTRequestByRetrofit(username, password);
    }

    private void sendPOSTRequestByRetrofit(String username, String password) {
        Retrofit loginRetrofit = new Retrofit.Builder()
                .baseUrl(URL_BASE)
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        AccountJSONModel account = new AccountJSONModel(username, password);

        String jsonBody = new Gson().toJson(account);

        RequestBody body = RequestBody.create(
                MediaType.parse("application/json"),
                jsonBody);

        LoginService loginService = loginRetrofit.create(LoginService.class);
        loginService.login(body)
                .enqueue(new retrofit2.Callback<LoginResultJSONModel>() {
                    @Override
                    public void onResponse(retrofit2.Call<LoginResultJSONModel> call, retrofit2.Response<LoginResultJSONModel> response) {
                        System.out.println("onResponse");
                        LoginResultJSONModel result = response.body();
                        System.out.println(result.getCode());

                        final String resultString = result.isSuccessful() ? "Login passed" : "Login failed";
                        System.out.println(resultString);

                        getActivity().runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                EventBus.getDefault().post(resultString);
                            }
                        });
                    }

                    @Override
                    public void onFailure(retrofit2.Call<LoginResultJSONModel> call, Throwable t) {

                    }
                });
    }

    private void sendPOSTRequest(String type, String username, String password) {

        // by form
        FormBody formBody = new FormBody.Builder()
                .add("username", username)
                .add("password", password)
                .build();

        // by json
        AccountJSONModel account = new AccountJSONModel(username, password);
        String jsonBody = new Gson().toJson(account);
        RequestBody requestBody = RequestBody.create(MediaType.parse("application/json"), jsonBody);

        Request request = new Request.Builder()
                .url(URL)
                .post("form".equals(type) ? formBody : requestBody)
                .build();

        new OkHttpClient().newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                System.out.println("onFailure");
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                responseBodyString = response.body().string();
                System.out.println(responseBodyString);

                LoginResultJSONModel result = new Gson().fromJson(responseBodyString, LoginResultJSONModel.class);
                final String resultString = result.isSuccessful() ? "Login passed" : "Login failed";
                System.out.println(resultString);

                getActivity().runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        EventBus.getDefault().post(resultString);
                    }
                });
            }
        });
    }

}
