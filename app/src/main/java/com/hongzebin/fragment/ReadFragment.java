package com.hongzebin.fragment;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;

import com.hongzebin.R;
import com.hongzebin.activity.ReadDetailActivity;
import com.hongzebin.adapter.TypeListAdapter;
import com.hongzebin.bean.TypeOutline;
import com.hongzebin.model.TypeOutlineCallback;
import com.hongzebin.model.TypeOutlineModel;
import com.hongzebin.util.ApiConstant;
import com.hongzebin.util.OneApplication;

import java.util.ArrayList;
import java.util.List;

import static com.hongzebin.util.ApiConstant.READ_ADDRESS;
import static com.hongzebin.util.Constant.ADD_LOADING;
import static com.hongzebin.util.Constant.NORMAL_LOADING;
import static com.hongzebin.util.Constant.REFRESH_LOADING;

/**
 * 阅读列表界面
 * Created by 洪泽彬
 */
public class ReadFragment extends Fragment {

    private List<TypeOutline> mList;
    private TypeListAdapter mAdapter;
    private SwipeRefreshLayout mRefresh;
    private ListView mListView;
    private int mCount = 0;     //设置加载更多后，初始显示的条的位置
    private View mView;
    private View mFooterView;   //加载更多
    private String mId;    //请求http的URL的指定
    private String mAddress;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        mId = "0";
        mAddress = ApiConstant.refreshReadApi(mId);
        mList = new ArrayList<>();
        mView = inflater.inflate(R.layout.type, container, false);
        mFooterView = inflater.inflate(R.layout.loadingmore, null);
        mRefresh = (SwipeRefreshLayout) mView.findViewById(R.id.refresh);
        mListView = (ListView) mView.findViewById(R.id.type_listview);
        Button btn = (Button) mFooterView.findViewById(R.id.loading_btn);
        mListView.addFooterView(mFooterView);   //listview最后一条为加载更多

        //刷新监听
        mRefresh.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                httpRequest(REFRESH_LOADING, READ_ADDRESS);
            }
        });
        //加载更多按钮监听
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mCount = mAdapter.getCount();
                mId = mList.get(mList.size() - 1).getId();
                mAddress = ApiConstant.refreshReadApi(mId);
                getData(ADD_LOADING, "LIST");
            }
        });
        getData(NORMAL_LOADING, "LIST");
        //打开活动，并传值
        mListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                TypeOutline to = mList.get(position);
                ReadDetailActivity.startReadDetail(getActivity(), to.getItem_id());
            }
        });
        return mView;
    }

    /**
     * 解析实例适配器
     *
     * @param list 获取到的数据的列表
     * @param mes      区别不同情况
     */
    private void realizeAdapter(List<TypeOutline> list, int mes) {
        if (mes == NORMAL_LOADING) {
            mList.addAll(list);
            mAdapter = new TypeListAdapter(OneApplication.getmContext(), mListView, R.layout.typelistview, mList);
            mListView.setAdapter(mAdapter);
        } else if (mes == REFRESH_LOADING) {
            mList = new ArrayList<>();
            mList.addAll(list);
            mAdapter = new TypeListAdapter(OneApplication.getmContext(), mListView, R.layout.typelistview, mList);
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
     * http请求后得到数据放入数据库，并解析实例适配器
     *
     * @param mes     区别不同情况
     * @param address 请求的URL
     */
    private void httpRequest(final int mes, String address) {
        TypeOutlineModel.getDataFromNetwork(address, new TypeOutlineCallback() {
            @Override
            public void onFinish(List<TypeOutline> list) {
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
        TypeOutlineModel.getDataFromBD(mAddress, tableName, new TypeOutlineCallback() {
            @Override
            public void onFinish(List<TypeOutline> list) {
                realizeAdapter(list, mes);
            }
            @Override
            public void onFail() {
                httpRequest(mes, mAddress);
            }
        });
    }
}
