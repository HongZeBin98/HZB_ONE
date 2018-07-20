package com.hongzebin.model;

import com.hongzebin.bean.PictureDetail;

import java.util.List;

public interface PictureListCallback {

    void onFinish(List<PictureDetail> list);

    void onFail();
}
