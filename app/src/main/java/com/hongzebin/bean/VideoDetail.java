package com.hongzebin.bean;

/**
 * 影视列表
 * Created by 洪泽彬
 */

public class VideoDetail {

    private String title;
    private User user;
    private String summary;
    private String content;
    private String praisenum;

    public VideoDetail(String title, User user, String summary, String content, String praisenum) {
        this.title = title;
        this.user = user;
        this.summary = summary;
        this.content = content;
        this.praisenum = praisenum;
    }

    public String getTitle() {
        return title;

    }

    public void setTitle(String title) {
        this.title = title;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public String getSummary() {
        return summary;
    }

    public void setSummary(String summary) {
        this.summary = summary;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getPraisenum() {
        return praisenum;
    }

    public void setPraisenum(String praisenum) {
        this.praisenum = praisenum;
    }
}
