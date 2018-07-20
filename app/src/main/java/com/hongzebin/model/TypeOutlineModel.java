package com.hongzebin.model;

import com.android.volley.VolleyError;
import com.hongzebin.db.AddingAndQuerying;
import com.hongzebin.util.HttpUtil;
import com.hongzebin.util.PutingData;
import com.hongzebin.util.UsingGson;

public class TypeOutlineModel {

    public static void getDataFromNetwork(final String address, final TypeOutlineCallback callback) {
        HttpUtil.sentHttpRequest(address, null, new HttpUtil.HttpCallbackListener() {
            @Override
            public void onFinish(Object response) {
                String json = (String) response;
                PutingData.putJson(address, json);    //加载进数据库
                callback.onFinish(UsingGson.getUsingGson().outlineGson(json));
            }

            @Override
            public void onError(VolleyError e) {
                callback.onFail();
            }
        });
    }

    public static void getDataFromBD(final String address, final String tableName, final TypeOutlineCallback callback){
        String jsonData;
        if((jsonData = (String) AddingAndQuerying.getmAddingAndQuerying().query(address, tableName)) == null){
            callback.onFail();
        }else {
            callback.onFinish(UsingGson.getUsingGson().outlineGson(jsonData));
        }
    }
}
