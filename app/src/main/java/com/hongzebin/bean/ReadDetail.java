package com.hongzebin.bean;

import java.util.List;

/**阅读详细
 * Created by 洪泽彬
 */

public class ReadDetail {

    private String hp_title;
    private List<Author> author;
    private String hp_content;
    private String praisenum;
    private String commentnum;


    public ReadDetail(String hp_title, List<Author> author, String hp_content, String praisenum, String commentnum) {
        this.hp_title = hp_title;
        this.author = author;
        this.hp_content = hp_content;
        this.praisenum = praisenum;
        this.commentnum = commentnum;
    }

    public ReadDetail(String hp_title) {
        this.hp_title = hp_title;
    }

    public String getHp_title() {
        return hp_title;
    }

    public void setHp_title(String hp_title) {
        this.hp_title = hp_title;
    }

    public List<Author> getAuthor() {
        return author;
    }

    public void setAuthor(List<Author> author) {
        this.author = author;
    }

    public String getHp_content() {
        return hp_content;
    }

    public void setHp_content(String hp_content) {
        this.hp_content = hp_content;
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

}
