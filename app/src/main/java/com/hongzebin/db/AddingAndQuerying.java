package com.hongzebin.db;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import com.hongzebin.util.OneApplication;

import static com.hongzebin.util.Constant.SAVING_TIME;

/**
 * 对数据库数据进行增加和查询
 * Created by 洪泽彬
 */

public class AddingAndQuerying {

    private DbHelper mDbHelper = new DbHelper(OneApplication.getmContext());
    private SQLiteDatabase mDb = mDbHelper.getWritableDatabase();

    //构造方法私有化，这样外界就不能访问了
    private AddingAndQuerying() {
//        mDb.execSQL("drop table One");
//        Log.e("AddingAndQuerying", "88888888888888 ");
    }

    //运用单例的静态内部类方法
    private static class AddingAndQueryingHolder {
        private static AddingAndQuerying mAddingAndQuerying = new AddingAndQuerying();
    }

    public static AddingAndQuerying getmAddingAndQuerying() {
        return AddingAndQueryingHolder.mAddingAndQuerying;
    }

    /**
     * 把数据加载进数据库
     *
     * @param values 已经加载了需要储存数据的容器
     */
    public void add(ContentValues values) {
        mDb.replace("One", null, values);    //若数据已经存在进行替换，否则增加；
    }

    /**
     * 查询某数据是否存在数据库中而且未超时，在就返回相关数据，否则返回空
     */
    public String queryJson(String url) {
        String json = null;
        Cursor cursor = mDb.query("One", null, "url = ?", new String[]{url}, null, null, null);
        if (cursor.moveToFirst()) {  //判断是否存在该数据
            boolean flag = System.currentTimeMillis() - cursor.getLong(cursor.getColumnIndex("time")) < SAVING_TIME;
            if (flag) {  //判读是否超时
                json = cursor.getString(cursor.getColumnIndex("json"));
            }
        }
        cursor.close();
        return json;
    }
}

