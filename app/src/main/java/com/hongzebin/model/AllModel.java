package com.hongzebin.model;

import com.android.volley.RequestQueue;
import com.android.volley.VolleyError;
import com.hongzebin.util.HttpUtil;
import com.hongzebin.util.UsingGson;

import java.util.ArrayList;
import java.util.List;

public class AllModel {

    public static void getDataFromNetwork(final String address, final RequestQueue mQueue, final AllCallback callback) {
        HttpUtil.sentHttpRequest(address, null, new HttpUtil.HttpCallbackListener() {
            @Override
            public void onFinish(Object response) {
                HttpUtil.sentReqPicture(UsingGson.getUsingGson().chaHuaIdJson(response.toString()), mQueue, false, new HttpUtil.HttpCallbackListener() {
                    @Override
                    public void onFinish(Object response) {
                        List<String> listStr = new ArrayList<>();
                        for (String x : (List<String>) response) {
                            String str = UsingGson.getUsingGson().chaHuaURLJson(x);
                            listStr.add(str);
                        }
                        callback.onFinish(listStr);
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
}
