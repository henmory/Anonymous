package com.henmory.anonymous.net;

import com.henmory.anonymous.main.Config;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by dan on 16/4/14.
 */
public class Login {

    public Login(String phone, String code, final SuccessCallback successCallback, final FailCallback failCallback){

        new NetConnection(Config.SERVICE_ADD, new NetConnection.SuccessCallBack() {
            @Override
            public void successCallBack(String result) {
                JSONObject json = null;
                try {
                    json = new JSONObject(result);
                    switch (json.getInt(Config.KEY_STATUS)) {
                        case Config.STATUS_SUCCESS:
                            if (successCallback != null) {
                                successCallback.onSuccess(json.getString(Config.KEY_TOKEN));
                            }
                            break;
                        default:
                            if (failCallback != null) {
                                failCallback.onFail();
                            }
                            break;

                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                    if (failCallback != null){
                        failCallback.onFail();
                    }
                }
            }
        }, new NetConnection.FailedCallBack() {
            @Override
            public void failedCallBack() {
                if (failCallback != null) {
                    failCallback.onFail();
                }
            }
        }, HttpMethord.POST, Config.KEY_ACTION, Config.ACTION_LOGIN, Config.KEY_PHONE_NUM, phone, Config.KEY_CODE, code);
    }
    public static interface SuccessCallback{
        void onSuccess(String token);
    }
    public static interface FailCallback{
        void onFail();
    }
}
