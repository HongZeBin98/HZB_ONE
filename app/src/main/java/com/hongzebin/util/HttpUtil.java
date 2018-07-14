package com.hongzebin.util;

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
    public interface HttpCallbackListenner {
        void onFinish(Object obj) throws Exception;

        void onError(Exception e);
    }

    /**
     * 发送HTTP请求
     *
     * @param address  请求URL
     * @param listener 回调监听
     */
    public static void sentHttpRequest(final String address, final HttpCallbackListenner listener) {
        new Thread(new Runnable() {
            @Override
            public void run() {
                HttpURLConnection connection = null;
                BufferedReader reader = null;
                try {
                    URL url = new URL(address);
                    connection = (HttpURLConnection) url.openConnection();
                    connection.setRequestMethod("GET");     //请求方式为GET
                    connection.setConnectTimeout(2500);     //连接超时范围
                    connection.setReadTimeout(2500);        //连接读取超时范围
                    InputStream in = connection.getInputStream();
                    reader = new BufferedReader(new InputStreamReader(in));
                    StringBuilder response = new StringBuilder();
                    String line;
                    while ((line = reader.readLine()) != null) {
                        response.append(line);
                    }
                    if (listener != null) {
                        listener.onFinish(response.toString());
                    }
                } catch (Exception e) {
                    if (listener != null) {
                        listener.onError(e);
                    }
                } finally {
                    if (reader != null) {
                        try {
                            reader.close();
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    }
                    if (connection != null) {
                        connection.disconnect();
                    }
                }
            }
        }).start();
    }

    /**
     * http请求，获取插画
     *
     * @param list     请求的itemid
     * @param flag     区别不同情况获取不同数量
     * @param listener 回调监听
     */
    public static void sentReqChahua(final List<String> list, final boolean flag, final HttpCallbackListenner listener) {
        new Thread(new Runnable() {
            @Override
            public void run() {
                HttpURLConnection connection = null;
                BufferedReader reader = null;
                List<String> chdList = new ArrayList<>();
                int size = 5;
                if (flag) {
                    size = list.size();
                }
                try {
                    for (int i = 0; i < size; i++) {
                        URL url = new URL(list.get(i));
                        connection = (HttpURLConnection) url.openConnection();
                        connection.setRequestMethod("GET");
                        connection.setConnectTimeout(8000);
                        connection.setReadTimeout(8000);
                        InputStream in = connection.getInputStream();
                        reader = new BufferedReader(new InputStreamReader(in));
                        StringBuilder response = new StringBuilder();
                        String line;
                        while ((line = reader.readLine()) != null) {
                            response.append(line);
                        }
                        chdList.add(response.toString());
                    }
                    if (listener != null) {
                        listener.onFinish(chdList);
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                } finally {
                    if (reader != null) {
                        try {
                            reader.close();
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    }
                    if (connection != null) {
                        connection.disconnect();
                    }
                }
            }
        }).start();
    }
}
