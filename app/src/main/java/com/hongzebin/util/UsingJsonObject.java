package com.hongzebin.util;

import com.hongzebin.bean.PictureDetail;
import com.hongzebin.bean.Comment;
import com.hongzebin.bean.MusicDetail;
import com.hongzebin.bean.ReadDetail;
import com.hongzebin.bean.TypeOutline;
import com.hongzebin.bean.VideoDetail;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

/**
 * 用JSONObject解析数据
 * Created by 洪泽彬
 */

public class UsingJsonObject {
    //构造方法私有化，这样外界就不能访问了
    private UsingJsonObject() {
    }

    //运用单例的静态内部类方法
    private static class UsingJsonObjectHolder {
        private static UsingJsonObject mUsingJsonObject = new UsingJsonObject();
    }

    public static UsingJsonObject getmUsingJsonObject() {
        return UsingJsonObjectHolder.mUsingJsonObject;
    }

    /**
     * 解析出类型列表信息
     *
     * @param jsonData 解析的数据
     * @param flag 如果为false表示只解析最新一条数据，否则解析全部
     * @return 解析后的数据
     */
    public List<TypeOutline> outlineJson(String jsonData, boolean flag) {
        List<TypeOutline> list = new ArrayList<>();
        int size = 1;
        TypeOutline to;
        try {
            JSONObject jsonObject = new JSONObject(jsonData);
            if (jsonObject.getString("res").equals("0")) {
                JSONArray jsona = new JSONArray(jsonObject.getString("data"));
                if(flag){
                    size = jsona.length();
                }
                for (int i = 0; i < size; i++) {
                    jsonObject = jsona.getJSONObject(i);
                    String id = jsonObject.getString("id");
                    String item_id = jsonObject.getString("item_id");
                    String title = jsonObject.getString("title");
                    String yinyan = jsonObject.getString("forward");
                    String pictureURL = jsonObject.getString("img_url");
                    String number = jsonObject.getString("like_count");
                    String time = jsonObject.getString("last_update_date");
                    String authorName = jsonObject.getJSONObject("author").getString("user_name");

                    to = new TypeOutline(id, authorName, item_id, title, yinyan, pictureURL, number, time);
                    list.add(to);
                }
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return list;
    }

    /**
     * 解析出插画id
     *
     * @param jsonData 解析的数据
     * @return 解析后的数据
     */
    public List<String> chaHuaIdJson(String jsonData) {
        List<String> list = new ArrayList<>();
        try {
            JSONArray jsona = new JSONArray(new JSONObject(jsonData).getString("data"));
            for (int i = 0; i < jsona.length(); i++) {
                String URL = "http://v3.wufazhuce.com:8000/api/hp/detail/" + jsona.getString(i) + "?version=3.5.0&platform=android";
                list.add(URL);
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return list;
    }

    /**
     * 解析出插画图片url
     *
     * @param jsonData 解析的数据
     * @return 解析后的数据
     */
    public String chaHuaURLJson(String jsonData) {
        String url = null;
        try {
            JSONObject jsono = new JSONObject(jsonData).getJSONObject("data");
            url = jsono.getString("hp_img_url");
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return url;
    }

    /**
     * 解析出插画详细信息
     *
     * @param jsonData 解析的数据
     * @return 解析后的数据
     */
    public PictureDetail chaHuaDetailJson(String jsonData) {
        PictureDetail chd = null;
        try {
            JSONObject jsono = new JSONObject(jsonData).getJSONObject("data");
            String id = jsono.getString("hpcontent_id");
            String text = jsono.getString("hp_content");
            String pictureText = jsono.getString("hp_author");
            String time = jsono.getString("last_update_date");
            String number = jsono.getString("praisenum");
            String textAuthors = jsono.getString("text_authors");
            String pictureURL = jsono.getString("hp_img_url");
            chd = new PictureDetail(id, pictureURL, pictureText, text, time, textAuthors, number);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return chd;
    }

    /**
     * 解析出阅读详细信息
     *
     * @param jsonData 解析的数据
     * @return 解析后的数据
     */
    public ReadDetail readDetailJson(String jsonData) {
        ReadDetail rd = null;
        try {
            JSONObject jsono = new JSONObject(jsonData).getJSONObject("data");
            String title = jsono.getString("hp_title");
            String author = jsono.getString("hp_author");
            String contentHtml = jsono.getString("hp_content");
            String authorDesc = jsono.getString("auth_it");
            String likeNum = jsono.getString("praisenum");
            String commentNum = jsono.getString("commentnum");
            rd = new ReadDetail(title, author, contentHtml, authorDesc,
                    likeNum, commentNum);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return rd;
    }

    /**
     * 解析出评论信息
     *
     * @param jsonData 解析的数据
     * @return 解析后的数据
     */
    public List<Comment> commentJson(String jsonData) {
        Comment com;
        List<Comment> list = new ArrayList<>();
        try {
            JSONArray jsona = new JSONObject(jsonData).getJSONObject("data").getJSONArray("data");
            for (int i = 0; i < jsona.length(); i++) {
                JSONObject jsono = jsona.getJSONObject(i);
                String text = jsono.getString("content");
                String user = jsono.getJSONObject("user").getString("user_name");
                String time = jsono.getString("created_at");
                String likeNum = jsono.getString("praisenum");
                com = new Comment(user, text, time, likeNum);
                list.add(com);
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return list;
    }

    /**
     * 解析出音乐详细信息
     *
     * @param jsonData 解析的数据
     * @return 解析后的数据
     */
    public MusicDetail musicDetailJson(String jsonData) {
        MusicDetail md = null;
        try {
            JSONObject jsono = new JSONObject(jsonData).getJSONObject("data");
            String musicName = jsono.getString("title");
            String coverURL = jsono.getString("cover");
            String title = jsono.getString("story_title");
            String summary = jsono.getString("story_summary");
            String storyHtml = jsono.getString("story");
            String lyric = jsono.getString("lyric");
            String info = jsono.getString("info");
            String likeNum = jsono.getString("praisenum");
            String comNum = jsono.getString("commentnum");
            String author = jsono.getJSONObject("story_author").getString("user_name");
            md = new MusicDetail(author, musicName, coverURL, title, summary, storyHtml, lyric, info, likeNum, comNum);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return md;
    }

    /**
     * 解析出影视详细信息
     *
     * @param jsonData 解析的数据
     * @return 解析后的数据
     */
    public VideoDetail videoDetailJson(String jsonData) {
        VideoDetail vd = null;
        try {
            JSONArray jsona = new JSONObject(jsonData).getJSONObject("data").getJSONArray("data");
            JSONObject jsono = jsona.getJSONObject(0);
            String title = jsono.getString("title");
            String text = jsono.getString("content");
            String likeNum = jsono.getString("praisenum");
            String author = jsono.getJSONObject("user").getString("user_name");
            String summary = jsono.getString("summary");
            vd = new VideoDetail(title, author, summary, text, likeNum);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return vd;
    }
}
