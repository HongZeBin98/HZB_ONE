package com.hongzebin.model;

import com.android.volley.VolleyError;
import com.hongzebin.util.HttpUtil;

public class MainModel {
    public static void getDataFromNetwork(final String address, final MainCallback callback) {
        HttpUtil.sentHttpRequest(address, null, new HttpUtil.HttpCallbackListener() {
            @Override
            public void onFinish(Object response) {
                callback.onFinish(response);
            }

            @Override
            public void onError(VolleyError e) {
                callback.onFail();
            }
        });
    }
}
