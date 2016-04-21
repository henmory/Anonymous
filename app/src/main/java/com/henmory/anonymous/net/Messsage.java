package com.henmory.anonymous.net;

/**
 * Created by dan on 16/4/18.
 */
public class Messsage {

    private int msgId;
    private String msg;
    private String phoneNum;

    public Messsage(int msgId, String phoneNum, String msg) {
        this.msgId = msgId;
        this.phoneNum = phoneNum;
        this.msg = msg;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public int getMsgId() {
        return msgId;
    }

    public void setMsgId(int msgId) {
        this.msgId = msgId;
    }

    public String getPhoneNum() {
        return phoneNum;
    }

    public void setPhoneNum(String phoneNum) {
        this.phoneNum = phoneNum;
    }



}
