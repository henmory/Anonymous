package com.henmory.anonymous.net;

import com.henmory.anonymous.main.Config;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by dan on 16/4/26.
 */
public class PublishMessage {
    public PublishMessage(String myPhoneNum, String token, String msg, final SuccessCallback successCallback, final FailCallback failCallback) {

        new NetConnection(Config.SERVICE_ADD, new NetConnection.SuccessCallBack() {
            @Override
            public void successCallBack(String result) {
                try {
                    JSONObject jsonObject = new JSONObject(result);
                    switch(jsonObject.getInt(Config.KEY_STATUS)){
                        case Config.STATUS_SUCCESS:
                            if (null != successCallback){
                                successCallback.onSuccess();
                            }
                            break;
                        case Config.STATUS_INVALID_TOKEN:
                            if (null != failCallback){
                                failCallback.onFail(Config.STATUS_INVALID_TOKEN);
                            }
                        default:
                            if (null != failCallback){
                                failCallback.onFail(Config.STATUS_FAIL);
                            }
                            break;
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                    if (null != failCallback){
                        failCallback.onFail(Config.STATUS_FAIL);
                    }
                }
            }
        }, new NetConnection.FailedCallBack() {
            @Override
            public void failedCallBack() {
                if (null != failCallback){
                    failCallback.onFail(Config.STATUS_FAIL);
                }
            }
        },HttpMethord.POST,Config.KEY_ACTION, Config.ACTION_PUBLISH_MESSAGE,
                Config.KEY_PHONE_NUM, myPhoneNum,
                Config.KEY_TOKEN, token,
                Config.KEY_MSG, msg);
    }

    public static interface SuccessCallback{
        public void onSuccess();
    }
    public static interface FailCallback{
        public void onFail(int errorCode);
    }
}
