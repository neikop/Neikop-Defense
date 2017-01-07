package com.example.windzlord.brainfuck.objects.models;

/**
 * Created by WindyKiss on 1/4/2017.
 */

public class HighScore {

    @com.google.gson.annotations.SerializedName("id")
    public int id;

    @com.google.gson.annotations.SerializedName("userId")
    public String userId;

    @com.google.gson.annotations.SerializedName("type")
    public String type;

    @com.google.gson.annotations.SerializedName("position")
    public int position;

    @com.google.gson.annotations.SerializedName("level")
    public int level;

    @com.google.gson.annotations.SerializedName("expCurrent")
    public int expCurrent;

    @com.google.gson.annotations.SerializedName("highscore")
    public int highscore;

    public HighScore(String userId, String type, int position) {
        this.userId = userId;
        this.type = type;
        this.position = position;
    }

    public void setLevel(int level) {
        this.level = level;
    }


    public void setExpCurrent(int expCurrent) {
        this.expCurrent = expCurrent;
    }

    public void setHighscore(int highscore) {
        this.highscore = highscore;
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

    public int getHighscore() {
        return highscore;
    }

    @Override
    public String toString() {
        return "HighScore{" +
                "id=" + id +
                ", userId='" + userId + '\'' +
                ", type='" + type + '\'' +
                ", position=" + position +
                ", level=" + level +
                ", expCurrent=" + expCurrent +
                ", highscore=" + highscore +
                '}';
    }
}
