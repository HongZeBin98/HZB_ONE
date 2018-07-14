package com.hongzebin.bean;

/**
 * 评论
 * Created by 洪泽彬
 */

public class Comment {
    private String mUser;
    private String mText;
    private String mTime;
    private String mLikeNum;

    public Comment(String mUser, String mText, String mTime,
                   String mLikeNum) {
        this.mUser = mUser;
        this.mText = mText;
        this.mTime = mTime;
        this.mLikeNum = mLikeNum;
    }

    public String getmUser() {
        return mUser;
    }

    public void setmUser(String mUser) {
        this.mUser = mUser;
    }

    public String getmText() {
        return mText;
    }

    public void setmText(String mText) {
        this.mText = mText;
    }

    public String getmTime() {
        return mTime;
    }

    public void setmTime(String mTime) {
        this.mTime = mTime;
    }

    public String getmLikeNum() {
        return mLikeNum;
    }

    public void setmLikeNum(String mLikeNum) {
        this.mLikeNum = mLikeNum;
    }

}
