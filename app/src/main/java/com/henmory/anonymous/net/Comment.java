package com.henmory.anonymous.net;

/**
 * Created by dan on 16/4/21.
 */
public class Comment {

    private String comment;
    private String phone_num;

    public Comment(String comment, String phone_num) {
        this.comment = comment;
        this.phone_num = phone_num;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    public String getPhone_num() {
        return phone_num;
    }

    public void setPhone_num(String phone_num) {
        this.phone_num = phone_num;
    }
}
