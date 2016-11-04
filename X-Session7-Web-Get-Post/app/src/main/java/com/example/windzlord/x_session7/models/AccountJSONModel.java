package com.example.windzlord.x_session7.models;

import com.google.gson.annotations.SerializedName;

/**
 * Created by WindzLord on 10/25/2016.
 */

public class AccountJSONModel {
    @SerializedName("username")
    private String username;

    @SerializedName("password")
    private String password;

    public AccountJSONModel(String username, String password) {
        this.username = username;
        this.password = password;
    }
}
