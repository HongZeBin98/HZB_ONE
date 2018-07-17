package com.hongzebin.util;

import android.os.AsyncTask;
import android.util.Log;

import com.hongzebin.bean.TypeOutline;

import java.util.ArrayList;
import java.util.List;

import okhttp3.OkHttpClient;
import okhttp3.Request;

public class DownloadTask extends AsyncTask<String, Integer, Integer> {

    public static final int TYPE_SUCCESS = 0;
    public static final int TYPE_FAILED = 1;

    private com.hongzebin.util.DownloadListener mListener;
    private int mTimes;

    public DownloadTask(com.hongzebin.util.DownloadListener listener, int amount){
        mListener = listener;
        mTimes = amount/10;
    }

    @Override
    protected Integer doInBackground(String... strings) {
        String id = "0";
        int progress = 1;
        String  url = "http://v3.wufazhuce.com:8000/api/channel/reading/more/" + id + "?channel=wdj&version=4.0.2&platform=android";
        List<TypeOutline> list = new ArrayList<>();

        try{
            OkHttpClient client = new OkHttpClient();
            for(int i = 0; i < mTimes; i++){
                Request request =new Request.Builder()
                        .url(url)
                        .build();
                String response = client.newCall(request).execute().body().string();
                PutingData.putJson(url, response);    //加载进数据库
                list.addAll(UsingJsonObject.getmUsingJsonObject().outlineJson(response, true));
                id = list.get(list.size() - 1).getmId();
                url = "http://v3.wufazhuce.com:8000/api/channel/reading/more/" + id + "?channel=wdj&version=4.0.2&platform=android";
                for(int j = 0; j < list.size(); j++){
                    String detailUrl = "http://v3.wufazhuce.com:8000/api/essay/" + list.get(j).getmItemId() + "?platform=android";
                    request = new Request.Builder()
                            .url(detailUrl)
                            .build();
                    response = client.newCall(request).execute().body().string();
                    PutingData.putJson(detailUrl, response);    //加载进数据库
                    publishProgress(progress);
                    progress += 2;
                }
                list.clear();
            }
        }
        catch (Exception e) {
            return TYPE_FAILED;
        }
        return TYPE_SUCCESS;
    }

    @Override
    protected void onProgressUpdate(Integer... values){
        int progress = values[0];
        mListener.onProgress(progress);
    }

    @Override
    protected void onPostExecute(Integer status){
        switch (status){
            case TYPE_FAILED:
                mListener.onFailed();
                break;
            case TYPE_SUCCESS:
                mListener.onSuccess();
                break;
            default:
                break;
        }
    }

}
