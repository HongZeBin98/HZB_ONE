package com.hongzebin.bean;

public class Author {

    private String user_name;
    private String desc;

    public Author(){}

    public Author(String user_name) {
        this.user_name = user_name;
    }

    public Author(String user_name, String desc) {
        this.user_name = user_name;
        this.desc = desc;
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }

    public String getUser_name() {
        return user_name;
    }

    public void setUser_name(String user_name) {
        this.user_name = user_name;
    }

}
