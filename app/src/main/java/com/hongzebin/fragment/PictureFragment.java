package com.hongzebin.fragment;

import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;

import com.hongzebin.R;
import com.hongzebin.adapter.PictureListAdapter;
import com.hongzebin.bean.PictureDetail;
import com.hongzebin.db.AddingAndQuerying;
import com.hongzebin.util.ApiConstant;
import com.hongzebin.util.HttpUtil;
import com.hongzebin.util.ListTurning;
import com.hongzebin.util.OneApplication;
import com.hongzebin.util.PutingData;
import com.hongzebin.util.UsingJsonObject;

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
    private List<PictureDetail> mList;
    private PictureListAdapter mAdapter;
    private SwipeRefreshLayout mRefresh;
    private ListView mListView;
    private Handler mHandler;
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
        mAddress = ApiConstant.refreshPictureApi(mId);
        mList = new ArrayList<>();
        mView = inflater.inflate(R.layout.type, container, false);
        mFooterView = inflater.inflate(R.layout.loadingmore, null);
        mListView = (ListView) mView.findViewById(R.id.type_listview);
        mRefresh = (SwipeRefreshLayout) mView.findViewById(R.id.refresh);
        Button btn = (Button) mFooterView.findViewById(R.id.loading_btn);
        mListView.addFooterView(mFooterView);   //活动列表最后一条为加载更多
        //异步消息处理，接受消息
        mHandler = new Handler() {
            public void handleMessage(Message msg) {
                switch (msg.what) {
                    //正常加载
                    case NORMAL_LOADING:
                        mListView.setAdapter(mAdapter);
                        break;
                    //刷新加载
                    case REFRESH_LOADING:
                        mAdapter.notifyDataSetChanged();
                        mListView.setAdapter(mAdapter);
                        mRefresh.setRefreshing(false);      //隐藏刷新图标
                        break;
                    //加载更多
                    case ADD_LOADING:
                        mAdapter.notifyDataSetChanged();
                        mListView.setAdapter(mAdapter);
                        mListView.setSelection(mCount);
                        break;
                    //无网络提示
                    case NONETWORK_REMIND:
                        Toast.makeText(getActivity(), "请联网后重试", Toast.LENGTH_SHORT).show();
                        mRefresh.setRefreshing(false);      //隐藏刷新图标
                        break;
                    default:
                        break;
                }
            }
        };
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
                mId = mList.get(mList.size() - 1).getmItemId();
                mAddress = ApiConstant.refreshPictureApi(mId);
                judgeDataExistence(ADD_LOADING);
            }
        });
        judgeDataExistence(NORMAL_LOADING);
        return mView;
    }

    /**
     * 解析实例适配器
     *
     * @param list http请求后的json数据
     * @param mes  区别不同情况
     */
    private void realizeAdapter(List<String> list, int mes) {
        Message message = new Message();
        if (mes == NORMAL_LOADING) {
            for (String x : list) {
                mList.add(UsingJsonObject.getmUsingJsonObject().chaHuaDetailJson(x));
            }
            mAdapter = new PictureListAdapter(OneApplication.getmContext(), mListView, R.layout.chahualistview, mList);
            //异步消息处理，发送消息
            message.what = mes;
            mHandler.sendMessage(message);
        } else if (mes == REFRESH_LOADING) {
            mList = new ArrayList<>();
            for (String x : list) {
                mList.add(UsingJsonObject.getmUsingJsonObject().chaHuaDetailJson(x));
            }
            mAdapter = new PictureListAdapter(OneApplication.getmContext(), mListView, R.layout.chahualistview, mList);
            message.what = mes;
            mHandler.sendMessage(message);
        } else if (mes == ADD_LOADING) {
            for (String x : list) {
                mList.add(UsingJsonObject.getmUsingJsonObject().chaHuaDetailJson(x));
            }
            message.what = mes;
            mHandler.sendMessage(message);
        }
    }

    /**
     * 二次http请求和解析
     *
     * @param mes 区别不同情况
     */
    private void httpRequest(final int mes, final String address) {
        HttpUtil.sentHttpRequest(address, new HttpUtil.HttpCallbackListenner() {
            @Override
            public void onFinish(Object response) {
                List<String> list = UsingJsonObject.getmUsingJsonObject().chaHuaIdJson(response.toString());
                HttpUtil.sentReqChahua(list, true, new HttpUtil.HttpCallbackListenner() {
                    @Override
                    public void onFinish(Object response) {
                        List<String> listStr = (List<String>) response;
                        //储存入数据库
                        PutingData.putList(listStr, address);
                        realizeAdapter(listStr, mes);
                    }

                    @Override
                    public void onError(Exception e) {
                        e.printStackTrace();
                    }
                });
            }

            @Override
            public void onError(Exception e) {
                Message message = new Message();
                message.what = NONETWORK_REMIND;
                mHandler.sendMessage(message);
            }
        });
    }

    /**
     * 判断数据库是否存在未超时可用数据，有就使用，无则请求
     *
     * @param mes 区别不同情况
     */
    private void judgeDataExistence(final int mes) {
        if ((mJsonData = AddingAndQuerying.getmAddingAndQuerying().queryJson(mAddress)) == null) {
            httpRequest(mes, mAddress);
        } else {
            final List<String> list = ListTurning.strToList(mJsonData);
            new Thread(new Runnable() {
                @Override
                public void run() {
                    realizeAdapter(list, mes);
                }
            }).start();
        }
    }
}
