package com.hongzebin.fragment;

import android.graphics.Picture;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;

import com.android.volley.RequestQueue;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.Volley;
import com.hongzebin.R;
import com.hongzebin.adapter.PictureListAdapter;
import com.hongzebin.bean.PictureDetail;
import com.hongzebin.bean.TypeOutline;
import com.hongzebin.db.AddingAndQuerying;
import com.hongzebin.model.PictureListCallback;
import com.hongzebin.model.PictureListModel;
import com.hongzebin.model.TypeOutlineCallback;
import com.hongzebin.model.TypeOutlineModel;
import com.hongzebin.util.ApiConstant;
import com.hongzebin.util.HttpUtil;
import com.hongzebin.util.ListTurning;
import com.hongzebin.util.OneApplication;
import com.hongzebin.util.PutingData;
import com.hongzebin.util.UsingGson;

import java.util.ArrayList;
import java.util.List;

import static com.hongzebin.util.ApiConstant.PICTURE_ADDRESS;
import static com.hongzebin.util.Constant.ADD_LOADING;
import static com.hongzebin.util.Constant.NORMAL_LOADING;
import static com.hongzebin.util.Constant.NONETWORK_REMIND;
import static com.hongzebin.util.Constant.REFRESH_LOADING;


/**
 * 插画的列表页面
 * Created by 洪泽彬
 */
public class PictureFragment extends Fragment {
    private RequestQueue mQueue;
    private List<PictureDetail> mList;
    private PictureListAdapter mAdapter;
    private SwipeRefreshLayout mRefresh;
    private ListView mListView;
    private View mView;
    private String mJsonData;
    private View mFooterView;   //加载更多
    private int mCount = 0;     //设置加载更多后，初始显示的条的位置
    private String mId;    //请求http的URL的指定id
    private String mAddress;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        mId = "0";
        mQueue = Volley.newRequestQueue(OneApplication.getmContext());
        mAddress = ApiConstant.refreshPictureApi(mId);
        mList = new ArrayList<>();
        mView = inflater.inflate(R.layout.type, container, false);
        mFooterView = inflater.inflate(R.layout.loadingmore, null);
        mListView = (ListView) mView.findViewById(R.id.type_listview);
        mRefresh = (SwipeRefreshLayout) mView.findViewById(R.id.refresh);
        Button btn = (Button) mFooterView.findViewById(R.id.loading_btn);
        mListView.addFooterView(mFooterView);   //活动列表最后一条为加载更多
        //刷新监听
        mRefresh.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                httpRequest(REFRESH_LOADING, PICTURE_ADDRESS);
            }
        });
        //加载更多的按钮监听
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mCount = mAdapter.getCount();
                mId = mList.get(mList.size() - 1).getHpcontent_id();
                mAddress = ApiConstant.refreshPictureApi(mId);
                getData(ADD_LOADING, "LIST");
            }
        });
        getData(NORMAL_LOADING, "LIST");
        return mView;
    }

    /**
     * 解析实例适配器
     *
     * @param list http请求后的json数据
     * @param mes  区别不同情况
     */
    private void realizeAdapter(List<PictureDetail> list, int mes) {
        if (mes == NORMAL_LOADING) {
            mList.addAll(list);
            mAdapter = new PictureListAdapter(OneApplication.getmContext(), mListView, R.layout.chahualistview, mList);
            mListView.setAdapter(mAdapter);
        } else if (mes == REFRESH_LOADING) {
            mList = new ArrayList<>();
            mList.addAll(list);
            mAdapter = new PictureListAdapter(OneApplication.getmContext(), mListView, R.layout.chahualistview, mList);
            mAdapter.notifyDataSetChanged();
            mListView.setAdapter(mAdapter);
            mRefresh.setRefreshing(false);      //隐藏刷新图标
        } else if (mes == ADD_LOADING) {
            mList.addAll(list);
            mAdapter.notifyDataSetChanged();
            mListView.setAdapter(mAdapter);
            mListView.setSelection(mCount);
        }
    }

    /**
     * 二次http请求和解析
     *
     * @param mes 区别不同情况
     */
    private void httpRequest(final int mes, final String address) {
        PictureListModel.getDataFromNetwork(address, mQueue, new PictureListCallback() {
            @Override
            public void onFinish(List<PictureDetail> list) {
                realizeAdapter(list, mes);
            }

            @Override
            public void onFail() {
                Toast.makeText(getActivity(), "请联网后重试", Toast.LENGTH_SHORT).show();
                mRefresh.setRefreshing(false);      //隐藏刷新图标
            }
        });
    }

    /**
     * 判断数据库是否存在未超时可用数据，有就使用，无则请求
     *
     * @param mes 区别不同情况
     */
    private void getData(final int mes, String tableName) {
        PictureListModel.getDataFromBD(mAddress, tableName, new PictureListCallback() {
            @Override
            public void onFinish(List<PictureDetail> list) {
                realizeAdapter(list, mes);
            }
            @Override
            public void onFail() {
                httpRequest(mes, mAddress);
            }
        });
    }
}
