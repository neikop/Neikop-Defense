package com.example.windzlord.brainfuck.managers;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import com.example.windzlord.brainfuck.objects.models.HighScore;
import com.readystatesoftware.sqliteasset.SQLiteAssetHelper;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * Created by WindyKiss on 1/4/2017.
 */

public class ManagerUserData extends SQLiteAssetHelper {

    private final String TAG = this.getClass().getSimpleName();

    private final static int DB_VERSION = 1;
    private final static String DB_NAME = "UserData.db";
    private static final String TABLE_NAME = "TableHighScore";
    private static final String COLUMN_ID = "ID";
    private static final String COLUMN_USER_ID = "User_ID";
    private static final String COLUMN_USER_NAME = "User_Name";
    private static final String COLUMN_USER_IMAGE = "User_Image";
    private static final String COLUMN_TYPE = "Type";
    private static final String COLUMN_POSITION = "Position";
    private static final String COLUMN_LEVEL = "Level";
    private static final String COLUMN_EXP_CURRENT = "Exp";
    private static final String COLUMN_HIGH_SCORE = "Score";
    private static final String[] COLUMNS = new String[]{
            COLUMN_ID,
            COLUMN_USER_ID,
            COLUMN_USER_NAME,
            COLUMN_USER_IMAGE,
            COLUMN_TYPE,
            COLUMN_POSITION,
            COLUMN_LEVEL,
            COLUMN_EXP_CURRENT,
            COLUMN_HIGH_SCORE};

    private ManagerUserData(Context context) {
        super(context, DB_NAME, null, DB_VERSION);
        sqLiteDatabase = getWritableDatabase();
    }

    private List<HighScore> getListPlayer() {
        ArrayList<HighScore> players = new ArrayList<>();
        Cursor cursor = sqLiteDatabase.query(TABLE_NAME, COLUMNS,
                null, null, COLUMN_USER_ID, null, null, null);
        while (cursor.moveToNext()) players.add(createScore(cursor));

        return players;
    }

    public List<HighScore> getListPlayerSorted() {
        List<HighScore> players = getListPlayer();
        for (HighScore player : players)
            player.setScore(ManagerUserData.getInstance().getExperience(player.getUserId()));

        Collections.sort(players, (o, s) -> s.getScore() == o.getScore() ?
                s.getUserName().compareTo(o.getUserName()) : s.getScore() - o.getScore());
        return players;
    }

    public List<HighScore> getListScore() {
        ArrayList<HighScore> scores = new ArrayList<>();
        Cursor cursor = sqLiteDatabase.query(TABLE_NAME, COLUMNS,
                null, null, null, null, null, null);
        while (cursor.moveToNext()) scores.add(createScore(cursor));

        return scores;
    }

    public List<HighScore> getScoreByUserId(String userID) {
        ArrayList<HighScore> scores = new ArrayList<>();
        String WHERE = String.format("%s LIKE '%s'", COLUMN_USER_ID, userID);
        Cursor cursor = sqLiteDatabase.query(TABLE_NAME, COLUMNS, WHERE,
                null, null, null, null, null);
        while (cursor.moveToNext()) scores.add(createScore(cursor));

        return scores;
    }

    private int getExperience(String userID) {
        int sum = 0;
        List<HighScore> scores = getScoreByUserId(userID);
        for (HighScore score : scores)
            sum += (score.getLevel() * (score.getLevel() - 1) / 2) * 300 + score.getExp();
        return sum;
    }

    public boolean isExistedUser(String userID) {
        return !getScoreByUserId(userID).isEmpty();
    }

    public HighScore getScoreByInfo(String userId, String type, int position) {
        Log.d(TAG, "End game: getScoreByInfo " + type + " " + position);
        ArrayList<HighScore> scores = new ArrayList<>();
        String WHERE = String.format("%s LIKE '%s' AND %s LIKE '%s' AND %s = %s",
                COLUMN_USER_ID, userId, COLUMN_TYPE, type, COLUMN_POSITION, position);
        Cursor cursor = sqLiteDatabase.query(TABLE_NAME, COLUMNS, WHERE,
                null, null, null, null, null);
        while (cursor.moveToNext()) scores.add(createScore(cursor));

        if (scores.isEmpty()) return null;
        return scores.get(0);
    }

    private HighScore createScore(Cursor cursor) {
        String id = cursor.getString(cursor.getColumnIndex(COLUMN_ID));
        String userId = cursor.getString(cursor.getColumnIndex(COLUMN_USER_ID));
        String userName = cursor.getString(cursor.getColumnIndex(COLUMN_USER_NAME));
        String userImage = cursor.getString(cursor.getColumnIndex(COLUMN_USER_IMAGE));
        String type = cursor.getString(cursor.getColumnIndex(COLUMN_TYPE));
        int position = cursor.getInt(cursor.getColumnIndex(COLUMN_POSITION));
        int level = cursor.getInt(cursor.getColumnIndex(COLUMN_LEVEL));
        int exp = cursor.getInt(cursor.getColumnIndex(COLUMN_EXP_CURRENT));
        int score = cursor.getInt(cursor.getColumnIndex(COLUMN_HIGH_SCORE));
        return new HighScore(id, userId, userName, userImage, type, position, level, exp, score);
    }

    public void updateDatabaseFromPreference() {
        Log.d(TAG, "updateDatabaseFromPreference");
        String userID = ManagerPreference.getInstance().getUserID();
        for (int i = 0; i < ManagerBrain.GAME_LIST.length; i++) {
            for (int position = 1; position < 4; position++) {
                String type = ManagerBrain.GAME_LIST[i];
                String userImage = ManagerPreference.getInstance().getUserImage();
                int level = ManagerPreference.getInstance().getLevel(type, position);
                int exp = ManagerPreference.getInstance().getExpCurrent(type, position);
                int score = ManagerPreference.getInstance().getScore(type, position);
                updateScore(userID, userImage, type, position, level, exp, score);
            }
        }
    }

    public void updateDatabase(List<HighScore> scores) {
        Log.d(TAG, "updateDatabase");
        for (HighScore score : scores)
            if (updateScore(score) == 0)
                insertScore(score);
    }

    private int updateScore(HighScore score) {
        Log.d(TAG, "updateScore to LOCAL " + score);
        return updateScore(score.getUserId(), score.getUserImage(), score.getType(), score.getPosition(),
                score.getLevel(), score.getExp(), score.getScore());
    }

    public int updateScore(String userID, String userImage, String type, int position, int level, int exp, int score) {
        ContentValues values = new ContentValues();
        values.put(COLUMN_USER_IMAGE, userImage);
        values.put(COLUMN_LEVEL, level);
        values.put(COLUMN_EXP_CURRENT, exp);
        values.put(COLUMN_HIGH_SCORE, score);
        String WHERE = String.format("%s LIKE '%s' AND %s LIKE '%s' AND %s = %s",
                COLUMN_USER_ID, userID, COLUMN_TYPE, type, COLUMN_POSITION, position);
        return sqLiteDatabase.update(TABLE_NAME, values, WHERE, null);
    }

    private void insertScore(HighScore score) {
        Log.d(TAG, "insertScore to LOCAL " + score);
        ContentValues values = new ContentValues();
        values.put(COLUMN_ID, score.getId());
        values.put(COLUMN_USER_ID, score.getUserId());
        values.put(COLUMN_USER_NAME, score.getUserName());
        values.put(COLUMN_USER_IMAGE, score.getUserImage());
        values.put(COLUMN_TYPE, score.getType());
        values.put(COLUMN_POSITION, score.getPosition());
        values.put(COLUMN_LEVEL, score.getLevel());
        values.put(COLUMN_EXP_CURRENT, score.getExp());
        values.put(COLUMN_HIGH_SCORE, score.getScore());
        sqLiteDatabase.insert(TABLE_NAME, null, values);
    }

    private SQLiteDatabase sqLiteDatabase;

    private static ManagerUserData instance;

    public static ManagerUserData getInstance() {
        return instance;
    }

    public static void init(Context context) {
        instance = new ManagerUserData(context);
    }

}
