package com.hongzebin.bean;

/**
 * 音乐详细
 * Created by 洪泽彬
 */

public class MusicDetail {

    private String title;
    private String cover;
    private String story_title;
    private String story_summary;
    private String story;
    private String lyric;
    private String info;
    private String praisenum;
    private String commentnum;
    private Author author;

    public MusicDetail(String title, String cover, String story_title, String story_summary, String story,
                       String lyric, String info, String praisenum, String commentnum, Author author) {
        this.title = title;
        this.cover = cover;
        this.story_title = story_title;
        this.story_summary = story_summary;
        this.story = story;
        this.lyric = lyric;
        this.info = info;
        this.praisenum = praisenum;
        this.commentnum = commentnum;
        this.author = author;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getCover() {
        return cover;
    }

    public void setCover(String cover) {
        this.cover = cover;
    }

    public String getStory_title() {
        return story_title;
    }

    public void setStory_title(String story_title) {
        this.story_title = story_title;
    }

    public String getStory_summary() {
        return story_summary;
    }

    public void setStory_summary(String story_summary) {
        this.story_summary = story_summary;
    }

    public String getStory() {
        return story;
    }

    public void setStory(String story) {
        this.story = story;
    }

    public String getLyric() {
        return lyric;
    }

    public void setLyric(String lyric) {
        this.lyric = lyric;
    }

    public String getInfo() {
        return info;
    }

    public void setInfo(String info) {
        this.info = info;
    }

    public String getPraisenum() {
        return praisenum;
    }

    public void setPraisenum(String praisenum) {
        this.praisenum = praisenum;
    }

    public String getCommentnum() {
        return commentnum;
    }

    public void setCommentnum(String commentnum) {
        this.commentnum = commentnum;
    }

    public Author getAuthor() {
        return author;
    }

    public void setAuthor(Author author) {
        this.author = author;
    }




}
