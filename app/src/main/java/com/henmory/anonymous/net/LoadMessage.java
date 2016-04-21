package com.henmory.anonymous.net;

import com.henmory.anonymous.main.Config;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by dan on 16/4/18.
 */
public class LoadMessage {
    public  LoadMessage(String myNumber, String token, int page_index, int perPageItemNum, final SuccessCallback successCallback, final FailCallback failCallback){

        new NetConnection(Config.SERVICE_ADD, new NetConnection.SuccessCallBack() {
            @Override
            public void successCallBack(String result) {
                try {
                    JSONObject jsonObj = new JSONObject(result);
                    switch (jsonObj.getInt(Config.KEY_STATUS)){
                        case Config.STATUS_SUCCESS:
                            if (null != successCallback){
                                List<Messsage> data = new ArrayList<Messsage>();
                                JSONArray dataArr = jsonObj.getJSONArray(Config.KEY_TIMELINE);
                                Messsage msg = null;
                                JSONObject jsonObject = null;

                                for (int i = 0; i < dataArr.length(); i++) {
                                    jsonObject = dataArr.getJSONObject(i);
                                    msg = new Messsage(
                                            jsonObject.getInt(Config.KEY_MSG_ID),
                                            jsonObject.getString(Config.KEY_PHONE_NUM),
                                            jsonObject.getString(Config.KEY_MSG));
                                    data.add(msg);
                                }
                                successCallback.onSuccess(jsonObj.getInt(Config.KEY_PAGE_INDEX),
                                        jsonObj.getInt(Config.KEY_PERPAGE_ITEM_NUM), data);
                            }
                            break;
                        case Config.STATUS_INVALID_TOKEN:
                            if (null != failCallback){
                                failCallback.onFailed(Config.STATUS_INVALID_TOKEN);
                            }
                            break;
                        default:
                            if (null != failCallback) {
                                failCallback.onFailed(Config.STATUS_FAIL);
                            }
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }, new NetConnection.FailedCallBack() {
            @Override
            public void failedCallBack() {
                if (failCallback != null){
                    failCallback.onFailed(Config.STATUS_FAIL);
                }
            }
        },HttpMethord.POST, Config.KEY_ACTION, Config.ACTION_TIMELINE, Config.KEY_PHONE_NUM, myNumber,
                Config.KEY_PAGE_INDEX, page_index + "", Config.KEY_PERPAGE_ITEM_NUM, perPageItemNum + "", //// TODO: 16/4/18 为什么这样可以
                Config.KEY_TOKEN, token);
    }

    public static interface SuccessCallback{
        void onSuccess(int pageIndex, int perPageItemNum, List<Messsage> timeline);

    }

    public static interface FailCallback{
        void onFailed(int result);
    }
}

