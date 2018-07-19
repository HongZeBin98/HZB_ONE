package com.hongzebin.util;

import android.util.Log;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

/**
 * 使用HttpURLConnection请求
 * Created by 洪泽彬
 */

public class HttpUtil {
    /**
     * 设置接口,回调,里面有成功和失败的后的做法
     */
    public interface HttpCallbackListener {
        void onFinish(Object obj) throws Exception;

        void onError(VolleyError e);
    }

    /**
     * 发送HTTP请求
     *
     * @param address  请求URL
     * @param listener 回调监听
     */
    public static void sentHttpRequest(final String address, RequestQueue queue, final HttpCallbackListener listener) {
        if (queue == null) {
            queue = Volley.newRequestQueue(OneApplication.getmContext());
        }
        StringRequest stringRequest = new StringRequest(address,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String s) {
                        try {
                            listener.onFinish(s);
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError volleyError) {
                listener.onError(volleyError);
            }
        });
        queue.add(stringRequest);
    }

    /**
     * http请求，获取插画
     *
     * @param list     请求的itemid
     * @param flag     区别不同情况获取不同数量
     * @param listener 回调监听
     */
    public static void sentReqPicture(final List<String> list, RequestQueue queue, final boolean flag, final HttpCallbackListener listener) {

        final List<String> chdList = new ArrayList<>();
        int size = 5;
        if (flag) {
            size = list.size();
        }
        boolean sw = false;
        for (int i = 0; i < size; i++) {
            if(i == size -1){
                sw = true;
            }
            final boolean finalSw = sw;
            StringRequest stringRequest = new StringRequest(list.get(i),
                    new Response.Listener<String>() {
                        @Override
                        public void onResponse(String s) {
                            chdList.add(s);
                            if (finalSw){
                                try {
                                    listener.onFinish(chdList);
                                } catch (Exception e) {
                                    e.printStackTrace();
                                }
                            }
                        }
                    }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError volleyError) {
                    listener.onError(volleyError);
                }
            });
            queue.add(stringRequest);
        }

    }
}

