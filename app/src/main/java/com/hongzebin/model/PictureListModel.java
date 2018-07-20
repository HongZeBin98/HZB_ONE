package com.hongzebin.model;

import com.android.volley.RequestQueue;
import com.android.volley.VolleyError;
import com.hongzebin.bean.PictureDetail;
import com.hongzebin.db.AddingAndQuerying;
import com.hongzebin.util.HttpUtil;
import com.hongzebin.util.ListTurning;
import com.hongzebin.util.PutingData;
import com.hongzebin.util.UsingGson;

import java.util.ArrayList;
import java.util.List;


public class PictureListModel {

    public static void getDataFromNetwork(final String address, final RequestQueue mQueue, final PictureListCallback callback) {
        HttpUtil.sentHttpRequest(address, null, new HttpUtil.HttpCallbackListener() {
            @Override
            public void onFinish(Object response) {
                HttpUtil.sentReqPicture(UsingGson.getUsingGson().chaHuaIdJson(response.toString()), mQueue, true, new HttpUtil.HttpCallbackListener() {
                    @Override
                    public void onFinish(Object response) {
                        List<String> listStr = (List<String>) response;
                        List<PictureDetail> list = new ArrayList<>();
                        //储存入数据库
                        PutingData.putList(listStr, address);
                        for (String x : listStr) {
                            list.add(UsingGson.getUsingGson().chaHuaDetailGson(x));
                        }
                        callback.onFinish(list);
                    }

                    @Override
                    public void onError(VolleyError e) {
                        e.printStackTrace();
                    }
                });
            }

            @Override
            public void onError(VolleyError e) {
                callback.onFail();
            }
        });
    }

    public static void getDataFromBD(final String address, final String tableName, final PictureListCallback callback) {
        String jsonData;
        if ((jsonData = (String) AddingAndQuerying.getmAddingAndQuerying().query(address, tableName)) == null) {
            callback.onFail();
        } else {
            List<String> listStr = ListTurning.strToList(jsonData);
            List<PictureDetail> list = new ArrayList<>();
            for (String x : listStr) {
                list.add(UsingGson.getUsingGson().chaHuaDetailGson(x));
            }
            callback.onFinish(list);
        }
    }
}
