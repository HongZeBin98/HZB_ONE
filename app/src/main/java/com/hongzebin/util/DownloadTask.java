package com.hongzebin.util;

import android.os.AsyncTask;

import com.hongzebin.bean.ReadDetail;
import com.hongzebin.bean.TypeOutline;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

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
        int progress = 0;
        String  url = ApiConstant.refreshReadApi(id);
        List<TypeOutline> list = new ArrayList<>();
        try{
            OkHttpClient client = new OkHttpClient();
            for(int i = 0; i < mTimes; i++){
                Request request =new Request.Builder()
                        .url(url)
                        .build();
                //Objects.requireNonNull方法可以判断对象是否为空，空的时候报空指针异常。
                String response = Objects.requireNonNull(client.newCall(request).execute().body()).string();
                PutingData.putJson(url, response);    //加载进数据库
                list.addAll(UsingGson.getUsingGson().outlineGson(response));
                id = list.get(list.size() - 1).getId();
                url = ApiConstant.refreshReadApi(id);
                for(int j = 0; j < list.size(); j++){
                    String detailUrl = ApiConstant.getReadAddress(list.get(j).getItem_id());
                    request = new Request.Builder()
                            .url(detailUrl)
                            .build();
                    response = Objects.requireNonNull(client.newCall(request).execute().body()).string();
                    ReadDetail readDetail = UsingGson.getUsingGson().readDetailGson(response);
                    PutingData.putRead(detailUrl, readDetail);    //加载进数据库
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
