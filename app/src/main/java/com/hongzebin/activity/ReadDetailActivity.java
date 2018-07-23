package com.hongzebin.activity;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.RequiresPermission;
import android.text.Html;
import android.util.Log;
import android.webkit.WebView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.VolleyError;
import com.hongzebin.R;
import com.hongzebin.adapter.ComListAdapter;
import com.hongzebin.bean.Comment;
import com.hongzebin.bean.ReadDetail;
import com.hongzebin.db.AddingAndQuerying;
import com.hongzebin.model.ReadDetailCallback;
import com.hongzebin.model.ReadDetailModel;
import com.hongzebin.ui.ListViewForScrollView;
import com.hongzebin.util.ApiConstant;
import com.hongzebin.util.GlobalTools;
import com.hongzebin.util.HttpUtil;
import com.hongzebin.util.OneApplication;
import com.hongzebin.util.PutingData;
import com.hongzebin.util.UsingGson;

import java.util.List;

import static com.hongzebin.util.Constant.COMMENT;
import static com.hongzebin.util.Constant.DETAIL;
import static com.hongzebin.util.Constant.NONETWORK_REMIND;

/**
 * 阅读详细界面
 * Created by 洪泽彬
 */

public class ReadDetailActivity extends Activity {
    private ComListAdapter mComAdapter = null;
    private ReadDetail mReadDetail;
    private String mDetailURL;
    private String mCommentURL;

    private TextView mTitle;
    private WebView mWebView;
    private TextView mAuthor;
    private TextView mAuthorDesc;
    private TextView mLikeNum;
    private TextView mComNum;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.readdetail);
        initUI();
        Intent intent = getIntent();
        String itemId = intent.getStringExtra("itemId");
        mDetailURL = ApiConstant.getReadAddress(itemId);
        mCommentURL = ApiConstant.getReadComAddress(itemId);
        //请求评论， 如果数据库存在未超时的有效数据则调用，否则http请求
        getData(COMMENT, "LIST");
        //请求阅读详细， 如果数据库存在未超时的有效数据则调用，否则http请求
        getData(DETAIL, "READ");
    }

    /**
     * 开启详细活动，并传值
     *
     * @param context 上下文
     * @param str     传递的值
     */
    public static void startReadDetail(Context context, String str) {
        Intent intent = new Intent(context, ReadDetailActivity.class);
        intent.putExtra("itemId", str);
        context.startActivity(intent);
    }

    /**
     * 不同情况实例不同适配器
     *
     * @param response 待解析的json数据
     * @param mes      区别不同情况
     */
    private void realizeAdapter(Object response, int mes) {
        if (mes == DETAIL) {
            mReadDetail = (ReadDetail) response;
            putDataToUI();
        } else {
            mComAdapter = new ComListAdapter(OneApplication.getmContext(), R.layout.commentlistview, (List<Comment>)response);
            ListViewForScrollView listView = (ListViewForScrollView) findViewById(R.id.detail_mylistview);
            listView.setFocusable(false);
            listView.setAdapter(mComAdapter);
        }
    }

    /**
     * http请求得到的数据加载入数据库， 根据不同情况实例适配器
     *
     * @param mes 区别不同情况
     * @param address 请求的url
     */
    private void httpRequest(final int mes, final String address) {
        ReadDetailModel.getDataFromNetwork(mes, address, new ReadDetailCallback() {
            @Override
            public void onFinish(Object object) {
                realizeAdapter(object, mes);
            }

            @Override
            public void onFail() {
                Toast.makeText(ReadDetailActivity.this, "请联网后重试", Toast.LENGTH_SHORT).show();
            }
        });
    }

    /**
     * 实例UI控件
     */
    public void initUI() {
        mTitle = (TextView) findViewById(R.id.detail_title);
        mWebView = (WebView) findViewById(R.id.detail_text);
        mAuthor = (TextView) findViewById(R.id.detail_author);
        mAuthorDesc = (TextView) findViewById(R.id.detail_authordesc);
        mLikeNum = (TextView) findViewById(R.id.detial_likenum);
        mComNum = (TextView) findViewById(R.id.detial_commentnum);
    }

    /**
     * 给UI控件传值
     */
    public void putDataToUI() {
        mTitle.setText(mReadDetail.getHp_title());
        String author = "文/" + mReadDetail.getAuthor().get(0).getUser_name();
        mAuthor.setText(author);
        mAuthorDesc.setText(mReadDetail.getAuthor().get(0).getDesc());
        mLikeNum.setText(mReadDetail.getPraisenum());
        mComNum.setText(mReadDetail.getCommentnum());
        mWebView.loadDataWithBaseURL(null, mReadDetail.getHp_content(), "text/html" , "utf-8", null);
    }

    /**
     * 判断数据库是否存在未超时可用数据，有就使用，无则请求
     *
     * @param flag 区别不同情况
     * @param tableName 数据库表名
     */
    private void getData(final int flag, String tableName) {
        ReadDetailModel.getDataFromBD(flag, mDetailURL, mCommentURL, tableName, new ReadDetailCallback() {
            @Override
            public void onFinish(Object object) {
                realizeAdapter(object, flag);
            }

            @Override
            public void onFail() {
                if (flag == DETAIL) {
                    httpRequest(flag, mDetailURL);
                } else {
                    httpRequest(flag, mCommentURL);
                }
            }
        });
    }
}
