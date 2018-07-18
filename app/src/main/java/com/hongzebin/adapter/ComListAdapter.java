package com.hongzebin.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.ListView;
import android.widget.TextView;

import com.hongzebin.R;
import com.hongzebin.bean.Comment;

import java.util.List;

/**
 * 评论列表适配器
 * Created by 洪泽彬
 */

public class ComListAdapter extends BaseAdapter {
    private LayoutInflater mInflater;
    private int mResourceId;
    private List<Comment> mObjects;

    public ComListAdapter(Context context, int textViewResourceId, List<Comment> objects) {
        mResourceId = textViewResourceId;
        mObjects = objects;
        mInflater = LayoutInflater.from(context);
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
        Comment com = mObjects.get(position);
        View view;
        ViewHolder viewHolder;
        //通过convertView对之前加载好的布局进行缓存
        if (convertView == null) {
            view = mInflater.inflate(mResourceId, parent,
                    false);  //false:只为附布局中声明的layout属性生效
            viewHolder = new ViewHolder();
            viewHolder.user = (TextView) view.findViewById(R.id.com_user);
            viewHolder.text = (TextView) view.findViewById(R.id.com_text);
            viewHolder.likeNum = (TextView) view.findViewById(R.id.com_likenum);
            viewHolder.time = (TextView) view.findViewById(R.id.com_time);
            view.setTag(viewHolder);    //将ViewHolder存储在View中
        } else {
            view = convertView;
            viewHolder = (ViewHolder) view.getTag();    //重新获取viewHolder
        }
        viewHolder.user.setText(com.getUser().getUser_name());
        viewHolder.text.setText(com.getContent());
        viewHolder.likeNum.setText(com.getPraisenum());
        viewHolder.time.setText(com.getUpdated_at());
        return view;
    }

    /**
     * 用于对控件实例进行缓存
     */
    private class ViewHolder {
        TextView user;
        TextView time;
        TextView text;
        TextView likeNum;
    }
}
