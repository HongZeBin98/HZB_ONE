package com.hongzebin.fragment;

import android.graphics.Picture;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
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
import com.hongzebin.activity.MusicDetailActivity;
import com.hongzebin.adapter.GlobalAdapter;
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
    private RecyclerView mRecyclerView;
    private View mView;
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
        mRecyclerView = (RecyclerView) mView.findViewById(R.id.type_recyclerView);
        mRefresh = (SwipeRefreshLayout) mView.findViewById(R.id.refresh);
        LinearLayoutManager linearLayoutManager =new LinearLayoutManager(OneApplication.getmContext());
        mRecyclerView.setLayoutManager(linearLayoutManager);
        mAdapter = new PictureListAdapter(new ArrayList<PictureDetail>(), R.layout.chahualistview, mRecyclerView);
        //刷新监听
        mRefresh.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                httpRequest(REFRESH_LOADING, PICTURE_ADDRESS);
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
            mAdapter.addData(mList, false);
            setCallback();
            mRecyclerView.setAdapter(mAdapter);
        } else if (mes == REFRESH_LOADING) {
            mList = new ArrayList<>();
            mList.addAll(list);
            mAdapter.addData(mList, false);
            setCallback();
            mAdapter.notifyDataSetChanged();
            mRefresh.setRefreshing(false);      //隐藏刷新图标
        }else if(mes == ADD_LOADING){
            mList.addAll(list);
            mAdapter.addData(list, false);
            mAdapter.notifyDataSetChanged();
            mAdapter.setLoading(false);
        }
    }

    /**
     * 实例回调接口
     */
    private void setCallback(){
        mAdapter.setCallback(new GlobalAdapter.OnCallback() {
            @Override
            public void onClickItem(int position) {
            }

            @Override
            public void onLoadMore() {
                mId = mList.get(mList.size() - 1).getHpcontent_id();
                mAddress = ApiConstant.refreshPictureApi(mId);
                getData(ADD_LOADING, "LIST");
            }
        });
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
