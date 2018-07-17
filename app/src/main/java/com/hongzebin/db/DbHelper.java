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

    public static final String CREATE_TABLE_LIST = "create table LIST("
            + "url text primary key, "
            + "json text)";

    public static final String CREATE_TABLE_MUSIC = "create table MUSIC("
            + "url text primary key, "
            + "musicName text, "
            + "cover text, "
            + "title text, "
            + "summary text, "
            + "contentHtml text, "
            + "lyric text, "
            + "info text, "
            + "likeCount text, "
            + "comCount text, "
            + "author text)";

    public static final String CREATE_TABLE_READ = "create table READ("
            + "url text primary key, "
            + "title text, "
            + "contentHtml text, "
            + "authorDesc text, "
            + "likeCount text, "
            + "comCount text, "
            + "author text)";

    public  static final String CREATE_TABLE_VIDEO = "create table VIDEO("
            + "url text primary key, "
            + "title text, "
            + "contentHtml text, "
            + "summary text, "
            + "likeCount text, "
            + "author text)";

    public DbHelper(Context context) {
        super(context, "One.db", null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(CREATE_TABLE_LIST);
        db.execSQL(CREATE_TABLE_MUSIC);
        db.execSQL(CREATE_TABLE_READ);
        db.execSQL(CREATE_TABLE_VIDEO);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
    }
}
