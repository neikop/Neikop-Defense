package com.example.windzlord.x_lab6.managers;

import com.example.windzlord.x_lab6.models.QuoteByRealm;

import java.util.Random;

import io.realm.Realm;
import io.realm.RealmResults;

/**
 * Created by WindzLord on 11/4/2016.
 */

public class DBContextRealm {

    private static DBContextRealm instance;

    public static void init() {
        instance = new DBContextRealm();
    }

    public static DBContextRealm getInstance() {
        return instance;
    }

    public void deleteAllQuotes() {
        beginTransaction();
        getRealm().delete(QuoteByRealm.class);
        commitTransaction();
    }

    public QuoteByRealm getRandomQuote() {
        RealmResults<QuoteByRealm> listQuote = getRealm().where(QuoteByRealm.class).findAll();
        return listQuote.get(new Random().nextInt(listQuote.size()));
    }

    public int getSize() {
        return getRealm().where(QuoteByRealm.class).findAll().size();
    }

    public void add(QuoteByRealm quote) {
        beginTransaction();
        getRealm().copyToRealm(quote);
        commitTransaction();
    }

    private Realm getRealm() {
        return Realm.getDefaultInstance();
    }

    private void beginTransaction() {
        getRealm().beginTransaction();
    }

    private void commitTransaction() {
        getRealm().commitTransaction();
    }
}
