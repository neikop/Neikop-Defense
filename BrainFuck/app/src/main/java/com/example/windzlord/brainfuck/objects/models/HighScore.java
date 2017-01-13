package com.example.windzlord.brainfuck.objects.models;

import com.google.gson.annotations.SerializedName;

/**
 * Created by WindyKiss on 1/4/2017.
 */

public class HighScore {

    @SerializedName("id")
    private String id;

    @SerializedName("userId")
    private String userId;

    @SerializedName("type")
    private String type;

    @SerializedName("userName")
    private String userName;

    @SerializedName("position")
    private int position;

    @SerializedName("level")
    private int level;

    @SerializedName("expCurrent")
    private int expCurrent;

    @SerializedName("highscore")
    private int score;


    public HighScore() {
    }

    public HighScore(String id, String userId, String userName,
                     String type, int position, int level, int expCurrent, int score) {
        this.id = id;
        this.userId = userId;
        this.userName = userName;
        this.type = type;
        this.position = position;
        this.level = level;
        this.expCurrent = expCurrent;
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

    public int getExpCurrent() {
        return expCurrent;
    }

    public int getScore() {
        return score;
    }

    public void setExpCurrent(int expCurrent) {
        this.expCurrent = expCurrent;
    }

    public void setScore(int score) {
        this.score = score;
    }

    @Override
    public String toString() {
        return String.format("%s %s %s = %s %s", userName, type, position, expCurrent, score);
    }
}
