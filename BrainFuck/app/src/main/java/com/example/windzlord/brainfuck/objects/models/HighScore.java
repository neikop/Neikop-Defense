package com.example.windzlord.brainfuck.objects.models;

/**
 * Created by WindyKiss on 1/4/2017.
 */

public class HighScore {

    @com.google.gson.annotations.SerializedName("id")
    private String id;

    @com.google.gson.annotations.SerializedName("userId")
    private String userId;

    @com.google.gson.annotations.SerializedName("userName")
    private String userName;

    @com.google.gson.annotations.SerializedName("type")
    private String type;

    @com.google.gson.annotations.SerializedName("position")
    private int position;

    @com.google.gson.annotations.SerializedName("level")
    private int level;

    @com.google.gson.annotations.SerializedName("expCurrent")
    private int expCurrent;

    @com.google.gson.annotations.SerializedName("highscore")
    private int score;


    public HighScore() {
    }

    public HighScore(String id, String userId, String userName, String type, int position, int level, int expCurrent, int highscore) {
        this.id = id;
        this.userId = userId;
        this.userName = userName;
        this.type = type;
        this.position = position;
        this.level = level;
        this.expCurrent = expCurrent;
        this.score = highscore;
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
}
