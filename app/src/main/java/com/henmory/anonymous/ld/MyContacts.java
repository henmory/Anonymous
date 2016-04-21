package com.henmory.anonymous.ld;

import android.content.Context;
import android.database.Cursor;
import android.provider.ContactsContract;

import com.henmory.anonymous.main.Config;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by henmory on 2016/3/14.
 */
public class MyContacts {

    //获取手机联系人信息
    public static String getContactsPhoneNumber(Context ct){
        JSONArray jsonArr = new JSONArray();
        JSONObject jsonObj = null;
        String phone;

        Cursor cursor = ct.getContentResolver().query(ContactsContract.CommonDataKinds.Phone.CONTENT_URI,
                null, null, null, null);
//
        while (cursor.moveToNext()){

            phone = cursor.getString(cursor.getColumnIndex(ContactsContract.CommonDataKinds.Phone.NUMBER));
            if (phone.charAt(0) == '+' &&
                    phone.charAt(1) == '8' &&
                    phone.charAt(2) == '6'){
                phone = phone.substring(3);
            }
            try {
                jsonObj = new JSONObject(); // TODO: 16/4/18
                jsonObj.put(Config.KEY_PHONE_NUM, phone);
            } catch (JSONException e) {
                e.printStackTrace();
            }
            jsonArr.put(jsonObj);
        }
        return jsonArr.toString();
    }
}
