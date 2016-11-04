package com.example.windzlord.x_lab6.managers;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.example.windzlord.x_lab6.models.Quote;
import com.readystatesoftware.sqliteasset.SQLiteAssetHelper;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by WindzLord on 10/26/2016.
 */

public class DBContextSQLite extends SQLiteAssetHelper {

    private final static String DB_NAME = "quote.db";
    private final static int DB_VERSION = 1;
    private static final String QUOTE_TABLE_NAME = "table_quote";
    private static final String QUOTE_COLUMN_ID = "id";
    private static final String QUOTE_COLUMN_TITLE = "title";
    private static final String QUOTE_COLUMN_CONTENT = "content";
    private static final String[] QUOTE_COLUMNS = new String[]{
            QUOTE_COLUMN_ID,
            QUOTE_COLUMN_TITLE,
            QUOTE_COLUMN_CONTENT
    };

    public DBContextSQLite(Context context) {
        super(context, DB_NAME, null, DB_VERSION);
    }

    public void clearAllRecords() {
        SQLiteDatabase writableDatabase = getWritableDatabase();
        writableDatabase.execSQL("delete from " + QUOTE_TABLE_NAME);
        writableDatabase.close();
    }

    public void insert(Quote quote) {
        SQLiteDatabase writableDatabase = getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(QUOTE_COLUMN_TITLE, quote.getTitle());
        contentValues.put(QUOTE_COLUMN_CONTENT, quote.getContent());
        quote.setId((int) writableDatabase.insert(QUOTE_TABLE_NAME, null, contentValues));
        writableDatabase.close();
    }

    public Cursor myQueryAll(SQLiteDatabase writableDatabase, String table, String[] columns) {
        return writableDatabase.query(table, columns, null, null, null, null, null);
    }

    public List<Quote> getAllQuotes() {
        List<Quote> quoteList = new ArrayList<>();
        SQLiteDatabase writableDatabase = getWritableDatabase();

        Cursor cursor = myQueryAll(writableDatabase, QUOTE_TABLE_NAME, QUOTE_COLUMNS);
        while (cursor.moveToNext()) {
            quoteList.add(makeQuote(cursor));

            System.out.println(makeQuote(cursor));
        }
        return quoteList;
    }

    public Cursor myQueryRandom(SQLiteDatabase writableDatabase, String table, String[] columns) {
        return writableDatabase.query(table, columns, null, null, null, null, "RANDOM()");
    }

    public Quote getRandomQuote(){
        SQLiteDatabase writableDatabase = getWritableDatabase();
        Cursor cursor = myQueryRandom(writableDatabase, QUOTE_TABLE_NAME, QUOTE_COLUMNS);
        cursor.moveToNext();
        return makeQuote(cursor);
    }

    private Quote makeQuote(Cursor cursor) {
        int id = cursor.getInt(cursor.getColumnIndex(QUOTE_COLUMN_ID));
        String title = cursor.getString(cursor.getColumnIndex(QUOTE_COLUMN_TITLE));
        String content = cursor.getString(cursor.getColumnIndex(QUOTE_COLUMN_CONTENT));
        return new Quote(id, title, content);
    }

    public static DBContextSQLite instance;

    public static DBContextSQLite getInstance() {
        return instance;
    }

    public static void init(Context context) {
        instance = new DBContextSQLite(context);
    }
}
