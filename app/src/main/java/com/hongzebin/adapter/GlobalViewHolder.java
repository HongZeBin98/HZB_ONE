package com.hongzebin.adapter;

import android.graphics.drawable.Drawable;
import android.support.v7.widget.RecyclerView;
import android.util.SparseArray;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

public class GlobalViewHolder extends RecyclerView.ViewHolder {

    private SparseArray<View> mViews;
    private View mItemView;

    public GlobalViewHolder(View itemView) {
        super(itemView);
        mItemView = itemView;
        mViews = new SparseArray<>();
    }

    public static GlobalViewHolder createGlobalViewHolder(ViewGroup parent, int layoutId){
        View view  = LayoutInflater.from(parent.getContext()).inflate(layoutId, parent, false);
        return new GlobalViewHolder(view);
    }

    public <T extends View> T getView(int viewId){
        View view = mViews.get(viewId);
        if(view == null){
            view = mItemView.findViewById(viewId);
            mViews.put(viewId, view);
        }
        return (T)view;
    }

    public GlobalViewHolder setText(int viewId, String text){
        TextView view = getView(viewId);
        view.setText(text);
        return this;
    }

    public View getmItemView(){
        return mItemView;
    }

    public GlobalViewHolder setDrawable(int viewId, Drawable drawable){
        ImageView view = getView(viewId);
        view.setImageDrawable(drawable);
        return this;
    }
}
