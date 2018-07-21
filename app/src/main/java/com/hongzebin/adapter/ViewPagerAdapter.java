package com.hongzebin.adapter;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.text.Spannable;
import android.text.SpannableStringBuilder;
import android.text.style.ImageSpan;

import com.hongzebin.R;
import com.hongzebin.util.OneApplication;

import java.util.List;

/**
 * viewpager适配器
 * Created by 洪泽彬
 */

public class ViewPagerAdapter extends android.support.v4.app.FragmentPagerAdapter {
    private List<Fragment> mFragments;  //用于保存用于滑动的Fragment对象
    private List<String> mTitleList;

    public ViewPagerAdapter(FragmentManager fm, List<Fragment> fragments, List<String> titles) {
        super(fm);
        mFragments = fragments;   //来返回当前要显示的fragment
        mTitleList = titles;
    }

    @Override
    public Fragment getItem(int position) {
        return mFragments.get(position);
    }

    @Override
    public int getCount() {
        return mFragments.size();
    }   //getCount()返回用于滑动的fragment总数

    @Override
    public CharSequence getPageTitle(int position) {
        //如果界面数量为2，代表是主界面的调用
        if (mFragments.size() == 2) {
            SpannableStringBuilder ssb = new SpannableStringBuilder(" " + mTitleList.get(position));
            if (position == 0)
                //为one标题前加图标
                ssb.setSpan(new ImageSpan(OneApplication.getmContext(), R.mipmap.one), 0, 1, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
            else if (position == 1)
                //为all标题前加图标
                ssb.setSpan(new ImageSpan(OneApplication.getmContext(), R.mipmap.all), 0, 1, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
            return ssb;
        }
        //如果界面数量不为2，代表是类型转换界面的调用
        else {
            return mTitleList.get(position);
        }
    }
}
