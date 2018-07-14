package com.hongzebin.util;

import android.content.ContentValues;

import com.hongzebin.db.AddingAndQuerying;

import java.util.List;

/**
 * 把具体数据放入数据库
 * Created by 洪泽彬
 */

public class PutingData {

    /**
     * 把list的数据放入数据库
     *
     * @param list 要放的list数据
     * @param url  请求的url
     */
    public static void putList(List<String> list, String url) {
        ContentValues mValues = new ContentValues();
        mValues.put("url", url);
        mValues.put("json", ListTurning.listToStr(list));
        mValues.put("time", System.currentTimeMillis());
        AddingAndQuerying.getmAddingAndQuerying().add(mValues);
    }

    /**
     * 把str的数据放入数据库
     *
     * @param json 要放的字符串数据
     * @param url  请求的url
     */
    public static void putStr(String url, String json) {
        ContentValues mValues = new ContentValues();
        mValues.put("url", url);
        mValues.put("json", json);
        mValues.put("time", System.currentTimeMillis());
        AddingAndQuerying.getmAddingAndQuerying().add(mValues);
    }
}
