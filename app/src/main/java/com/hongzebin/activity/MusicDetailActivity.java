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

import com.hongzebin.R;
import com.hongzebin.adapter.ComListAdapter;
import com.hongzebin.bean.Comment;
import com.hongzebin.bean.MusicDetail;
import com.hongzebin.db.AddingAndQuerying;
import com.hongzebin.ui.ListViewForScrollView;
import com.hongzebin.util.DownloadImage;
import com.hongzebin.util.HttpUtil;
import com.hongzebin.util.OneApplication;
import com.hongzebin.util.PutingData;
import com.hongzebin.util.UsingJsonObject;

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
    private String mJsonData;
    private String mDetailURL;
    private String mCommentURL;
    private Handler mHandler;

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
        mDetailURL = "http://v3.wufazhuce.com:8000/api/music/detail/" + intent.getStringExtra("id") + "?version=3.5.0&platform=android";
        mCommentURL = "http://v3.wufazhuce.com:8000/api/comment/praiseandtime/music/" + intent.getStringExtra("id") + "/0?platform=android";
        mHandler = new Handler() {
            public void handleMessage(Message msg) {
                switch (msg.what) {
                    case DETAIL:
                        putDataToUI();
                        break;
                    case COMMENT:
                        ListViewForScrollView listView = (ListViewForScrollView) findViewById(R.id.music_mylistview);
                        listView.setFocusable(false);
                        listView.setAdapter(mComAdapter);
                        break;
                    case NONETWORK_REMIND:
                        Toast.makeText(MusicDetailActivity.this, "请联网后重试", Toast.LENGTH_SHORT).show();
                        break;
                    default:
                        break;
                }
            }
        };
        //请求评论， 如果数据库存在未超时的有效数据则调用，否则http请求
        judgeDataExistence(COMMENT);
        //请求阅读详细， 如果数据库存在未超时的有效数据则调用，否则http请求
        judgeDataExistence(DETAIL);
    }

    /**
     * 开启详细活动，并传值
     *
     * @param context 上下文
     * @param str     传递的值
     */
    public static void startMusicDetail(Context context, String str) {
        Intent intent = new Intent(context, MusicDetailActivity.class);
        intent.putExtra("id", str);
        context.startActivity(intent);
    }

    /**
     * 不同情况实例不同适配器
     *
     * @param response 待解析的json数据
     * @param mes      区别不同情况
     */
    private void realizeAdapter(String response, int mes) {
        Message message = new Message();
        if (mes == DETAIL) {
            mMusicDetail = UsingJsonObject.getmUsingJsonObject().musicDetailJson((String) response);
            //异步消息处理，发送消息
            message.what = DETAIL;
            mHandler.sendMessage(message);
        } else {
            List<Comment> comList = UsingJsonObject.getmUsingJsonObject().commentJson((String) response);
            mComAdapter = new ComListAdapter(OneApplication.getmContext(), R.layout.commentlistview, comList);
            message.what = COMMENT;
            mHandler.sendMessage(message);
        }
    }

    /**
     * http请求得到的数据加载入数据库， 根据不同情况实例适配器
     *
     * @param mes 区别不同情况
     * @param address 请求的url
     */
    private void httpRequest(final int mes, final String address) {
        HttpUtil.sentHttpRequest(address, new HttpUtil.HttpCallbackListenner() {
            @Override
            public void onFinish(Object response) {
                PutingData.putStr(address, (String) response);    //加载进数据库
                realizeAdapter((String) response, mes); //实例适配器
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
        new DownloadImage(mImg).execute(mMusicDetail.getmCover());
        mTitle.setText(mMusicDetail.getmTitle());
        mSummary.setText(mMusicDetail.getmSummary());
        mName.setText(mMusicDetail.getmMusicName());
        mInfo.setText(mMusicDetail.getmInfo());
        mLyric.setText(mMusicDetail.getmLyric());
        mConNum.setText(mMusicDetail.getmCommentNum());
        mAuthor.setText("文/" + mMusicDetail.getmAuthor());
        mLikeNum.setText(mMusicDetail.getmLikeNum());
        mStory.setText(Html.fromHtml(mMusicDetail.getmStory()));
    }

    /**
     * 判断数据库是否存在未超时可用数据，有就使用，无则请求
     *
     * @param flag 区别不同情况
     */
    private void judgeDataExistence(final int flag) {
        String url;
        if (flag == DETAIL) {
            url = mDetailURL;
        } else {
            url = mCommentURL;
        }
        if ((mJsonData = AddingAndQuerying.getmAddingAndQuerying().queryJson(url)) == null) {
            httpRequest(flag, url);
        } else {
            new Thread(new Runnable() {
                @Override
                public void run() {
                    realizeAdapter(mJsonData, flag);
                }
            }).start();
        }
    }
}
