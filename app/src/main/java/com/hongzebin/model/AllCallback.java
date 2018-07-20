package com.hongzebin.model;

import com.hongzebin.bean.PictureDetail;

import java.util.List;

public interface AllCallback {

    void onFinish(List<String> list);

    void onFail();
}
