package com.example.windzlord.brainfuck.managers;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.example.windzlord.brainfuck.objects.models.HighScore;
import com.readystatesoftware.sqliteasset.SQLiteAssetHelper;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutionException;


public class ManagerUserData extends SQLiteAssetHelper {

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
    }

    public List<HighScore> getListPlayer() {
        ArrayList<HighScore> scores = new ArrayList<>();
        SQLiteDatabase database = getWritableDatabase();
        Cursor cursor = database.query(TABLE_NAME, COLUMNS,
                null, null, "userId", null, null, null);
        while (cursor.moveToNext()) scores.add(createScore(cursor));
        cursor.close();
        database.close();
        return scores;
    }

    public List<HighScore> getListScore() {
        ArrayList<HighScore> scores = new ArrayList<>();
        SQLiteDatabase database = getWritableDatabase();
        Cursor cursor = database.query(TABLE_NAME, COLUMNS,
                null, null, null, null, null, null);
        while (cursor.moveToNext()) scores.add(createScore(cursor));
        cursor.close();
        database.close();
        return scores;
    }

    public List<HighScore> getScoreByUserId(String userId) {
        ArrayList<HighScore> scores = new ArrayList<>();
        SQLiteDatabase database = getWritableDatabase();
        String WHERE = "userId LIKE '" + userId + "'";
        Cursor cursor = database.query(TABLE_NAME, COLUMNS, WHERE,
                null, null, null, null, null);
        while (cursor.moveToNext()) scores.add(createScore(cursor));
        cursor.close();
        database.close();
        return scores;
    }

    public int getSumScore(String userId) {
        int sum = 0;
        SQLiteDatabase database = getWritableDatabase();
        Cursor cursor = database.rawQuery("SELECT SUM("
                + COLUMN_HIGH_SCORE + ") as Total FROM "
                + TABLE_NAME + " WHERE userId LIKE '"
                + userId + "'", null);
        if (cursor.moveToFirst()) sum = cursor.getInt(cursor.getColumnIndex("Total"));
        cursor.close();
        database.close();
        return sum;
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
        ArrayList<HighScore> scores = new ArrayList<>();
        SQLiteDatabase database = getWritableDatabase();
        String WHERE = "userId LIKE '" + userId + "' AND type LIKE '" + type + "' AND position = " + position;
        Cursor cursor = database.query(TABLE_NAME, COLUMNS, WHERE,
                null, null, null, null, null);
        while (cursor.moveToNext()) scores.add(createScore(cursor));
        cursor.close();
        database.close();
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

    public void updateScore(String userId, String type, int position, int level, int exp, int score) {
        SQLiteDatabase database = getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put("level", level); //These Fields should be your String values of actual column names
        values.put("expCurrent", exp);
        values.put("highscore", score);
        String WHERE = "userId LIKE '" + userId + "' AND type LIKE '" + type + "' AND position = " + position;
        database.update(TABLE_NAME, values, WHERE, null);
        database.close();
    }

    void resetDatabase(List<HighScore> scores) {
        if (!ManagerNetwork.getInstance().isConnectedToInternet())
            return;
        SQLiteDatabase database = getWritableDatabase();
        database.execSQL("delete from " + TABLE_NAME);
        database.close();
        for (HighScore score : scores) insertScore(score);
    }

    private void insertScore(HighScore score) {
        SQLiteDatabase writableDatabase = getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(COLUMN_ID, score.getId());
        values.put(COLUMN_USER_ID, score.getUserId());
        values.put(COLUMN_USER_NAME, score.getUserName());
        values.put(COLUMN_TYPE, score.getType());
        values.put(COLUMN_POSITION, score.getPosition());
        values.put(COLUMN_LEVEL, score.getLevel());
        values.put(COLUMN_EXP_CURRENT, score.getExpCurrent());
        values.put(COLUMN_HIGH_SCORE, score.getScore());
        try{
            writableDatabase.insert(TABLE_NAME, null, values);
        } catch (Exception e){

        }
        writableDatabase.close();
    }

    private static ManagerUserData instance;

    public static ManagerUserData getInstance() {
        return instance;
    }

    public static void init(Context context) {
        instance = new ManagerUserData(context);
    }

}
