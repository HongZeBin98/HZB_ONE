package com.hongzebin.db;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import com.hongzebin.bean.MusicDetail;
import com.hongzebin.bean.ReadDetail;
import com.hongzebin.bean.VideoDetail;
import com.hongzebin.util.OneApplication;

import static com.hongzebin.util.Constant.SAVING_TIME;

/**
 * 对数据库数据进行增加和查询
 * Created by 洪泽彬
 */

public class AddingAndQuerying {

    private DbHelper mDbHelper = new DbHelper(OneApplication.getmContext());
    private SQLiteDatabase mDb = mDbHelper.getWritableDatabase();

    //构造方法私有化，这样外界就不能访问了
    private AddingAndQuerying() {
    }

    //运用单例的静态内部类方法
    private static class AddingAndQueryingHolder {
        private static AddingAndQuerying mAddingAndQuerying = new AddingAndQuerying();
    }

    public static AddingAndQuerying getmAddingAndQuerying() {
        return AddingAndQueryingHolder.mAddingAndQuerying;
    }

    /**
     * 把数据加载进数据库
     *
     * @param values 已经加载了需要储存数据的容器
     */
    public void add(ContentValues values, String tableName) {
        mDb.replace(tableName, null, values);    //若数据已经存在进行替换，否则增加；
    }

    /**
     * 查询某数据是否存在数据库中，在就返回相关数据，否则返回空
     */
    public Object query(String url, String tableName) {
        Cursor cursor = mDb.query(tableName, null, "url = ?", new String[]{url}, null, null, null);
        if (cursor.moveToFirst()) {  //判断是否存在该数据
            if (tableName.equals("LIST")){
                String json = cursor.getString(cursor.getColumnIndex("json"));
                cursor.close();
                return json;
            } else if (tableName.equals("READ")){
                String title = cursor.getString(cursor.getColumnIndex("title"));
                String contentHtml = cursor.getString(cursor.getColumnIndex("contentHtml"));
                String authorDesc = cursor.getString(cursor.getColumnIndex("authorDesc"));
                String likeCount = cursor.getString(cursor.getColumnIndex("likeCount"));
                String comCount = cursor.getString(cursor.getColumnIndex("comCount"));
                String author = cursor.getString(cursor.getColumnIndex("author"));

                ReadDetail readDetail = new ReadDetail(title, author, contentHtml, authorDesc, likeCount, comCount);
                return readDetail;
            } else if (tableName.equals("MUSIC")){
                String musicName = cursor.getString(cursor.getColumnIndex("musicName"));
                String cover = cursor.getString(cursor.getColumnIndex("cover"));
                String title = cursor.getString(cursor.getColumnIndex("title"));
                String summary = cursor.getString(cursor.getColumnIndex("summary"));
                String contentHtml = cursor.getString(cursor.getColumnIndex("contentHtml"));
                String lyric = cursor.getString(cursor.getColumnIndex("lyric"));
                String info = cursor.getString(cursor.getColumnIndex("info"));
                String likeCount = cursor.getString(cursor.getColumnIndex("likeCount"));
                String comCount = cursor.getString(cursor.getColumnIndex("comCount"));
                String author = cursor.getString(cursor.getColumnIndex("author"));

                MusicDetail musicDetail = new MusicDetail(musicName, cover, title, summary, contentHtml, lyric, info, likeCount, comCount, author);
                return musicDetail;
            } else if (tableName.equals("VIDEO")){
                String title = cursor.getString(cursor.getColumnIndex("title"));
                String contentHtml = cursor.getString(cursor.getColumnIndex("contentHtml"));
                String summary = cursor.getString(cursor.getColumnIndex("summary"));
                String likeCount = cursor.getString(cursor.getColumnIndex("likeCount"));
                String author = cursor.getString(cursor.getColumnIndex("author"));

                VideoDetail videoDetail = new VideoDetail(title, author, summary, contentHtml, likeCount);
                return videoDetail;
            }
        }
        return null;
    }
}

