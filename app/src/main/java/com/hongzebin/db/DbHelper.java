package com.hongzebin.db;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

/**
 * 数据库
 * Created by 洪泽彬
 */

public class DbHelper extends SQLiteOpenHelper {

    public static final String CREATE_ONE = "create table One("
            + "url text primary key, "
            + "json text, "
            + "time integer)";

    public DbHelper(Context context) {
        super(context, "One.db", null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(CREATE_ONE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
    }
}
