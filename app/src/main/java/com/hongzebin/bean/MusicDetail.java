package com.hongzebin.bean;

/**
 * 音乐详细
 * Created by 洪泽彬
 */

public class MusicDetail {
    private String mMusicName;
    private String mCover;
    private String mTitle;
    private String mSummary;
    private String mStory;
    private String mLyric;
    private String mInfo;
    private String mLikeNum;
    private String mCommentNum;
    private String mAuthor;

    public MusicDetail(String mAuthor, String mMusicName, String mCover, String mTitle, String mSummary,
                       String mStory, String mLyric, String mInfo, String mLikeNum, String mCommentNum) {
        this.mAuthor = mAuthor;
        this.mMusicName = mMusicName;
        this.mCover = mCover;
        this.mTitle = mTitle;
        this.mSummary = mSummary;
        this.mStory = mStory;
        this.mLyric = mLyric;
        this.mInfo = mInfo;
        this.mLikeNum = mLikeNum;
        this.mCommentNum = mCommentNum;
    }

    public String getmAuthor() {
        return mAuthor;
    }

    public void setmAuthor(String mAuthor) {
        this.mAuthor = mAuthor;
    }

    public String getmMusicName() {
        return mMusicName;
    }

    public void setmMusicName(String mMusicName) {
        this.mMusicName = mMusicName;
    }

    public String getmCover() {
        return mCover;
    }

    public void setmCover(String mCover) {
        this.mCover = mCover;
    }

    public String getmTitle() {
        return mTitle;
    }

    public void setmTitle(String mTitle) {
        this.mTitle = mTitle;
    }

    public String getmSummary() {
        return mSummary;
    }

    public void setmSummary(String mSummary) {
        this.mSummary = mSummary;
    }

    public String getmStory() {
        return mStory;
    }

    public void setmStory(String mStory) {
        this.mStory = mStory;
    }

    public String getmLyric() {
        return mLyric;
    }

    public void setmLyric(String mLyric) {
        this.mLyric = mLyric;
    }

    public String getmInfo() {
        return mInfo;
    }

    public void setmInfo(String mInfo) {
        this.mInfo = mInfo;
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
