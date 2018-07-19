package com.hongzebin.service;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.os.SystemClock;

import com.android.volley.RequestQueue;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.Volley;
import com.hongzebin.util.HttpUtil;
import com.hongzebin.util.OneApplication;
import com.hongzebin.util.PutingData;
import com.hongzebin.util.UsingGson;

import java.util.ArrayList;
import java.util.List;

import static com.hongzebin.util.ApiConstant.MUSIC_ADDRESS;
import static com.hongzebin.util.ApiConstant.PICTURE_ADDRESS;
import static com.hongzebin.util.ApiConstant.READ_ADDRESS;
import static com.hongzebin.util.ApiConstant.VIDEO_ADDRESS;

/**
 * 每隔12小时后台自动更新
 */
public class AutoUpdateService extends Service {

    private RequestQueue mQueue;

    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId){
        mQueue = Volley.newRequestQueue(this);
        List<String> list = new ArrayList<>();

        list.add(MUSIC_ADDRESS);
        list.add(READ_ADDRESS);
        list.add(VIDEO_ADDRESS);

        for(String x: list){
            updateData(x);
        }
        updateDataForChaHua(PICTURE_ADDRESS);
        AlarmManager manager = (AlarmManager) getSystemService(ALARM_SERVICE);
        int anHour = 12*60*60*1000; //设置间隔时间为12小时
        long triggerAtTime = SystemClock.elapsedRealtime() + anHour;
        Intent i = new Intent(this, AutoUpdateService.class);
        PendingIntent pi = PendingIntent.getService(this, 0, i, 0);
        manager.cancel(pi); //取消定时服务
        manager.set(AlarmManager.ELAPSED_REALTIME_WAKEUP, triggerAtTime, pi);   //设置隔12小时后运行
        return super.onStartCommand(intent, flags, startId);
    }

    /**
     * 通过网络请求获取数据后加载进数据库
     *
     * @param address
     */
    private void updateData(final String address){
        HttpUtil.sentHttpRequest(address, mQueue, new HttpUtil.HttpCallbackListener() {    //网络请求
            @Override
            public void onFinish(Object response) {
                PutingData.putJson(address, (String)response);    //加载进数据库
            }

            @Override
            public void onError(VolleyError e) {
            }
        });
    }

    /**
     * 二次http请求和解析
     *
     * @param address 下载url
     */
    private void updateDataForChaHua(final String address) {
        HttpUtil.sentHttpRequest(address, mQueue, new HttpUtil.HttpCallbackListener() {
            @Override
            public void onFinish(Object response) {
                List<String> list = UsingGson.getUsingGson().chaHuaIdJson(response.toString());
                HttpUtil.sentReqPicture(list, mQueue,true, new HttpUtil.HttpCallbackListener() {
                    @Override
                    public void onFinish(Object response) {
                        List<String> listStr = (List<String>) response;
                        //储存入数据库
                        PutingData.putList(listStr, address);
                    }

                    @Override
                    public void onError(VolleyError e) {
                        e.printStackTrace();
                    }
                });
            }

            @Override
            public void onError(VolleyError e) {
                e.printStackTrace();
            }
        });
    }
}