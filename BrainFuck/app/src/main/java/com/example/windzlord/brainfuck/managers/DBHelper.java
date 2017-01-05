package com.example.windzlord.brainfuck.managers;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.example.windzlord.brainfuck.objects.models.CalculationOne;
import com.readystatesoftware.sqliteasset.SQLiteAssetHelper;

/**
 * Created by Ha San~ on 12/31/2016.
 */

public class DBHelper extends SQLiteAssetHelper {
    private final static String DB_NAME = "db_cal.db";
    private final static int DB_VERSION = 1;
    private static final String QUOTE_TABLE_NAME = "cal_king";
    private static final String QUOTE_TABLE_NAME_2 ="cal_2";
    private static final String QUOTE_COLUMN_ID = "id";
    private static final String QUOTE_COLUMN_CALCULATION = "calculation";
    private static final String QUOTE_COLUMN_RESULTS = "results";
    private static final String QUOTE_COLUMN_LEVELS = "levels";


    private static final String[] QUOTE_COLUMNS = new String[]{
            QUOTE_COLUMN_ID,
            QUOTE_COLUMN_CALCULATION,
            QUOTE_COLUMN_RESULTS,
            QUOTE_COLUMN_LEVELS
    };

    public DBHelper(Context context) {
        super(context, DB_NAME, null, DB_VERSION);
    }

    public CalculationOne getrandomCalculation(int levels) {
        SQLiteDatabase db = getWritableDatabase();
        Cursor cursor = db.query(QUOTE_TABLE_NAME,
                QUOTE_COLUMNS,
                QUOTE_COLUMN_LEVELS +"=?",
                new String[]{String.valueOf(levels)},
                null,
                null,
                "RANDOM()",
                "1");

        if (cursor.moveToNext()){
            return create(cursor);
        }
        return null;
    }
    public CalculationOne getrandomCalculation2(int levels) {
        SQLiteDatabase db = getWritableDatabase();
        Cursor cursor = db.query(QUOTE_TABLE_NAME_2,
                QUOTE_COLUMNS,
                QUOTE_COLUMN_LEVELS +"=?",
                new String[]{String.valueOf(levels)},
                null,
                null,
                "RANDOM()",
                "1");

        if (cursor.moveToNext()){
            return create(cursor);
        }
        return null;
    }

    public CalculationOne create(Cursor cursor) {
        int id = cursor.getInt(cursor.getColumnIndex(QUOTE_COLUMN_ID));
        String name = cursor.getString(cursor.getColumnIndex(QUOTE_COLUMN_CALCULATION));
        int results = cursor.getInt(cursor.getColumnIndex(QUOTE_COLUMN_RESULTS));
        int levels = cursor.getInt(cursor.getColumnIndex(QUOTE_COLUMN_LEVELS));
        return new CalculationOne(id,name,results,levels);
    }

    private static DBHelper instance;

    public static DBHelper getInstance() {
        return instance;
    }

    public static void init(Context context) {
        instance = new DBHelper(context);
    }

}
