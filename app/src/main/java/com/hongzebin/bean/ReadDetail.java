package com.hongzebin.bean;

/**阅读详细
 * Created by 洪泽彬
 */

public class ReadDetail {
    private String mTitle;
    private String mAuthor;
    private String mContentHtml;
    private String mAuthorDesc;
    private String mLikeNum;
    private String mCommentNum;

    public ReadDetail(String mTitle, String mAuthor, String mContentHtml, String mAuthorDesc,
                      String mLikeNum, String mCommentNum) {
        this.mTitle = mTitle;
        this.mAuthor = mAuthor;
        this.mContentHtml = mContentHtml;
        this.mAuthorDesc = mAuthorDesc;
        this.mLikeNum = mLikeNum;
        this.mCommentNum = mCommentNum;
    }

    public String getmTitle() {
        return mTitle;
    }

    public void setmTitle(String mTitle) {
        this.mTitle = mTitle;
    }

    public String getmAuthor() {
        return mAuthor;
    }

    public void setmAuthor(String mAuthor) {
        this.mAuthor = mAuthor;
    }

    public String getmContentHtml() {
        return mContentHtml;
    }

    public void setmContentHtml(String mContentHtml) {
        this.mContentHtml = mContentHtml;
    }

    public String getmAuthorDesc() {
        return mAuthorDesc;
    }

    public void setmAuthorDesc(String mAuthorDesc) {
        this.mAuthorDesc = mAuthorDesc;
    }

    public String getmLikeNum() {
        return mLikeNum;
    }

    public void setmLikeNum(String mLikeNum) {
        this.mLikeNum = mLikeNum;
    }

    public String getmCommentNum() {
        return mCommentNum;
    }

    public void setmCommentNum(String mCommentNum) {
        this.mCommentNum = mCommentNum;
    }
}
