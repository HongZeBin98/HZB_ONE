package com.hongzebin.util;

import android.content.Context;

/**
 * 继承Application，获取context
 * Created by 洪泽彬
 */

public class OneApplication extends android.app.Application {
    private static Context mContext;

    @Override
    public void onCreate() {
        super.onCreate();
        mContext = getApplicationContext();
    }

    //获取context
    public static Context getmContext() {
        return mContext;
    }
}
