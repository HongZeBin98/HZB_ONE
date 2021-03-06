package com.hongzebin.util;

import android.graphics.drawable.Drawable;
import android.os.AsyncTask;
import android.support.v7.widget.RecyclerView;
import android.widget.ImageView;
import android.widget.ListView;

import com.hongzebin.adapter.GlobalViewHolder;

import java.io.IOException;
import java.net.URL;

/**
 * 下载图片，用于解决listview里面异步并且有convertView出现图片错乱的现象
 * Created by 洪泽彬
 */

public class DownloadImageForListView extends AsyncTask<String, Void, Drawable> {
    private String mImageUrl;
    GlobalViewHolder viewHolder;
    RecyclerView recyclerView;
    int viewId;

    public DownloadImageForListView(GlobalViewHolder viewHolder, int viewId, RecyclerView recyclerView) {
        this.viewHolder = viewHolder;
        this.recyclerView = recyclerView;
        this.viewId = viewId;
    }

    //后台中下载图片
    public Drawable doInBackground(String... urls) {
        mImageUrl = urls[0];
        return loadImageFromNetwork(mImageUrl);
    }

    //主线程更新UI
    public void onPostExecute(Drawable result) {
        ImageView imageView = (ImageView)recyclerView.findViewWithTag(mImageUrl);
        if(imageView != null && result != null){
            viewHolder.setDrawable(viewId, result);
        }
    }

    /**
     * 下载图片
     *
     * @param url 下载的URL
     * @return 有图片的drawable
     */
    public Drawable loadImageFromNetwork(String url) {
        Drawable drawable = null;
        try {
            drawable = Drawable.createFromStream(new URL(url).openStream(), "image.jpg");
        } catch (IOException e) {
            e.printStackTrace();
        }
        return drawable;
    }
}
