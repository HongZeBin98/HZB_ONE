package com.hongzebin.activity;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.BitmapDrawable;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.NotificationCompat;
import android.support.v4.view.ViewPager;

import com.hongzebin.R;
import com.hongzebin.adapter.ViewPagerAdapter;
import com.hongzebin.bean.TypeOutline;
import com.hongzebin.fragment.AllFragment;
import com.hongzebin.fragment.OneFragment;
import com.hongzebin.model.MainCallback;
import com.hongzebin.model.MainModel;
import com.hongzebin.service.AutoUpdateService;
import com.hongzebin.util.DownloadImage;
import com.hongzebin.util.GlobalTools;
import com.hongzebin.util.OneApplication;
import com.hongzebin.util.UsingGson;

import java.util.ArrayList;
import java.util.List;

import static com.hongzebin.util.ApiConstant.READ_ADDRESS;

/**
 * 主界面，通过viewpager有one和all页面
 * Created by 洪泽彬
 */
public class MainActivity extends FragmentActivity {
    private List<String> mTitleList;
    private List<Fragment> mFragmentList;
    private ViewPager mViewPager;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.pageviewpager);
        //激活后台更新服务
        Intent intent = new Intent(this, AutoUpdateService.class);
        startService(intent);
        //判断是否联网，若联网才有通知
        if(GlobalTools.isNetworkAvailable(OneApplication.getmContext())){
            httpRequest(READ_ADDRESS);
        }

        mViewPager = (ViewPager) findViewById(R.id.viewpager);

        //界面列表
        mFragmentList = new ArrayList<Fragment>();
        mFragmentList.add(new OneFragment());
        mFragmentList.add(new AllFragment());
        //标题列表
        mTitleList = new ArrayList<String>();
        mTitleList.add(" ");
        mTitleList.add(" ");
        ViewPagerAdapter adapter = new ViewPagerAdapter(getSupportFragmentManager(), mFragmentList, mTitleList);
        mViewPager.setAdapter(adapter);
    }

    /**
     * 网络请求最新第一条数据，加载入通知
     *
     * @param address 请求的URL
     */
    private void httpRequest(final String address) {
        MainModel.getDataFromNetwork(address, new MainCallback() {
            @Override
            public void onFinish(Object response) {
                Context context = OneApplication.getmContext();
                //设置通知被点击后打开的页面
                Intent intent = new Intent(context, TypeActivity.class);
                intent.putExtra("number", 1);
                final PendingIntent pi = PendingIntent.getActivities(context, 0, new Intent[]{intent}, 0);

                final TypeOutline to =  UsingGson.getUsingGson().outlineGson((String)response).get(0);
                new Thread(){
                    @Override
                    public void run(){
                        BitmapDrawable bd = (BitmapDrawable) new DownloadImage().loadImageFromNetwork(to.getImg_url());
                        //设置通知
                        NotificationManager manager = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);
                        Notification notification = new NotificationCompat.Builder(OneApplication.getmContext())
                                .setLargeIcon(bd.getBitmap())
                                .setContentTitle(to.getTitle())
                                .setContentText(to.getForward())
                                .setSmallIcon(R.mipmap.notification)
                                .setDefaults(NotificationCompat.DEFAULT_ALL)
                                .setContentIntent(pi)
                                .setAutoCancel(true)
                                .build();
                        manager.notify(2, notification);    //启动通知
                    }
                }.start();
            }

            @Override
            public void onFail() {
            }
        });
    }
}

