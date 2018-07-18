package com.hongzebin.util;

import android.content.ContentValues;
import android.util.Log;

import com.hongzebin.bean.MusicDetail;
import com.hongzebin.bean.ReadDetail;
import com.hongzebin.bean.VideoDetail;
import com.hongzebin.db.AddingAndQuerying;

import java.util.List;

/**
 * 把具体数据放入数据库
 * Created by 洪泽彬
 */

public class PutingData {

    /**
     * 把list的数据放入数据库
     *
     * @param list 要放的list数据
     * @param url  请求的url
     */
    public static void putList(List<String> list, String url) {
        ContentValues mValues = new ContentValues();
        mValues.put("url", url);
        mValues.put("json", ListTurning.listToStr(list));
        AddingAndQuerying.getmAddingAndQuerying().add(mValues, "LIST");
    }

    /**
     * 把str的数据放入数据库
     *
     * @param json 要放的字符串数据
     * @param url  请求的url
     */
    public static void putJson(String url, String json) {
        ContentValues mValues = new ContentValues();
        mValues.put("url", url);
        mValues.put("json", json);
        AddingAndQuerying.getmAddingAndQuerying().add(mValues, "LIST");
    }

    /**
     *把阅读详细的数据放入数据库
     * @param url 请求的url
     * @param rd 要放的阅读详细对象
     */
    public static  void putRead(String url, ReadDetail rd){
        ContentValues mValues = new ContentValues();
        mValues.put("url", url);
        mValues.put("title", rd.getHp_title());
        mValues.put("contentHtml", rd.getHp_content());
        mValues.put("authorDesc", rd.getAuthor().get(0).getDesc());
        mValues.put("likeCount", rd.getPraisenum());
        mValues.put("comCount", rd.getCommentnum());
        mValues.put("author", rd.getAuthor().get(0).getUser_name());
        AddingAndQuerying.getmAddingAndQuerying().add(mValues, "READ");
    }

    /**
     *把音乐详细的数据放入数据库
     * @param url 请求的url
     * @param md 要放的音乐详细对象
     */
    public static  void putMusic(String url, MusicDetail md){
        ContentValues mValues = new ContentValues();
        mValues.put("url", url);
        mValues.put("musicName", md.getTitle());
        mValues.put("cover", md.getCover());
        mValues.put("title", md.getStory_title());
        mValues.put("summary", md.getStory_summary());
        mValues.put("contentHtml", md.getStory());
        mValues.put("lyric", md.getLyric());
        mValues.put("info", md.getInfo());
        mValues.put("likeCount", md.getPraisenum());
        mValues.put("comCount", md.getCommentnum());
        mValues.put("author", md.getAuthor().getUser_name());

        AddingAndQuerying.getmAddingAndQuerying().add(mValues, "MUSIC");
    }

    /**
     *把影视详细的数据放入数据库
     * @param url 请求的url
     * @param vd 要放的影视详细对象
     */
    public static  void putVideo(String url, VideoDetail vd){
        ContentValues mValues = new ContentValues();
        mValues.put("url", url);
        mValues.put("title", vd.getTitle());
        mValues.put("contentHtml", vd.getContent());
        mValues.put("summary", vd.getSummary());
        mValues.put("likeCount", vd.getPraisenum());
        mValues.put("author", vd.getUser().getUser_name());
        AddingAndQuerying.getmAddingAndQuerying().add(mValues, "VIDEO");
    }
}
