package com.example.windzlord.x_session7.models;

import com.google.gson.annotations.SerializedName;

/**
 * Created by WindzLord on 10/25/2016.
 */

public class LoginResultJSONModel {
    @SerializedName("code")
    private String code;

    public String getCode() {
        return code;
    }

    public boolean isSuccessful() {
        return ("1".equals(code));
    }
}
