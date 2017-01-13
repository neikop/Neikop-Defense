package com.example.windzlord.brainfuck.objects.models;

import com.example.windzlord.brainfuck.managers.ManagerUserData;
import com.google.gson.annotations.SerializedName;

/**
 * Created by WindyKiss on 1/4/2017.
 */

public class HighScore {

    @SerializedName("id")
    private String id;

    @SerializedName("User_ID")
    private String userId;

    @SerializedName("User_Name")
    private String userName;

    @SerializedName("Type")
    private String type;

    @SerializedName("Position")
    private int position;

    @SerializedName("Level")
    private int level;

    @SerializedName("Exp")
    private int exp;

    @SerializedName("Score")
    private int score;


    public HighScore() {
    }

    public HighScore(String id, String userId, String userName,
                     String type, int position, int level, int exp, int score) {
        this.id = id;
        this.userId = userId;
        this.userName = userName;
        this.type = type;
        this.position = position;
        this.level = level;
        this.exp = exp;
        this.score = score;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public void setType(String type) {
        this.type = type;
    }

    public void setPosition(int position) {
        this.position = position;
    }

    public void setLevel(int level) {
        this.level = level;
    }

    public String getType() {
        return type;
    }

    public int getPosition() {
        return position;
    }

    public int getLevel() {
        return level;
    }

    public int getExp() {
        return exp;
    }

    public int getScore() {
        return score;
    }

    public void setExp(int exp) {
        this.exp = exp;
    }

    public void setScore(int score) {
        this.score = score;
    }

    @Override
    public String toString() {
        return String.format("%s %s %s = %s %s", userName, type, position, exp, score);
    }
}
