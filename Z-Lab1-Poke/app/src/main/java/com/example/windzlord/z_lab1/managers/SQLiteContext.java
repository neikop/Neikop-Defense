package com.example.windzlord.z_lab1.managers;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.example.windzlord.z_lab1.objects.Pokemon;
import com.readystatesoftware.sqliteasset.SQLiteAssetHelper;

import java.util.ArrayList;

/**
 * Created by WindzLord on 11/17/2016.
 */

public class SQLiteContext extends SQLiteAssetHelper {

    private static final String DB_NAME = "pokemon.db";
    private static final int DB_VERSION = 1;

    private static final String POKE_TABLE_NAME = "pokemon";
    private static final String POKE_COLUMN_ID = "id";
    private static final String POKE_COLUMN_NAME = "name";
    private static final String POKE_COLUMN_TAG = "tag";
    private static final String POKE_COLUMN_GENE = "gen";
    private static final String POKE_COLUMN_IMAGE = "img";
    private static final String POKE_COLUMN_COLOR = "color";
    private static final String[] POKE_COLUMNS = new String[]{
            POKE_COLUMN_ID, POKE_COLUMN_NAME, POKE_COLUMN_TAG,
            POKE_COLUMN_GENE, POKE_COLUMN_IMAGE, POKE_COLUMN_COLOR
    };

    public SQLiteContext(Context context) {
        super(context, DB_NAME, null, DB_VERSION);
    }

    public static SQLiteContext instance;

    public static SQLiteContext getInstance() {
        return instance;
    }

    public static void init(Context context) {
        instance = new SQLiteContext(context);
    }

    private String havingGene(int... genes) {
        String having = "";
        for (int gene : genes) {
            if (having.isEmpty()) having += ("GEN=" + gene);
            else having += (" OR GEN=" + gene);
        }
        return having;
    }

    public ArrayList<Pokemon> getPokemons(int... genes) {
        ArrayList<Pokemon> pokeList = new ArrayList<>();
        SQLiteDatabase writableDatabase = getWritableDatabase();
        Cursor cursor = writableDatabase.query(POKE_TABLE_NAME, POKE_COLUMNS, null, null, "ID", havingGene(genes), null);
        while (cursor.moveToNext()) {
            pokeList.add(makePoke(cursor));
        }
        return pokeList;
    }

    private String havingGene(ArrayList<Integer> genes) {
        String having = "";
        for (int gene : genes) {
            if (having.isEmpty()) having += ("GEN=" + gene);
            else having += (" OR GEN=" + gene);
        }
        return having;
    }

    public ArrayList<Pokemon> getPokemons(ArrayList<Integer> genes) {
        ArrayList<Pokemon> pokeList = new ArrayList<>();
        SQLiteDatabase writableDatabase = getWritableDatabase();
        Cursor cursor = writableDatabase.query(POKE_TABLE_NAME, POKE_COLUMNS, null, null, "ID", havingGene(genes), null);
        while (cursor.moveToNext()) {
            pokeList.add(makePoke(cursor));
        }
        return pokeList;
    }

    public ArrayList<Pokemon> getPokemons(int gene) {
        int[] genes = new int[]{gene};
        return getPokemons(genes);
    }

    public Pokemon getRandomPokemon() {
        SQLiteDatabase writableDatabase = getWritableDatabase();
        Cursor cursor = writableDatabase.query(POKE_TABLE_NAME, POKE_COLUMNS, null, null, null, null, "RANDOM()");
        cursor.moveToNext();
        return makePoke(cursor);
    }

    private Pokemon makePoke(Cursor cursor) {
        return new Pokemon(
                cursor.getInt(cursor.getColumnIndex(POKE_COLUMN_ID)),
                cursor.getString(cursor.getColumnIndex(POKE_COLUMN_NAME)),
                cursor.getString(cursor.getColumnIndex(POKE_COLUMN_TAG)),
                cursor.getInt(cursor.getColumnIndex(POKE_COLUMN_GENE)),
                cursor.getString(cursor.getColumnIndex(POKE_COLUMN_IMAGE)),
                cursor.getString(cursor.getColumnIndex(POKE_COLUMN_COLOR)));
    }
}
