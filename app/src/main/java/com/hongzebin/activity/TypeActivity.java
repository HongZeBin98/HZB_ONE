package com.hongzebin.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.view.PagerTabStrip;
import android.support.v4.view.ViewPager;
import android.util.TypedValue;

import com.hongzebin.R;
import com.hongzebin.adapter.FragAdapter;
import com.hongzebin.fragment.ReadFragment;
import com.hongzebin.fragment.MusicFragment;
import com.hongzebin.fragment.ChaHuaFragment;
import com.hongzebin.fragment.VideoFragment;

import java.util.ArrayList;
import java.util.List;

/**
 * 类型列表界面
 * Created by 洪泽彬
 */

public class TypeActivity extends FragmentActivity {
    private List<String> mTitleList;
    private List<Fragment> mFragmentList;
    private ViewPager mViewPager;
    private PagerTabStrip mPts;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.typeviewpager);
        Intent intent = getIntent();
        mViewPager = (ViewPager) findViewById(R.id.viewpager);
        mPts = (PagerTabStrip) findViewById(R.id.pagertab);
        mPts.setTextSize(TypedValue.COMPLEX_UNIT_SP, 19);   //设置滚动标题字大小

        mFragmentList = new ArrayList<Fragment>();
        mFragmentList.add(new ChaHuaFragment());
        mFragmentList.add(new ReadFragment());
        mFragmentList.add(new MusicFragment());
        mFragmentList.add(new VideoFragment());

        mTitleList = new ArrayList<String>();
        mTitleList.add("插画");
        mTitleList.add("阅读");
        mTitleList.add("音乐");
        mTitleList.add("影视");

        FragAdapter adapter = new FragAdapter(getSupportFragmentManager(), mFragmentList, mTitleList);
        mViewPager.setAdapter(adapter);
        mViewPager.setCurrentItem(intent.getIntExtra("number", -1)); //设置打开时默认页面
    }
}
