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
        mValues.put("title", rd.getmTitle());
        mValues.put("contentHtml", rd.getmContentHtml());
        mValues.put("authorDesc", rd.getmAuthorDesc());
        mValues.put("likeCount", rd.getmLikeNum());
        mValues.put("comCount", rd.getmCommentNum());
        mValues.put("author", rd.getmAuthor());
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
        mValues.put("musicName", md.getmMusicName());
        mValues.put("cover", md.getmCover());
        mValues.put("title", md.getmTitle());
        mValues.put("summary", md.getmSummary());
        mValues.put("contentHtml", md.getmStory());
        mValues.put("lyric", md.getmLyric());
        mValues.put("info", md.getmInfo());
        mValues.put("likeCount", md.getmLikeNum());
        mValues.put("comCount", md.getmCommentNum());
        mValues.put("author", md.getmAuthor());
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
        mValues.put("title", vd.getmTitle());
        mValues.put("contentHtml", vd.getmText());
        mValues.put("summary", vd.getmSummary());
        mValues.put("likeCount", vd.getmLikeNum());
        mValues.put("author", vd.getmUser());
        AddingAndQuerying.getmAddingAndQuerying().add(mValues, "VIDEO");
    }
}
