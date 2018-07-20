package com.hongzebin.activity;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.text.Html;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.VolleyError;
import com.hongzebin.R;
import com.hongzebin.adapter.ComListAdapter;
import com.hongzebin.bean.Comment;
import com.hongzebin.bean.MusicDetail;
import com.hongzebin.db.AddingAndQuerying;
import com.hongzebin.model.MusicDetailCallback;
import com.hongzebin.model.MusicDetailModel;
import com.hongzebin.ui.ListViewForScrollView;
import com.hongzebin.util.ApiConstant;
import com.hongzebin.util.DownloadImage;
import com.hongzebin.util.HttpUtil;
import com.hongzebin.util.OneApplication;
import com.hongzebin.util.PutingData;
import com.hongzebin.util.UsingGson;

import java.util.List;

import static com.hongzebin.util.Constant.COMMENT;
import static com.hongzebin.util.Constant.DETAIL;
import static com.hongzebin.util.Constant.NONETWORK_REMIND;


/**
 * 音乐详细界面
 * Created by 洪泽彬
 */

public class MusicDetailActivity extends Activity {
    private ComListAdapter mComAdapter = null;
    private MusicDetail mMusicDetail;
    private String mDetailURL;
    private String mCommentURL;

    private ImageView mImg;
    private TextView mTitle;
    private TextView mAuthor;
    private TextView mSummary;
    private TextView mStory;
    private TextView mName;
    private TextView mInfo;
    private TextView mLyric;
    private TextView mLikeNum;
    private TextView mConNum;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.musicdetail);
        initUI();   //实例化
        Intent intent = getIntent();
        String itemId = intent.getStringExtra("itemId");
        mDetailURL = ApiConstant.getMusicAddress(itemId);
        mCommentURL = ApiConstant.getMusicComAddress(itemId);
        //请求评论， 如果数据库存在未超时的有效数据则调用，否则http请求
        getData(COMMENT, "LIST");
        //请求音乐详细， 如果数据库存在未超时的有效数据则调用，否则http请求
        getData(DETAIL, "MUSIC");
    }

    /**
     * 开启详细活动，并传值
     *
     * @param context 上下文
     * @param str     传递的值
     */
    public static void startMusicDetail(Context context, String str) {
        Intent intent = new Intent(context, MusicDetailActivity.class);
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
            mMusicDetail = (MusicDetail) response;
            putDataToUI();
        } else {
            mComAdapter = new ComListAdapter(OneApplication.getmContext(), R.layout.commentlistview, (List<Comment>) response);
            ListViewForScrollView listView = (ListViewForScrollView) findViewById(R.id.music_mylistview);
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
        MusicDetailModel.getDataFromNetwork(mes, address, new MusicDetailCallback() {
            @Override
            public void onFinish(Object object) {
                realizeAdapter(object, mes);
            }

            @Override
            public void onFail() {
                Toast.makeText(MusicDetailActivity.this, "请联网后重试", Toast.LENGTH_SHORT).show();
            }
        });
    }

    /**
     * 实例UI控件
     */
    private void initUI() {
        mImg = (ImageView) findViewById(R.id.music_img);
        mTitle = (TextView) findViewById(R.id.music_title);
        mAuthor = (TextView) findViewById(R.id.music_author);
        mSummary = (TextView) findViewById(R.id.music_summary);
        mStory = (TextView) findViewById(R.id.music_story);
        mName = (TextView) findViewById(R.id.music_name);
        mInfo = (TextView) findViewById(R.id.music_info);
        mLyric = (TextView) findViewById(R.id.music_lyric);
        mLikeNum = (TextView) findViewById(R.id.music_likenum);
        mConNum = (TextView) findViewById(R.id.music_commentnum);
    }

    /**
     * 给UI控件传值
     */
    private void putDataToUI() {
        new DownloadImage(mImg).execute(mMusicDetail.getCover());
        mTitle.setText(mMusicDetail.getStory_title());

        mSummary.setText(mMusicDetail.getStory_summary());
        mName.setText(mMusicDetail.getTitle());
        mInfo.setText(mMusicDetail.getInfo());
        mLyric.setText(mMusicDetail.getLyric());
        mConNum.setText(mMusicDetail.getCommentnum());
        mAuthor.setText("文/" + mMusicDetail.getAuthor().getUser_name());
        mLikeNum.setText(mMusicDetail.getPraisenum());
        mStory.setText(Html.fromHtml(mMusicDetail.getStory()));
    }

    /**
     * 判断数据库是否存在未超时可用数据，有就使用，无则请求
     *
     * @param flag      区别不同情况
     * @param tableName 数据库表名
     */
    private void getData(final int flag, String tableName) {
        MusicDetailModel.getDataFromBD(flag, mDetailURL, mCommentURL, tableName, new MusicDetailCallback() {
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
