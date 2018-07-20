package com.hongzebin.model;

import com.hongzebin.bean.TypeOutline;

import java.util.List;

public interface TypeOutlineCallback {

    void onFinish(List<TypeOutline> list);

    void onFail();
}
