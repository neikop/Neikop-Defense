package com.example.windzlord.x_session7;

import com.example.windzlord.x_session7.models.LoginResultJSONModel;

import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.POST;

/**
 * Created by WindzLord on 11/4/2016.
 */

public interface LoginService {

    @POST("/api/login")
    Call<LoginResultJSONModel> login(@Body RequestBody body);

}
