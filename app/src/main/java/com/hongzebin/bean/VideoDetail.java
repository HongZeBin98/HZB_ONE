package com.hongzebin.bean;

/**
 * 影视列表
 * Created by 洪泽彬
 */

public class VideoDetail {
    private String mTitle;
    private String mUser;
    private String mSummary;
    private String mText;
    private String mLikeNum;

    public VideoDetail(String mTitle, String mUser, String mSummary, String mText, String mLikeNum) {
        this.mTitle = mTitle;
        this.mUser = mUser;
        this.mSummary = mSummary;
        this.mText = mText;
        this.mLikeNum = mLikeNum;
    }

    public String getmLikeNum() {
        return mLikeNum;
    }

    public void setmLikeNum(String mLikeNum) {
        this.mLikeNum = mLikeNum;
    }

    public String getmTitle() {
        return mTitle;
    }

    public void setmTitle(String mTitle) {
        this.mTitle = mTitle;
    }

    public String getmUser() {
        return mUser;
    }

    public void setmUser(String mUser) {
        this.mUser = mUser;
    }

    public String getmSummary() {
        return mSummary;
    }

    public void setmSummary(String mSummary) {
        this.mSummary = mSummary;
    }

    public String getmText() {
        return mText;
    }

    public void setmText(String mText) {
        this.mText = mText;
    }
}
