package com.hongzebin.adapter;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.hongzebin.R;
import com.hongzebin.bean.TypeOutline;
import com.hongzebin.util.DownloadImage;
import com.hongzebin.util.DownloadImageForListView;

import java.util.List;

/**
 * 类型列表适配器
 * Created by 洪泽彬
 */

public class TypeListAdapter extends GlobalAdapter<TypeOutline> {

    private RecyclerView mRecyclerView;

    public TypeListAdapter(List<TypeOutline> mData, int mLayoutId, RecyclerView recyclerView) {
        super(mData, mLayoutId , recyclerView);
        mRecyclerView = recyclerView;
    }

    @Override
    public void convert(GlobalViewHolder viewHolder, TypeOutline item) {
        viewHolder.setText(R.id.list_author, item.getAuthor().getUser_name())
                .setText(R.id.list_title, item.getTitle())
                .setText(R.id.list_forward, item.getForward())
                .setText(R.id.list_time, item.getPost_date())
                .setText(R.id.list_number, item.getLike_count())
                .pictureSetTag(R.id.list_picture, item.getImg_url(), mRecyclerView);

        new DownloadImageForListView(viewHolder, R.id.list_picture, mRecyclerView).execute(item.getImg_url());
    }
}
