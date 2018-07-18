package com.hongzebin.bean;

/**
 * 类型列表
 * Created by 洪泽彬
 */

public class TypeOutline {
    private String id;
    private String item_id;
    private String title;
    private String forward;
    private String img_url;
    private String like_count;
    private String post_date;
    private Author author;

    public TypeOutline(String id, String item_id, String title, String forward, String img_url,
                       String like_count, String post_date, Author user_name) {
        this.id = id;
        this.item_id = item_id;
        this.title = title;
        this.forward = forward;
        this.img_url = img_url;
        this.like_count = like_count;
        this.post_date = post_date;
        this.author = user_name;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getItem_id() {
        return item_id;
    }

    public void setItem_id(String item_id) {
        this.item_id = item_id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getForward() {
        return forward;
    }

    public void setForward(String forward) {
        this.forward = forward;
    }

    public String getImg_url() {
        return img_url;
    }

    public void setImg_url(String img_url) {
        this.img_url = img_url;
    }

    public String getLike_count() {
        return like_count;
    }

    public void setLike_count(String like_count) {
        this.like_count = like_count;
    }

    public String getPost_date() {
        return post_date;
    }

    public void setPost_date(String post_date) {
        this.post_date = post_date;
    }

    public Author getAuthor() {
        return author;
    }

    public void setAuthor(Author author) {
        this.author = author;
    }

}
