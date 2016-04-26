package com.henmory.anonymous.net;

import com.henmory.anonymous.main.Config;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by dan on 16/4/26.
 */
public class PublishComments {

    public PublishComments(String phoneNum, String token, String comment, int msgID ,
                           final SuccessCallback successCallback, final FailCallback failCallback) {

        new NetConnection(Config.SERVICE_ADD, new NetConnection.SuccessCallBack() {
            @Override
            public void successCallBack(String result) {
                try {
                    JSONObject jsonObject = new JSONObject(result);
                    switch (jsonObject.getInt(Config.KEY_STATUS)){
                        case Config.STATUS_SUCCESS:
                            if (successCallback != null){
                                successCallback.onSuccess(Config.STATUS_SUCCESS);
                            }
                            break;
                        default:
                            if (failCallback != null) {
                                failCallback.onFail(jsonObject.getInt(Config.KEY_STATUS));
                            }
                            break;
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }

            }
        }, new NetConnection.FailedCallBack() {
            @Override
            public void failedCallBack() {
                if (failCallback != null) {
                    failCallback.onFail(Config.STATUS_FAIL);
                }
            }
        },HttpMethord.POST, Config.KEY_ACTION, Config.ACTION_PUBLISH_COMMENT,
                Config.KEY_PHONE_NUM, phoneNum,
                Config.KEY_TOKEN, token,
                Config.KEY_COMMENT, comment,
                Config.KEY_MSG_ID, msgID + "");
    }

    public static interface SuccessCallback{
        public void onSuccess(int result);
    }

    public static interface FailCallback{
        public void onFail(int errorCode);
    }
}
