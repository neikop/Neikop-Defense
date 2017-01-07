package com.example.windzlord.brainfuck.managers;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.example.windzlord.brainfuck.objects.models.Calculation;
import com.readystatesoftware.sqliteasset.SQLiteAssetHelper;

/**
 * Created by Ha San~ on 12/31/2016.
 */

public class ManagerDatabaseGame extends SQLiteAssetHelper {

    private static final int DB_VERSION = 1;
    private static final String DB_NAME = "database.db";
    private static final String QUOTE_TABLE_NAME_1 = "calcu_game1";
    private static final String QUOTE_TABLE_NAME_2 = "calcu_game2";
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

    private ManagerDatabaseGame(Context context) {
        super(context, DB_NAME, null, DB_VERSION);
    }

    public Calculation getRandomCalculation(int levels) {
        SQLiteDatabase db = getWritableDatabase();
        Cursor cursor = db.query(QUOTE_TABLE_NAME_2,
                QUOTE_COLUMNS,
                QUOTE_COLUMN_LEVELS + "=?",
                new String[]{String.valueOf(levels)},
                null,
                null,
                "RANDOM()",
                "1");

        if (cursor.moveToNext()) {
            return create(cursor);
        }
        return null;
    }

    private Calculation create(Cursor cursor) {
        int id = cursor.getInt(cursor.getColumnIndex(QUOTE_COLUMN_ID));
        String name = cursor.getString(cursor.getColumnIndex(QUOTE_COLUMN_CALCULATION));
        int results = cursor.getInt(cursor.getColumnIndex(QUOTE_COLUMN_RESULTS));
        int levels = cursor.getInt(cursor.getColumnIndex(QUOTE_COLUMN_LEVELS));
        return new Calculation(id, name, results, levels);
    }

    private static ManagerDatabaseGame instance;

    public static ManagerDatabaseGame getInstance() {
        return instance;
    }

    public static void init(Context context) {
        instance = new ManagerDatabaseGame(context);
    }

}
