package com.example.windzlord.brainfuck.managers;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import com.example.windzlord.brainfuck.objects.models.HighScore;
import com.readystatesoftware.sqliteasset.SQLiteAssetHelper;

import java.util.ArrayList;
import java.util.List;


public class ManagerUserData extends SQLiteAssetHelper {

    private final String TAG = this.getClass().getSimpleName();

    private final static int DB_VERSION = 1;
    private final static String DB_NAME = "database.db";
    private static final String TABLE_NAME = "Highscore";
    private static final String COLUMN_ID = "id";
    private static final String COLUMN_USER_ID = "userId";
    private static final String COLUMN_TYPE = "type";
    private static final String COLUMN_POSITION = "position";
    private static final String COLUMN_LEVEL = "level";
    private static final String COLUMN_EXP_CURRENT = "expCurrent";
    private static final String COLUMN_HIGH_SCORE = "highscore";
    private static final String COLUMN_USER_NAME = "userName";
    private static final String[] COLUMNS = new String[]{
            COLUMN_ID,
            COLUMN_USER_ID,
            COLUMN_USER_NAME,
            COLUMN_TYPE,
            COLUMN_POSITION,
            COLUMN_LEVEL,
            COLUMN_EXP_CURRENT,
            COLUMN_HIGH_SCORE};

    private ManagerUserData(Context context) {
        super(context, DB_NAME, null, DB_VERSION);
        sqLiteDatabase = getWritableDatabase();
    }

    public List<HighScore> getListPlayer() {
        ArrayList<HighScore> players = new ArrayList<>();
        Cursor cursor = sqLiteDatabase.query(TABLE_NAME, COLUMNS,
                null, null, COLUMN_USER_ID, null, null, null);
        while (cursor.moveToNext()) players.add(createScore(cursor));

        return players;
    }

    public List<HighScore> getListScore() {
        ArrayList<HighScore> scores = new ArrayList<>();
        Cursor cursor = sqLiteDatabase.query(TABLE_NAME, COLUMNS,
                null, null, null, null, null, null);
        while (cursor.moveToNext()) scores.add(createScore(cursor));

        return scores;
    }

    public List<HighScore> getScoreByUserId(String userId) {
        ArrayList<HighScore> scores = new ArrayList<>();
        String WHERE = String.format("%s LIKE '%s'", COLUMN_USER_ID, userId);
        Cursor cursor = sqLiteDatabase.query(TABLE_NAME, COLUMNS, WHERE,
                null, null, null, null, null);
        while (cursor.moveToNext()) scores.add(createScore(cursor));

        return scores;
    }

    public int getExperience(String userId) {
        int sum = 0;
        List<HighScore> scores = getScoreByUserId(userId);
        for (HighScore score : scores)
            sum += (score.getLevel() * (score.getLevel() - 1) / 2) * 300 + score.getExpCurrent();
        return sum;
    }

    public boolean isExistedUser(String userId) {
        return !getScoreByUserId(userId).isEmpty();
    }

    public HighScore getScoreByInfo(String userId, String type, int position) {
        Log.d(TAG, "End game: getScoreByInfo " + userId + " " + type + " " + position);
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
        String type = cursor.getString(cursor.getColumnIndex(COLUMN_TYPE));
        int position = cursor.getInt(cursor.getColumnIndex(COLUMN_POSITION));
        int level = cursor.getInt(cursor.getColumnIndex(COLUMN_LEVEL));
        int exp = cursor.getInt(cursor.getColumnIndex(COLUMN_EXP_CURRENT));
        int score = cursor.getInt(cursor.getColumnIndex(COLUMN_HIGH_SCORE));
        return new HighScore(id, userId, userName, type, position, level, exp, score);
    }

    public int updateScore(String userId, String type, int position, int level, int exp, int score) {
        ContentValues values = new ContentValues();

        values.put(COLUMN_LEVEL, level);
        values.put(COLUMN_EXP_CURRENT, exp);
        values.put(COLUMN_HIGH_SCORE, score);
        String WHERE = String.format("%s LIKE '%s' AND %s LIKE '%s' AND %s = %s",
                COLUMN_USER_ID, userId, COLUMN_TYPE, type, COLUMN_POSITION, position);
        return sqLiteDatabase.update(TABLE_NAME, values, WHERE, null);
    }

    void updateDatabase(List<HighScore> scores) {
        Log.d(TAG, "updateDatabase");
        for (HighScore score : scores)
            if (updateScore(score) == 0)
                insertScore(score);
    }

    private int updateScore(HighScore score) {
        Log.d(TAG, "updateScore " + score);
        return updateScore(score.getUserId(), score.getType(), score.getPosition(),
                score.getLevel(), score.getExpCurrent(), score.getScore());
    }

    public void insertScore(HighScore score) {
        Log.d(TAG, "insertScore " + score);
        ContentValues values = new ContentValues();
        values.put(COLUMN_ID, score.getId());
        values.put(COLUMN_USER_ID, score.getUserId());
        values.put(COLUMN_USER_NAME, score.getUserName());
        values.put(COLUMN_TYPE, score.getType());
        values.put(COLUMN_POSITION, score.getPosition());
        values.put(COLUMN_LEVEL, score.getLevel());
        values.put(COLUMN_EXP_CURRENT, score.getExpCurrent());
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
