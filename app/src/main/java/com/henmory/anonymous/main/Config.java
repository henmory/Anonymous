package com.henmory.anonymous.main;

import android.content.Context;
import android.content.SharedPreferences.Editor;

/**
 * Created by henmory on 2016/3/14.
 */
public class Config {

    public static final String APP_ID = "com.henmory.anonymous";

    public static final String KEY_ACTION = "action";
    public static final String KEY_TOKEN= "token";
    public static final String KEY_PHONE_NUM = "phone";
    public static final String KEY_CODE = "code";
    public static final String KEY_STATUS = "status";
    public static final String KEY_CONTACTS = "contacts";
    public static final String KEY_PAGE_INDEX = "page_index";
    public static final String KEY_PERPAGE_ITEM_NUM = "per_page_item_num";
    public static final String KEY_TIMELINE = "timeline" ;
    public static final String KEY_MSG_ID = "msgId";
    public static final String KEY_COMMENT = "comment";
    public static final String KEY_MSG = "msg";

    public static final String ACION_GET_GODE = "send_pass";
    public static final String ACTION_LOGIN = "login";
    public static final String ACTION_UPLOAD_CONTACTS = "upload_contacts";
    public static final String ACTION_TIMELINE = "timeline";
    public static final String ACTION_GETCOMMENT = "get_comment";
    public static final String ACTION_PUBLISH_COMMENT = "pub_comment";
    public static final String ACTION_PUBLISH_MESSAGE = "publish_message";

    public static final String CHARSET = "UTF-8";

    public static final String SERVICE_ADD = "http://10.9.52.104:8080/TestService/api.jsp";//ip地址可以修改
    public static final int STATUS_SUCCESS = 1;
    public static final int STATUS_FAIL = 0;
    public static final int STATUS_INVALID_TOKEN = 2;

    public static String getToken(Context ct){

        return ct.getSharedPreferences(APP_ID, Context.MODE_PRIVATE).getString(KEY_TOKEN, null);
    }

    public static void cachedToken(Context ct, String token){
        Editor e = ct.getSharedPreferences(APP_ID,Context.MODE_PRIVATE).edit();
        e.putString(KEY_TOKEN, token);
        e.commit();
    }

    public static void cachedPhoneNum(Context ct, String phone_num){
        Editor e = ct.getSharedPreferences(APP_ID, Context.MODE_PRIVATE).edit();
        e.putString(KEY_PHONE_NUM, phone_num);
        e.commit();
    }
    public static String getPhonenum(Context ct){
        return ct.getSharedPreferences(APP_ID, Context.MODE_PRIVATE).getString(KEY_PHONE_NUM, null);
    }
}
