package com.hongzebin.adapter;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.hongzebin.R;
import com.hongzebin.bean.PictureDetail;
import com.hongzebin.util.DownloadImageForListView;

import java.util.List;

/**
 * 插画listview适配器
 * Created by 洪泽彬
 */
public class PictureListAdapter extends BaseAdapter {
    private ListView mListView;
    private LayoutInflater mInflater;
    private int mResourceId;
    private List<PictureDetail> mObjects;

    public PictureListAdapter(Context context, ListView listView, int textViewResourceId, List<PictureDetail> objects) {
        mInflater = LayoutInflater.from(context);
        mObjects = objects;
        mResourceId = textViewResourceId;
        mListView = listView;
    }

    @Override
    public int getCount() {
        return mObjects.size();
    }

    @Override
    public Object getItem(int position) {
        return mObjects.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        PictureDetail chd = mObjects.get(position);
        View view;
        ViewHolder viewHolder;
        if (mListView == null) {
            mListView = (ListView) parent;
        }
        //通过convertView对之前加载好的布局进行缓存
        if (convertView == null) {
            view = mInflater.inflate(mResourceId, parent,
                    false);  //false:只为附布局中声明的layout属性生效
            viewHolder = new ViewHolder();
            viewHolder.authorText = (TextView) view.findViewById(R.id.text_author);
            viewHolder.text = (TextView) view.findViewById(R.id.chahua_text);
            viewHolder.pictureText = (TextView) view.findViewById(R.id.picture_author);
            viewHolder.time = (TextView) view.findViewById(R.id.chahua_time);
            viewHolder.number = (TextView) view.findViewById(R.id.chahua_number);
            viewHolder.picture = (ImageView) view.findViewById(R.id.chahua_picture);
            viewHolder.picture = (ImageView) view.findViewById(R.id.chahua_picture);
            view.setTag(viewHolder);    //将ViewHolder存储在View中
        } else {
            view = convertView;
            viewHolder = (ViewHolder) view.getTag();
        }
        //给图片控件控件设置一个Tag
        viewHolder.picture.setImageResource(R.drawable.picturefail);
        viewHolder.picture.setTag(chd.getmImgURL());
        new DownloadImageForListView(mListView).execute(chd.getmImgURL());
        viewHolder.authorText.setText(chd.getmTextAuthor());
        viewHolder.text.setText(chd.getmText());
        viewHolder.pictureText.setText(chd.getmPictureText());
        viewHolder.time.setText(chd.getmTime());
        viewHolder.number.setText(chd.getmLikeCount());
        return view;
    }

    /**
     * 用于对控件实例进行缓存
     */
    private class ViewHolder {
        TextView authorText;
        TextView text;
        TextView pictureText;
        TextView time;
        TextView number;
        ImageView picture;
    }
}
