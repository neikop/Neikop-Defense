package com.example.windzlord.brainfuck.managers;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.example.windzlord.brainfuck.objects.models.HighScore;
import com.readystatesoftware.sqliteasset.SQLiteAssetHelper;

import java.util.ArrayList;
import java.util.List;


public class SQLiteDBHelper extends SQLiteAssetHelper {

    private final static String DB_NAME = "database.db";
    private final static int DB_VERSION = 1;
    private static final String TABLE_NAME = "Highscore";
    private static final String COLUMN_ID = "id";
    private static final String COLUMN_USER_ID = "userId";
    private static final String COLUMN_TYPE = "type";
    private static final String COLUMN_POSITION = "position";
    private static final String COLUMN_LEVEL = "level";
    private static final String COLUMN_EXP_CURRENT = "expCurrent";
    private static final String COLUMN_HIGHSCORE = "highscore";
    private static final String[] COLUMNS = new String[]{
            COLUMN_ID,
            COLUMN_USER_ID,
            COLUMN_TYPE,
            COLUMN_POSITION,
            COLUMN_LEVEL,
            COLUMN_EXP_CURRENT,
            COLUMN_HIGHSCORE
    };

    public SQLiteDBHelper(Context context) {
        super(context, DB_NAME, null, DB_VERSION);
    }

    public void insert(HighScore highScore) {
        SQLiteDatabase writableDatabase = getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(COLUMN_ID, highScore.getId());
        contentValues.put(COLUMN_USER_ID, highScore.getUserId());
        contentValues.put(COLUMN_TYPE, highScore.getType());
        contentValues.put(COLUMN_POSITION, highScore.getPosition());
        contentValues.put(COLUMN_LEVEL, highScore.getLevel());
        contentValues.put(COLUMN_EXP_CURRENT, highScore.getExpCurrent());
        contentValues.put(COLUMN_HIGHSCORE, highScore.getHighscore());
        writableDatabase.insert(TABLE_NAME, null, contentValues);
        writableDatabase.close();
    }


    public List<HighScore> getAllHighscore() {
        ArrayList<HighScore> highScores = new ArrayList<>();
        SQLiteDatabase db = getWritableDatabase();
        Cursor cursor = db.query(TABLE_NAME,//table
                COLUMNS,//column
                null,//selection
                null,//selectionArgs
                null,//groupBy
                null,//having
                null,//orderBy
                null);//limit

        while (cursor.moveToNext()) {
            highScores.add(createHighscore(cursor));
        }
        cursor.close();

        db.close();
        return highScores;
    }

    public List<HighScore> getAllHighscoreByUserId(String userId) {
        ArrayList<HighScore> highScores = new ArrayList<>();
        SQLiteDatabase db = getWritableDatabase();
        String where = "userId LIKE '" + userId + "'";
        Cursor cursor = db.query(TABLE_NAME,//table
                COLUMNS,//column
                where,//selection
                null,//selectionArgs
                null,//groupBy
                null,//having
                null,//orderBy
                null);//limit

        while (cursor.moveToNext()) {
            highScores.add(createHighscore(cursor));
        }
        cursor.close();

        db.close();
        return highScores;
    }

    public HighScore getHighscoreByInfo(String userId, String type, int position) {
        ArrayList<HighScore> highScores = new ArrayList<>();
        SQLiteDatabase db = getWritableDatabase();
        String where = "userId LIKE '" + userId + "' AND type LIKE '" + type + "' AND position = " +position;
        Cursor cursor = db.query(TABLE_NAME,//table
                COLUMNS,//column
                where,//selection
                null,//selectionArgs
                null,//groupBy
                null,//having
                null,//orderBy
                null);//limit

        while (cursor.moveToNext()) {
            highScores.add(createHighscore(cursor));
        }
        cursor.close();

        db.close();
        return highScores.get(0);
    }


    private HighScore createHighscore(Cursor cursor) {
        String id = cursor.getString(cursor.getColumnIndex(COLUMN_ID));
        String userId = cursor.getString(cursor.getColumnIndex(COLUMN_USER_ID));
        String type = cursor.getString(cursor.getColumnIndex(COLUMN_TYPE));
        int position = cursor.getInt(cursor.getColumnIndex(COLUMN_POSITION));
        int level = cursor.getInt(cursor.getColumnIndex(COLUMN_LEVEL));
        int exp = cursor.getInt(cursor.getColumnIndex(COLUMN_EXP_CURRENT));
        int highscore = cursor.getInt(cursor.getColumnIndex(COLUMN_HIGHSCORE));
        return new HighScore(id, userId, type, position, level, exp, highscore);
    }

    public void resetDB(List<HighScore> highScoreList) {
        SQLiteDatabase db = getWritableDatabase();
        db.execSQL("delete from " + TABLE_NAME);
        db.close();

        for (HighScore highScore : highScoreList){
            insert(highScore);
        }
    }

    public void updateHighscore(String userId, String type, int position, int level, int exp, int highscore) {
        SQLiteDatabase db = getWritableDatabase();
        String where = "userId LIKE '" + userId + "' AND type LIKE '" + type + "' AND position = " +position;
        ContentValues cv = new ContentValues();
        cv.put("level", level); //These Fields should be your String values of actual column names
        cv.put("expCurrent", exp);
        cv.put("highscore", highscore);
        db.update(
            TABLE_NAME,
                cv,
                where,
                null
        );
        db.close();

    }


    public static SQLiteDBHelper instance;

    public static SQLiteDBHelper getInstance() {
        return instance;
    }

    public static void init(Context context) {
        instance = new SQLiteDBHelper(context);
    }

}
