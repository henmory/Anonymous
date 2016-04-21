package com.henmory.anonymous.net;

import com.henmory.anonymous.main.Config;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by dan on 16/4/20.
 */
public class GetComment {

    public GetComment(String phone, String token, int page_index, int perpageItemNum, int msgID,
                      final SuccessCallback successCallback, final FailCallback failCallback) {

        new NetConnection(Config.SERVICE_ADD, new NetConnection.SuccessCallBack() {
            @Override
            public void successCallBack(String result) {
                try {
                    JSONObject jsonObject = new JSONObject(result);
                    switch (jsonObject.getInt(Config.KEY_STATUS)){
                        case Config.STATUS_SUCCESS:
                            if (null != successCallback){

                                List<Comment> data = new ArrayList<Comment>();
                                JSONArray jsonArray = jsonObject.getJSONArray(Config.KEY_TIMELINE);
                                JSONObject jsonObject1 = null;
                                Comment comment = null;
                                for (int i = 0; i < jsonArray.length(); i++) {
                                    jsonObject1 = jsonArray.getJSONObject(i);
                                    comment = new Comment(jsonObject1.getString(Config.KEY_COMMENT),
                                            jsonObject1.getString(Config.KEY_PHONE_NUM));
                                    data.add(comment);
                                }

                                successCallback.onSuccess(jsonObject.getInt(Config.KEY_MSG_ID),
                                        jsonObject.getInt(Config.KEY_PAGE_INDEX),
                                        jsonObject.getInt(Config.KEY_PERPAGE_ITEM_NUM),
                                        data);
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
                    if (failCallback != null) {
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
        },HttpMethord.POST,
                Config.KEY_ACTION, Config.ACTION_GETCOMMENT,
                Config.KEY_PHONE_NUM, phone,
                Config.KEY_TOKEN, token,
                Config.KEY_PAGE_INDEX, page_index + "",
                Config.KEY_PERPAGE_ITEM_NUM, perpageItemNum + "",
                Config.KEY_MSG_ID, msgID + "");
    }

    public static interface SuccessCallback{
        void onSuccess(int msgID, int page_index, int perpage_intem_num, List<Comment> data);
    }
    public static interface FailCallback{
        void onFail(int result);
    }
}
