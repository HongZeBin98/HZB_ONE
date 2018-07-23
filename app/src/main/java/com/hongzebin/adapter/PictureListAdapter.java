package com.hongzebin.adapter;

import android.content.Context;
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
import com.hongzebin.bean.PictureDetail;
import com.hongzebin.bean.TypeOutline;
import com.hongzebin.util.DownloadImageForListView;

import java.util.List;

/**
 * 插画listview适配器
 * Created by 洪泽彬
 */
public class PictureListAdapter extends GlobalAdapter<PictureDetail> {

    private RecyclerView mRecyclerView;

    public PictureListAdapter(List<PictureDetail> mDatas, int mLayoutId, RecyclerView recyclerView) {
        super(mDatas, mLayoutId, recyclerView);
        mRecyclerView = recyclerView;
    }

    @Override
    public void convert(GlobalViewHolder viewHolder, PictureDetail item) {
        viewHolder.setText(R.id.text_author, item.getText_authors())
                .setText(R.id.chahua_text, item.getHp_content())
                .setText(R.id.picture_author, item.getHp_author())
                .setText(R.id.chahua_time, item.getLast_update_date())
                .setText(R.id.chahua_number, item.getPraisenum())
                .pictureSetTag(R.id.chahua_picture, item.getHp_img_url(), mRecyclerView);

        new DownloadImageForListView(viewHolder, R.id.chahua_picture, mRecyclerView).execute(item.getHp_img_url());
    }
}
