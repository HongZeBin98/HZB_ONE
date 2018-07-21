package com.hongzebin.adapter;

import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AccelerateInterpolator;
import android.widget.ProgressBar;

import com.hongzebin.R;

import java.util.ArrayList;
import java.util.List;

import static com.hongzebin.util.Constant.TYPE_LOAD_MORE;
import static com.hongzebin.util.Constant.TYPE_NORMAL;

public abstract class GlobalAdapter<T> extends RecyclerView.Adapter<GlobalViewHolder> {

    private List<T> mData;
    private int mLayoutId;
    private OnCallback callback;
    private boolean isLoading;
    private RecyclerView mRecyclerView;

    private int visibleThreshold = 5;
    private boolean canLoadMore = true;

    public abstract void convert(GlobalViewHolder viewHolder, T item);

    public interface OnCallback {
        void onClickItem(int position);
        void onLoadMore();
    }

    public GlobalAdapter(List<T> mData, int mLayoutId, RecyclerView recyclerView) {
        this.mData = mData;
        this.mLayoutId = mLayoutId;
        mRecyclerView = recyclerView;
        recyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);
                LinearLayoutManager linearLayoutManager = (LinearLayoutManager) recyclerView.getLayoutManager();
                int itemCount = linearLayoutManager.getItemCount();
                int lastPosition  = linearLayoutManager.findLastVisibleItemPosition();
                //如果当前不是正在加载更多，并且到了该加载更多的位置，加载更多。
                if(!isLoading && (lastPosition >= (itemCount - visibleThreshold))){
                    if (canLoadMore && callback != null){
                        isLoading = true;
                        callback.onLoadMore();
                    }
                }
            }
        });
    }

    @Override
    public GlobalViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        if(viewType == TYPE_LOAD_MORE){
            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_load_more, parent, false);
            ProgressBar progressBar = (ProgressBar) view.findViewById(R.id.pb_loading);
            progressBar.setInterpolator(new AccelerateInterpolator(2));
            progressBar.setIndeterminate(true);
            return new GlobalViewHolder(view);
        }else {
            final GlobalViewHolder globalViewHolder = GlobalViewHolder.createGlobalViewHolder(parent, mLayoutId);
            globalViewHolder.getmItemView().setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if (callback != null) {
                        callback.onClickItem(globalViewHolder.getAdapterPosition());
                    }
                }
            });
            return globalViewHolder;
        }
    }

    @Override
    public void onBindViewHolder(final GlobalViewHolder globalViewHolder, final int position) {
        if(getItemViewType(position) == TYPE_LOAD_MORE){
            View itemView = globalViewHolder.itemView;
            //判定是不是可以加载更多或则正在加载
            if (canLoadMore && isLoading) {
                if (itemView.getVisibility() != View.VISIBLE) {
                    itemView.setVisibility(View.VISIBLE);
                }
            }else if(itemView.getVisibility() == View.VISIBLE){
                itemView.setVisibility(View.GONE);
            }
        }else{
            convert(globalViewHolder, mData.get(position));
        }
    }

    @Override
    public int getItemCount(){
        return mData.size() + 1;
    }

    @Override
    public int getItemViewType(int position){
        if (position == getItemCount() - 1) {
            return TYPE_LOAD_MORE;
        }else {
            return TYPE_NORMAL;
        }
    }

    public void setLoading(boolean loading) {
        isLoading = loading;
    }

    public void addData(List list, boolean create){
        if (create == true){
            mData = new ArrayList<>();
        }
        mData.addAll(list);
//        notifyDataSetChanged();
    }

    public void setCallback(OnCallback callback) {
        this.callback = callback;
    }

}
