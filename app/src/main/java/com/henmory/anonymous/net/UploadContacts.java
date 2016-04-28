package com.henmory.anonymous.net;

import com.henmory.anonymous.main.Config;

import org.json.JSONException;
import org.json.JSONObject;


/**
 * Created by dan on 16/4/18.
 */
public class UploadContacts {

    public UploadContacts(String my_num, String token, String contacts, final SuccessCallback successCallback, final FailCallback failCallback){


            new NetConnection(Config.SERVICE_ADD, new NetConnection.SuccessCallBack() {
                @Override
                public void onSucces(String result) {
                    try {
                        JSONObject jsonObj = new JSONObject(result);
                        switch(jsonObj.getInt(Config.KEY_STATUS)){
                            case Config.STATUS_SUCCESS:
                                if (null != successCallback){
                                    successCallback.onSuccess();
                                }
                                break;
                            case Config.STATUS_INVALID_TOKEN:
                                if (null != failCallback){
                                    failCallback.onFail(Config.STATUS_INVALID_TOKEN);
                                }
                                break;
                            default:
                                if (null != failCallback){
                                    failCallback.onFail(Config.STATUS_FAIL);
                                }
                                break;
                        }
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
            }, new NetConnection.FailedCallBack() {
                @Override
                public void onFail() {
                    if (null != failCallback) {
                        failCallback.onFail(Config.STATUS_FAIL);
                    }
                }
            }, HttpMethord.POST, Config.KEY_ACTION, Config.ACTION_UPLOAD_CONTACTS, Config.KEY_PHONE_NUM, my_num, Config.KEY_TOKEN, token, Config.KEY_CONTACTS, contacts);
    }

    public static interface SuccessCallback{
        void onSuccess();
    }
    public static interface FailCallback{
        void onFail(int result);
    }

}
