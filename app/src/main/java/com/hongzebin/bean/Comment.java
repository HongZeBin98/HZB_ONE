package com.hongzebin.bean;

/**
 * 评论
 * Created by 洪泽彬
 */

public class Comment {
    private User user;
    private String content;
    private String updated_at;
    private String praisenum;

    public Comment(User user, String content, String updated_at, String praisenum) {
        this.user = user;
        this.content = content;
        this.updated_at = updated_at;
        this.praisenum = praisenum;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getUpdated_at() {
        return updated_at;
    }

    public void setUpdated_at(String updated_at) {
        this.updated_at = updated_at;
    }

    public String getPraisenum() {
        return praisenum;
    }

    public void setPraisenum(String praisenum) {
        this.praisenum = praisenum;
    }
}
