package com.hongzebin.bean;

/**
 * 类型列表
 * Created by 洪泽彬
 */

public class TypeOutline {
    private String mId;
    private String mItemId;
    private String mTitle;
    private String mForward;
    private String mImgURL;
    private String mLikeCount;
    private String mDate;
    private String mAuthor;

    public TypeOutline(String mId, String mAuthor, String mItemId, String mTitle, String forward,
                       String mImgURL, String mLikeCount, String mDate) {
        this.mId = mId;
        this.mAuthor = mAuthor;
        this.mItemId = mItemId;
        this.mTitle = mTitle;
        this.mForward = forward;
        this.mImgURL = mImgURL;
        this.mLikeCount = mLikeCount;
        this.mDate = mDate;
    }

    public String getmId() {
        return mId;
    }

    public void setmId(String mId) {
        this.mId = mId;
    }

    public String getmForward() {
        return mForward;
    }

    public void setmForward(String mForward) {
        this.mForward = mForward;
    }

    public String getmItemId() {
        return mItemId;
    }

    public void setmItemId(String mItemId) {
        this.mItemId = mItemId;
    }

    public String getmTitle() {
        return mTitle;
    }

    public void setmTitle(String mTitle) {
        this.mTitle = mTitle;
    }

    public String getmImgURL() {
        return mImgURL;
    }

    public void setmImgURL(String mImgURL) {
        this.mImgURL = mImgURL;
    }

    public String getmLikeCount() {
        return mLikeCount;
    }

    public void setmLikeCount(String mLikeCount) {
        this.mLikeCount = mLikeCount;
    }

    public String getmDate() {
        return mDate;
    }

    public void setmDate(String mDate) {
        this.mDate = mDate;
    }

    public String getmAuthor() {
        return mAuthor;
    }

    public void setmAuthor(String mAuthor) {
        this.mAuthor = mAuthor;
    }

}
