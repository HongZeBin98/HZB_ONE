package com.hongzebin.bean;

/**
 * 插画详细
 * Created by 洪泽彬
 */

public class PictureDetail {

    private String hpcontent_id;
    private String hp_img_url;
    private String hp_author;
    private String hp_content;
    private String last_update_date;
    private String text_authors;
    private String praisenum;

    public PictureDetail(String hpcontent_id, String hp_img_url, String hp_author, String hp_content, String last_update_date, String text_authors, String praisenum) {
        this.hpcontent_id = hpcontent_id;
        this.hp_img_url = hp_img_url;
        this.hp_author = hp_author;
        this.hp_content = hp_content;
        this.last_update_date = last_update_date;
        this.text_authors = text_authors;
        this.praisenum = praisenum;
    }

    public String getHpcontent_id() {
        return hpcontent_id;
    }

    public void setHpcontent_id(String hpcontent_id) {
        this.hpcontent_id = hpcontent_id;
    }

    public String getHp_img_url() {
        return hp_img_url;
    }

    public void setHp_img_url(String hp_img_url) {
        this.hp_img_url = hp_img_url;
    }

    public String getHp_author() {
        return hp_author;
    }

    public void setHp_author(String hp_author) {
        this.hp_author = hp_author;
    }

    public String getHp_content() {
        return hp_content;
    }

    public void setHp_content(String hp_content) {
        this.hp_content = hp_content;
    }

    public String getLast_update_date() {
        return last_update_date;
    }

    public void setLast_update_date(String last_update_date) {
        this.last_update_date = last_update_date;
    }

    public String getText_authors() {
        return text_authors;
    }

    public void setText_authors(String text_authors) {
        this.text_authors = text_authors;
    }

    public String getPraisenum() {
        return praisenum;
    }

    public void setPraisenum(String praisenum) {
        this.praisenum = praisenum;
    }

}
