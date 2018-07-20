package com.hongzebin.fragment;

import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.os.Handler;
import android.os.IBinder;
import android.os.Message;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.RequestQueue;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.Volley;
import com.hongzebin.R;
import com.hongzebin.activity.TypeActivity;
import com.hongzebin.adapter.SowingMapAdapter;
import com.hongzebin.bean.PictureDetail;
import com.hongzebin.model.AllCallback;
import com.hongzebin.model.AllModel;
import com.hongzebin.service.DownloadService;
import com.hongzebin.util.DownloadImage;
import com.hongzebin.util.OneApplication;
import com.hongzebin.util.HttpUtil;
import com.hongzebin.util.UsingGson;

import java.util.ArrayList;
import java.util.List;

import static com.hongzebin.util.ApiConstant.PICTURE_ADDRESS;
import static com.hongzebin.util.Constant.NORMAL_LOADING;

/**all界面，上面有不同类型按钮和一个轮播图
 * Created by 洪泽彬
 */

public class AllFragment extends Fragment implements View.OnClickListener {

    private ViewPager mViewPager;
    private RequestQueue mQueue;
    private Button mReadBtn;
    private Button mPictureBtn;
    private Button mMusicBtn;
    private Button mVideoBtn;
    private TextView mDownload;
    private SowingMapAdapter mAdapter;
    private View mView;
    private List<ImageView> mImageList;
    private Handler mHandler;
    private Context context = OneApplication.getmContext();

    private DownloadService.DownloadBinder downloadBinder;

    private ServiceConnection connection = new ServiceConnection() {
        @Override
        public void onServiceConnected(ComponentName componentName, IBinder iBinder) {
            downloadBinder = (DownloadService.DownloadBinder) iBinder;
        }

        @Override
        public void onServiceDisconnected(ComponentName componentName) {
        }
    };

    @Override
    public void onDestroy() {
        super.onDestroy();
        context.unbindService(connection);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        mQueue = Volley.newRequestQueue(OneApplication.getmContext());
        mImageList = new ArrayList<>();
        mView = inflater.inflate(R.layout.all, container, false);
        Intent intent = new Intent(context,  DownloadService.class);
        context.startService(intent);
        context.bindService(intent, connection, Context.BIND_AUTO_CREATE);
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
        httpRequest(PICTURE_ADDRESS);
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
                startActivity(intent);
                break;
            case R.id.music:
                intent.putExtra("number", 2);
                startActivity(intent);
                break;
            case R.id.video:
                intent.putExtra("number", 3);
                startActivity(intent);
                break;
            case R.id.tuwen:
                intent.putExtra("number", 0);
                startActivity(intent);
                break;
            case R.id.download:
                downloadBinder.startDownload();
                break;
            default:
                break;
        }

    }

    /**
     * 实例轮播图适配器
     *
     * @param list 图片的url容器
     */
    private void realizeAdapter(final List<String> list) {
        new Thread(){
            @Override
            public void run(){
                ImageView iv;
                for (String x : list) {
                    Drawable drawable = new DownloadImage().loadImageFromNetwork(x);
                    iv = new ImageView(OneApplication.getmContext());
                    iv.setImageDrawable(drawable);
                    iv.setScaleType(ImageView.ScaleType.FIT_XY);    //使图片铺满
                    mImageList.add(iv);
                }
                //配置轮播图适配器
                mAdapter = new SowingMapAdapter(mImageList);
                //发送消息
                Message message = new Message();
                message.what = NORMAL_LOADING;
                mHandler.sendMessage(message);
            }
        }.start();
    }

    /**
     * 二次http请求解析获取数据
     *
     * @param address 请求的URL
     */
    private void httpRequest(final String address) {
        AllModel.getDataFromNetwork(address, mQueue, new AllCallback() {
            @Override
            public void onFinish(List<String> list) {
                realizeAdapter(list);
            }

            @Override
            public void onFail() {
                Toast.makeText(getActivity(), "请联网后重试", Toast.LENGTH_SHORT).show();
            }
        });
    }

    /**
     * 控件实例化，传入数据
     */
    private void initUI() {
        mReadBtn = (Button) mView.findViewById(R.id.read);
        mPictureBtn = (Button) mView.findViewById(R.id.music);
        mVideoBtn = (Button) mView.findViewById(R.id.video);
        mMusicBtn = (Button) mView.findViewById(R.id.tuwen);
        mDownload = (TextView) mView.findViewById(R.id.download);

        mReadBtn.setOnClickListener(this);
        mPictureBtn.setOnClickListener(this);
        mMusicBtn.setOnClickListener(this);
        mVideoBtn.setOnClickListener(this);
        mDownload.setOnClickListener(this);
    }
}
