package com.example.windzlord.brainfuck.managers;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.example.windzlord.brainfuck.objects.models.Calculator;
import com.readystatesoftware.sqliteasset.SQLiteAssetHelper;

/**
 * Created by Ha San~ on 12/31/2016.
 */

public class ManagerGameData extends SQLiteAssetHelper {

    private static final int DB_VERSION = 1;
    private static final String DB_NAME = "Calculator.db";
    private static final String QUOTE_TABLE_NAME_1 = "CalculatorOne";
    private static final String QUOTE_TABLE_NAME_2 = "CalculatorTwo";
    private static final String QUOTE_COLUMN_ID = "ID";
    private static final String QUOTE_COLUMN_CALCULATION = "Calculator";
    private static final String QUOTE_COLUMN_RESULTS = "Result";
    private static final String QUOTE_COLUMN_LEVELS = "Level";

    private static final String[] QUOTE_COLUMNS = new String[]{
            QUOTE_COLUMN_ID,
            QUOTE_COLUMN_CALCULATION,
            QUOTE_COLUMN_RESULTS,
            QUOTE_COLUMN_LEVELS
    };

    private ManagerGameData(Context context) {
        super(context, DB_NAME, null, DB_VERSION);
    }

    public Calculator getRandomCalculation(int levels) {
        SQLiteDatabase db = getWritableDatabase();
        Cursor cursor = db.query(QUOTE_TABLE_NAME_2,
                QUOTE_COLUMNS,
                QUOTE_COLUMN_LEVELS + "=?",
                new String[]{String.valueOf(levels)},
                null,
                null,
                "RANDOM()",
                "1");
        if (cursor.moveToNext()) return createCalculation(cursor);
        else return null;
    }

    private Calculator createCalculation(Cursor cursor) {
        int id = cursor.getInt(cursor.getColumnIndex(QUOTE_COLUMN_ID));
        String name = cursor.getString(cursor.getColumnIndex(QUOTE_COLUMN_CALCULATION));
        int results = cursor.getInt(cursor.getColumnIndex(QUOTE_COLUMN_RESULTS));
        int levels = cursor.getInt(cursor.getColumnIndex(QUOTE_COLUMN_LEVELS));
        return new Calculator(id, name, results, levels);
    }

    private static ManagerGameData instance;

    public static ManagerGameData getInstance() {
        return instance;
    }

    public static void init(Context context) {
        instance = new ManagerGameData(context);
    }

}
