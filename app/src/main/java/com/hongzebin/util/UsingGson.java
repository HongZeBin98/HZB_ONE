package com.hongzebin.util;

import android.util.Log;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
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

public class UsingGson {
    //构造方法私有化，这样外界就不能访问了
    private UsingGson() {
    }

    //运用单例的静态内部类方法
    private static class UsingGsonObjectHolder {
        private static UsingGson mUsingGson = new UsingGson();
    }

    public static UsingGson getUsingGson() {
        return UsingGsonObjectHolder.mUsingGson;
    }

    /**
     * 解析出类型列表信息
     *
     * @param jsonData 解析的数据
     * @return 解析后的数据
     */
    public List<TypeOutline> outlineGson(String jsonData) {
        List<TypeOutline> list = new ArrayList<>();
        TypeOutline to;
        try {
            JSONObject jsonObject = new JSONObject(jsonData);
            if (jsonObject.getString("res").equals("0")) {
                Gson gson = new Gson();
                list = gson.fromJson(jsonObject.getString("data"), new TypeToken<List<TypeOutline>>(){}.getType());
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
                String URL = ApiConstant.getPictureAddress(jsona.getString(i));
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
    public PictureDetail chaHuaDetailGson(String jsonData) {
        PictureDetail chd = null;
        try {
            JSONObject jsonObject = new JSONObject(jsonData);
            if (jsonObject.getString("res").equals("0")) {
                Gson gson = new Gson();
                chd = gson.fromJson(jsonObject.getString("data"), PictureDetail.class);
            }
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
    public ReadDetail readDetailGson(String jsonData) {
        ReadDetail rd = null;
        try {
            JSONObject jsonObject = new JSONObject(jsonData);
            if (jsonObject.getString("res").equals("0")) {
                Gson gson = new Gson();
                rd = gson.fromJson(jsonObject.getString("data"), ReadDetail.class);
            }
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
    public List<Comment> commentGson(String jsonData) {
        Comment com;
        List<Comment> list = null;
        try {
            JSONObject jsonObject = new JSONObject(jsonData);
            if (jsonObject.getString("res").equals("0")) {
                JSONArray jsonArray = jsonObject.getJSONObject("data").getJSONArray("data");
                Gson gson = new Gson();
                list = gson.fromJson(jsonArray.toString(), new TypeToken<List<Comment>>(){}.getType());
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
    public MusicDetail musicDetailGson(String jsonData) {
        MusicDetail md = null;
        try {
            JSONObject jsonObject = new JSONObject(jsonData);
            if (jsonObject.getString("res").equals("0")) {
                Gson gson = new Gson();
                md = gson.fromJson(jsonObject.getString("data"), MusicDetail.class);
            }
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
    public VideoDetail videoDetailGson(String jsonData) {
        VideoDetail vd = null;
        try {
            JSONObject jsonObject = new JSONObject(jsonData);
            if (jsonObject.getString("res").equals("0")) {
                Gson gson = new Gson();
                JSONArray jsonArray = jsonObject.getJSONObject("data").getJSONArray("data");
                vd = gson.fromJson(jsonArray.get(0).toString(), VideoDetail.class);
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return vd;
    }
}
