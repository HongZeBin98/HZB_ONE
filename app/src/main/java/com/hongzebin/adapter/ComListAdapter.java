package com.hongzebin.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.hongzebin.R;
import com.hongzebin.bean.Comment;

import java.util.List;

/**
 * 评论列表适配器
 * Created by 洪泽彬
 */

public class ComListAdapter extends ArrayAdapter<Comment> {

    private int mResourceId;

    public ComListAdapter(Context context, int textViewResourceId, List<Comment> objects) {
        super(context, textViewResourceId, objects);
        mResourceId = textViewResourceId;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        Comment com = getItem(position);
        View view;
        ViewHolder viewHolder;
        //通过convertView对之前加载好的布局进行缓存
        if (convertView == null) {
            view = LayoutInflater.from(getContext()).inflate(mResourceId, parent,
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
        viewHolder.user.setText(com.getmUser());
        viewHolder.text.setText(com.getmText());
        viewHolder.likeNum.setText(com.getmLikeNum());
        viewHolder.time.setText(com.getmTime());
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
