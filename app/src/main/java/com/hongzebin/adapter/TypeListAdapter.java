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
import com.hongzebin.bean.Author;
import com.hongzebin.bean.TypeOutline;
import com.hongzebin.util.DownloadImageForListView;

import java.util.List;

/**
 * 类型列表适配器
 * Created by 洪泽彬
 */

public class TypeListAdapter extends BaseAdapter {
    private ListView mListView;
    private int mResourceId;
    private LayoutInflater mInflater;
    private List<TypeOutline> mObjects;

    public TypeListAdapter(Context context, ListView listView, int textViewResourceId, List<TypeOutline> objects) {
        mResourceId = textViewResourceId;
        mInflater = LayoutInflater.from(context);
        mListView = listView;
        mObjects = objects;
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
        TypeOutline to = mObjects.get(position);
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
            viewHolder.author = (TextView) view.findViewById(R.id.list_author);
            viewHolder.title = (TextView) view.findViewById(R.id.list_title);
            viewHolder.forward = (TextView) view.findViewById(R.id.list_yinyan);
            viewHolder.time = (TextView) view.findViewById(R.id.list_time);
            viewHolder.number = (TextView) view.findViewById(R.id.list_number);
            viewHolder.picture = (ImageView) view.findViewById(R.id.list_picture);
            view.setTag(viewHolder);    //将ViewHolder存储在View中
        } else {
            view = convertView;
            viewHolder = (ViewHolder) view.getTag();
        }
        //给图片控件控件设置一个Tag
        String imgUrl = to.getImg_url();
        viewHolder.picture.setImageResource(R.drawable.picturefail);
        viewHolder.picture.setTag(imgUrl);
        new DownloadImageForListView(mListView).execute(imgUrl);
        viewHolder.title.setText(to.getTitle());
        viewHolder.forward.setText(to.getForward());
        viewHolder.author.setText(to.getAuthor().getUser_name());
        viewHolder.time.setText(to.getPost_date());
        viewHolder.number.setText(to.getLike_count());
        return view;
    }

    //用于对控件实例进行缓存
    private class ViewHolder {
        TextView title;
        TextView author;
        TextView forward;
        TextView time;
        TextView number;
        ImageView picture;
    }
}
