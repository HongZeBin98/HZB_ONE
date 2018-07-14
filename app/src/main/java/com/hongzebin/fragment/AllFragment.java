package com.hongzebin.fragment;

import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;

import com.hongzebin.R;
import com.hongzebin.activity.TypeActivity;
import com.hongzebin.adapter.LunBoAdapter;
import com.hongzebin.util.DownloadImage;
import com.hongzebin.util.OneApplication;
import com.hongzebin.util.HttpUtil;
import com.hongzebin.util.UsingJsonObject;

import java.util.ArrayList;
import java.util.List;

import static com.hongzebin.util.ApiConstant.CHAHUA_ADDRESS;
import static com.hongzebin.util.Constant.NORMAL_LOADING;

/**all界面，上面有不同类型按钮和一个轮播图
 * Created by 洪泽彬
 */

public class AllFragment extends Fragment implements View.OnClickListener {

    private ViewPager mViewPager;
    private Button mReadBtn;
    private Button mTuwenBtn;
    private Button mMusicBtn;
    private Button mVedioBtn;
    private LunBoAdapter mAdapter;
    private View mView;
    private List<ImageView> mImageList;
    private Handler mHandler;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        mImageList = new ArrayList<>();
        mView = inflater.inflate(R.layout.all, container, false);
        //异步消息处理，接受消息后进行UI的改变
        mHandler = new Handler() {
            public void handleMessage(Message msg) {
                switch (msg.what) {
                    case NORMAL_LOADING:
                        mViewPager = (ViewPager) mView.findViewById(R.id.viewpager);
                        mViewPager.setAdapter(mAdapter);
                        break;
                    default:
                        break;
                }
            }
        };
        httpRequest(CHAHUA_ADDRESS);
        initUI();
        return mView;
    }

    //按钮点击事件
    @Override
    public void onClick(View v) {
        Intent intent = new Intent(OneApplication.getmContext(), TypeActivity.class);
        switch (v.getId()) {
            //通过数字确定viewpager打开哪一页
            case R.id.read:
                intent.putExtra("number", 1);
                break;
            case R.id.music:
                intent.putExtra("number", 2);
                break;
            case R.id.video:
                intent.putExtra("number", 3);
                break;
            case R.id.tuwen:
                intent.putExtra("number", 0);
                break;
            default:
                break;
        }
        startActivity(intent);
    }

    /**
     * 实例轮播图适配器
     *
     * @param list 图片的url容器
     */
    private void realizeAdapter(List<String> list) {
        ImageView iv;
        for (String x : list) {
            Drawable drawable = new DownloadImage().loadImageFromNetwork(x);
            iv = new ImageView(OneApplication.getmContext());
            iv.setImageDrawable(drawable);
            iv.setScaleType(ImageView.ScaleType.FIT_XY);    //使图片铺满
            mImageList.add(iv);
        }
        //配置轮播图适配器
        mAdapter = new LunBoAdapter(mImageList);
        //发送消息
        Message message = new Message();
        message.what = NORMAL_LOADING;
        mHandler.sendMessage(message);
    }

    /**
     * 二次http请求解析获取数据
     *
     * @param address 请求的URL
     */
    private void httpRequest(final String address) {
        HttpUtil.sentHttpRequest(address, new HttpUtil.HttpCallbackListenner() {
            @Override
            public void onFinish(Object response) {
                List<String> list = UsingJsonObject.getmUsingJsonObject().chaHuaIdJson(response.toString());
                HttpUtil.sentReqChahua(list, false, new HttpUtil.HttpCallbackListenner() {
                    @Override
                    public void onFinish(Object response) {
                        List<String> listStr = new ArrayList<>();
                        for (String x : (List<String>) response) {
                            listStr.add(UsingJsonObject.getmUsingJsonObject().chaHuaURLJson(x));
                        }
                        realizeAdapter(listStr);
                    }
                    @Override
                    public void onError(Exception e) {
                        Log.d("ChaHuaFragment", "--------------Error: ");
                        e.printStackTrace();
                    }
                });
            }
            @Override
            public void onError(Exception e) {
                Log.d("ChaHuaFragment", "--------------Error: ");
                e.printStackTrace();
            }
        });
    }

    /**
     * 控件实例化，传入数据
     */
    private void initUI() {
        mReadBtn = (Button) mView.findViewById(R.id.read);
        mTuwenBtn = (Button) mView.findViewById(R.id.music);
        mVedioBtn = (Button) mView.findViewById(R.id.video);
        mMusicBtn = (Button) mView.findViewById(R.id.tuwen);

        mReadBtn.setOnClickListener(this);
        mTuwenBtn.setOnClickListener(this);
        mMusicBtn.setOnClickListener(this);
        mVedioBtn.setOnClickListener(this);
    }
}
