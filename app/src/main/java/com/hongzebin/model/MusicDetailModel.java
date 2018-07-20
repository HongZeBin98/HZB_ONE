package com.hongzebin.model;

import com.android.volley.VolleyError;
import com.hongzebin.bean.Comment;
import com.hongzebin.bean.MusicDetail;
import com.hongzebin.db.AddingAndQuerying;
import com.hongzebin.util.HttpUtil;
import com.hongzebin.util.PutingData;
import com.hongzebin.util.UsingGson;

import java.util.List;

import static com.hongzebin.util.Constant.DETAIL;

public class MusicDetailModel {
    public static void getDataFromNetwork(final int mes,final String address, final MusicDetailCallback callback) {
        HttpUtil.sentHttpRequest(address, null, new HttpUtil.HttpCallbackListener() {
            @Override
            public void onFinish(Object response) {
                if(mes == DETAIL){
                    MusicDetail musicDetail = UsingGson.getUsingGson().musicDetailGson((String) response);
                    PutingData.putMusic(address, musicDetail);    //加载进数据库
                    callback.onFinish(musicDetail);
                }else {
                    List<Comment> comList;
                    String string = (String) response;
                    PutingData.putJson(address, string);    //加载进数据库
                    comList = UsingGson.getUsingGson().commentGson(string);
                    callback.onFinish(comList);
                }
            }

            @Override
            public void onError(VolleyError e) {
                callback.onFail();
            }
        });
    }

    public static void getDataFromBD(final int mes, String detailUrl, String commentUrl, final String tableName, final MusicDetailCallback callback){
        String url;
        Object object;

        if (mes == DETAIL) {
            url = detailUrl;
        } else {
            url = commentUrl;
        }

        if((object = AddingAndQuerying.getmAddingAndQuerying().query(url, tableName)) == null){
            callback.onFail();
        }else {
            if (mes == DETAIL){
                callback.onFinish(object);
            }else{
                callback.onFinish(UsingGson.getUsingGson().commentGson((String) object));
            }
        }
    }
}
