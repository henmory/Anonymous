package com.henmory.anonymous.net;

import com.henmory.anonymous.main.Config;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by dan on 16/4/12.
 */
public class GetCode {

    public GetCode(String phone, final SuccessCallback successCallback, final FailCallback failCallback){


        new NetConnection(Config.SERVICE_ADD, new NetConnection.SuccessCallBack() {
            @Override
            public void successCallBack(String result) {
                try {
                    JSONObject jsonObject = new JSONObject(result);
                    switch (jsonObject.getInt(Config.KEY_STATUS))
                    {
                        case Config.STATUS_SUCCESS:
                            if (null != successCallback)
                                successCallback.onSuccess();
                            break;
                        default:
                            if (null != failCallback)
                                failCallback.onFail();
                            break;
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }, new NetConnection.FailedCallBack() {
            @Override
            public void failedCallBack() {
                if (null != failCallback)
                    failCallback.onFail();
            }
        }, HttpMethord.POST, Config.KEY_ACTION, Config.ACION_GET_GODE, Config.KEY_PHONE_NUM, phone);

    }

    public static interface SuccessCallback{
        void onSuccess();
    }
    public static interface FailCallback{
        void onFail();
    }

}
