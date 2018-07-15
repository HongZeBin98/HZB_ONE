package com.hongzebin.service;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.Service;
import android.content.Intent;
import android.os.Binder;
import android.os.IBinder;
import android.support.v4.app.NotificationCompat;
import android.widget.Toast;

import com.hongzebin.R;
import com.hongzebin.util.DownloadListener;
import com.hongzebin.util.DownloadTask;

public class DownloadService extends Service {

    private DownloadTask downloadTask;
    private DownloadBinder mBinder = new DownloadBinder();

    private DownloadListener listener = new DownloadListener() {
        @Override
        public void onProgress(int progress) {
            getNotificationManager().notify(1, getNotification("Downloading...", progress));
        }

        @Override
        public void onSuccess() {
            downloadTask = null;
            //下载成功时将前台服务通知关闭,并创建一个下载成功的通知
            stopForeground(true);
            getNotificationManager().notify(1, getNotification("Downloading Success", -1));
            Toast.makeText(DownloadService.this, "Download Success", Toast.LENGTH_SHORT).show();
        }

        @Override
        public void onFailed() {
            downloadTask = null;
            //下载失败时将前台服务通知关闭,并创建一个下载失败的通知
            stopForeground(true);
            getNotificationManager().notify(1, getNotification("Downloading Failed", -1));
            Toast.makeText(DownloadService.this, "Download Failed", Toast.LENGTH_SHORT).show();
        }
    };

    @Override
    public IBinder onBind(Intent intent) {
        return mBinder;
    }

    public class DownloadBinder extends Binder {

        public void startDownload(){
            if(downloadTask == null){
                downloadTask = new DownloadTask(listener, 50);
                downloadTask.execute();
                startForeground(1, getNotification("Downloading...", 0));
            }
        }
    }

    private NotificationManager getNotificationManager(){
        return (NotificationManager) getSystemService(NOTIFICATION_SERVICE);
    }

    private Notification getNotification(String title, int progress){
        NotificationCompat.Builder builder = new NotificationCompat.Builder(this)
                .setSmallIcon(R.mipmap.ic_launcher)
                .setContentTitle(title);
        if(progress >= 0){
            builder.setContentText(progress + "%").setProgress(100, progress, false);
        }
        return builder.build();
    }
}
