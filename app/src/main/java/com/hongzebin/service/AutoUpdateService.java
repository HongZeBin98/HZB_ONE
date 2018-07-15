package com.hongzebin.service;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.os.Message;
import android.os.SystemClock;
import android.util.Log;

import com.hongzebin.util.HttpUtil;
import com.hongzebin.util.PutingData;
import com.hongzebin.util.UsingJsonObject;

import java.util.ArrayList;
import java.util.List;

import static com.hongzebin.util.Constant.NOTNETWORKING_REMIND;

/**
 * 每隔12小时后台自动更新
 */
public class AutoUpdateService extends Service {

    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId){
        List<String> list = new ArrayList<>();

        list.add("http://v3.wufazhuce.com:8000/api/channel/music/more/0?platform=android");
        list.add("http://v3.wufazhuce.com:8000/api/channel/reading/more/0?channel=wdj&version=4.0.2&platform=android");
        list.add("http://v3.wufazhuce.com:8000/api/channel/movie/more/0?platform=android");

        for(String x: list){
            updateData(x);
        }
        updateDataForChaHua("http://v3.wufazhuce.com:8000/api/hp/idlist/0?version=3.5.0&platform=android");
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
        HttpUtil.sentHttpRequest(address, new HttpUtil.HttpCallbackListenner() {    //网络请求
            @Override
            public void onFinish(Object response) {
                PutingData.putStr(address, (String)response);    //加载进数据库
            }

            @Override
            public void onError(Exception e) {
            }
        });
    }

    /**
     * 二次http请求和解析
     *
     * @param address 下载url
     */
    private void updateDataForChaHua(final String address) {
        HttpUtil.sentHttpRequest(address, new HttpUtil.HttpCallbackListenner() {
            @Override
            public void onFinish(Object response) {
                List<String> list = UsingJsonObject.getmUsingJsonObject().chaHuaIdJson(response.toString());
                HttpUtil.sentReqChahua(list, true, new HttpUtil.HttpCallbackListenner() {
                    @Override
                    public void onFinish(Object response) {
                        List<String> listStr = (List<String>) response;
                        //储存入数据库
                        PutingData.putList(listStr, address);
                    }

                    @Override
                    public void onError(Exception e) {
                        Log.d("ChaHuaFragment", "--------------Error: ");
                        e.printStackTrace();
                    }
                });
            }

            @Override
            public void onError(Exception e) {
                Log.d("ChaHuaFragment", "--------------Error: ");
                e.printStackTrace();
            }
        });
    }
}