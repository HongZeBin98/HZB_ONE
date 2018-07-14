package com.hongzebin.bean;

/**
 * 插画详细
 * Created by 洪泽彬
 */

public class ChaHuaDetail {
    private String mItemId;
    private String mImgURL;
    private String mPictureText;
    private String mText;
    private String mTime;
    private String mTextAuthor;
    private String mLikeCount;

    public ChaHuaDetail(String mItemId, String mImgURL, String mPictureText, String mText,
                        String mTime, String mTextAuthor, String mLikeCount) {
        this.mItemId = mItemId;
        this.mImgURL = mImgURL;
        this.mPictureText = mPictureText;
        this.mText = mText;
        this.mTime = mTime;
        this.mTextAuthor = mTextAuthor;
        this.mLikeCount = mLikeCount;
    }

    public String getmItemId() {
        return mItemId;
    }

    public void setmItemId(String mItemId) {
        this.mItemId = mItemId;
    }

    public String getmImgURL() {
        return mImgURL;
    }

    public void setmImgURL(String mImgURL) {
        this.mImgURL = mImgURL;
    }

    public String getmPictureText() {
        return mPictureText;
    }

    public void setmPictureText(String mPictureText) {
        this.mPictureText = mPictureText;
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

    public String getmTextAuthor() {
        return mTextAuthor;
    }

    public void setmTextAuthor(String mTextAuthor) {
        this.mTextAuthor = mTextAuthor;
    }

    public String getmLikeCount() {
        return mLikeCount;
    }

    public void setmLikeCount(String mLikeCount) {
        this.mLikeCount = mLikeCount;
    }
}
