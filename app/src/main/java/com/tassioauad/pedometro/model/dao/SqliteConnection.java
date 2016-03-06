package com.tassioauad.pedometro.model.dao;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.tassioauad.pedometro.model.dao.impl.ActivityLocationDaoImpl;

public class SqliteConnection extends SQLiteOpenHelper {

    private static final String DATABASE_NAME = "pedometrodb";
    private static final Integer DATABASE_VERSION = 1;

    public SqliteConnection(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(ActivityLocationDaoImpl.CREATE_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }
}
