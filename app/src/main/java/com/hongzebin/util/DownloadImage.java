package com.hongzebin.util;

import android.graphics.drawable.Drawable;
import android.os.AsyncTask;
import android.util.Log;
import android.widget.ImageView;

import java.io.IOException;
import java.net.URL;

/**
 * 下载图片
 * Created by 洪泽彬
 */

public class DownloadImage extends AsyncTask<String, Void, Drawable> {

    private ImageView picture;

    public DownloadImage() {
    }

    public DownloadImage(ImageView picture) {
        this.picture = picture;
    }

    //后台中下载图片
    public Drawable doInBackground(String... urls) {
        return loadImageFromNetwork(urls[0]);
    }

    //主线程更新UI
    public void onPostExecute(Drawable result) {
        picture.setImageDrawable(result);
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
        } catch (Exception e) {
            Log.e("DownloadImage", Log.getStackTraceString(e) );
        }
        if (drawable == null) {
            Log.d("test", "null drawable");
        } else {
            Log.d("test", "have drawable");
        }
        return drawable;
    }
}
