package com.hongzebin.activity;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.text.Html;
import android.webkit.WebView;
import android.widget.TextView;
import android.widget.Toast;

import com.hongzebin.R;
import com.hongzebin.adapter.ComListAdapter;
import com.hongzebin.bean.Comment;
import com.hongzebin.bean.VideoDetail;
import com.hongzebin.model.VideoDetailCallback;
import com.hongzebin.model.VideoDetailModel;
import com.hongzebin.ui.ListViewForScrollView;
import com.hongzebin.util.ApiConstant;
import com.hongzebin.util.OneApplication;

import java.util.List;

import static com.hongzebin.util.Constant.COMMENT;
import static com.hongzebin.util.Constant.DETAIL;

/**
 * 影视详细
 * Created by 洪泽彬
 */

public class VideoDetailActivity extends Activity {
    private ComListAdapter mComAdapter = null;
    private VideoDetail mVideoDetail;
    private String mDetailURL;
    private String mCommentURL;

    private TextView mTitle;
    private TextView mAuthor;
    private TextView mSummary;
    private WebView mWebView;
    private TextView mLikeNum;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.videodetail);
        initUI();
        Intent intent = getIntent();
        String itemId = intent.getStringExtra("itemId");
        mDetailURL = ApiConstant.getVideoAddress(itemId);
        mCommentURL = ApiConstant.getVideoComAddress(itemId);
        //请求评论， 如果数据库存在未超时的有效数据则调用，否则http请求
        getData(COMMENT, "LIST");
        //请求阅读详细， 如果数据库存在未超时的有效数据则调用，否则http请求
        getData(DETAIL, "VIDEO");
    }

    /**
     * 开启详细活动，并传值
     *
     * @param context 上下文
     * @param str     传递的值
     */
    public static void startVideoDetail(Context context, String str) {
        Intent intent = new Intent(context, VideoDetailActivity.class);
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
            mVideoDetail = (VideoDetail) response;
            putDataToUI();
        } else {
            mComAdapter = new ComListAdapter(OneApplication.getmContext(), R.layout.commentlistview, (List<Comment>) response);
            ListViewForScrollView listView = (ListViewForScrollView) findViewById(R.id.video_mylistview);
            listView.setFocusable(false);
            listView.setAdapter(mComAdapter);
        }
    }

    /**
     * http请求得到的数据加载入数据库， 根据不同情况实例适配器
     *
     * @param mes     区别不同情况
     * @param address 请求的url
     */
    private void httpRequest(final int mes, final String address) {
        VideoDetailModel.getDataFromNetwork(mes, address, new VideoDetailCallback() {
            @Override
            public void onFinish(Object object) {
                realizeAdapter(object, mes);
            }

            @Override
            public void onFail() {
                Toast.makeText(VideoDetailActivity.this, "请联网后重试", Toast.LENGTH_SHORT).show();
            }
        });
    }

    /**
     * 实例UI控件
     */
    private void initUI() {
        mTitle = (TextView) findViewById(R.id.video_title);
        mAuthor = (TextView) findViewById(R.id.video_author);
        mSummary = (TextView) findViewById(R.id.video_summary);
        mWebView = (WebView) findViewById(R.id.video_text);
        mLikeNum = (TextView) findViewById(R.id.video_likenum);
    }

    /**
     * 给UI控件传值
     */
    private void putDataToUI() {
        mTitle.setText(mVideoDetail.getTitle());
        mAuthor.setText("文/" + mVideoDetail.getUser().getUser_name());
        mLikeNum.setText(mVideoDetail.getPraisenum());
        mSummary.setText(mVideoDetail.getSummary());
        mWebView.loadDataWithBaseURL(null, mVideoDetail.getContent(), "text/html" , "utf-8", null);
    }

    /**
     * 判断数据库是否存在未超时可用数据，有就使用，无则请求
     *
     * @param flag      区别不同情况
     * @param tableName 数据库表名
     */
    private void getData(final int flag, String tableName) {
        VideoDetailModel.getDataFromBD(flag, mDetailURL, mCommentURL, tableName, new VideoDetailCallback() {
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
