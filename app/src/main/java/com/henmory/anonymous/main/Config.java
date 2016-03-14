package com.henmory.anonymous.main;

import android.content.Context;
import android.content.SharedPreferences.Editor;

/**
 * Created by henmory on 2016/3/14.
 */
public class Config {

    public static final String APP_ID = "com.henmory.anonymous";
    public static final String KEY_TOKEN= "token";

    public static String getCachedToken(Context ct){

        return ct.getSharedPreferences(APP_ID, Context.MODE_PRIVATE).getString(KEY_TOKEN, null);
    }

    public static void setCachedToken(Context ct, String token){
        Editor e = ct.getSharedPreferences(APP_ID,Context.MODE_PRIVATE).edit();
        e.putString(KEY_TOKEN, token);
        e.commit();
    }
}
