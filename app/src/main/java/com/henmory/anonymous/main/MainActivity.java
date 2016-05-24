package com.henmory.anonymous.main;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;

import com.henmory.anonymous.activitys.LoginActivity;
import com.henmory.anonymous.activitys.TimeLineActivity;
import com.henmory.anonymous.database.MyDatabaseHelper;

public class MainActivity extends AppCompatActivity {

    private static String token;
    private static String phoneNum;
    private static final String  ACTIVITY_TAG = "MainActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        token = Config.getToken(this);
        phoneNum = Config.getPhonenum(this);
        if ((token == null) || (phoneNum == null)) {
            startActivity(new Intent(this, LoginActivity.class));
        } else {
            Log.d(ACTIVITY_TAG, "token is valid, and enter main screen");
            Intent i = new Intent(this, TimeLineActivity.class);
            i.putExtra(Config.KEY_TOKEN, token);
            i.putExtra(Config.KEY_PHONE_NUM, phoneNum);
            startActivity(i);
        }
        finish();
//        testDatabase();
    }

    //测试数据库
    void testDatabase(){
        SQLiteDatabase db = MyDatabaseHelper.getDatabase(this);
        String sql;

        //insert
        sql = "insert into "  + MyDatabaseHelper.TABLE_NAME + " (username, password)"
                + " values ('henmory', 'changhong')";
        Log.d(ACTIVITY_TAG,sql);
        db.execSQL(sql);
        sql = "insert into " + MyDatabaseHelper.TABLE_NAME + "(username, password)"
                + " values ('hao de', 'ok')";
        db.execSQL(sql);

       //delete
        sql = "delete from " + MyDatabaseHelper.TABLE_NAME + " where username = 'henmory'";
        Log.d(ACTIVITY_TAG,sql);
        db.execSQL(sql);

        //modify
        sql = "update " + MyDatabaseHelper.TABLE_NAME + " set password = '123' where username = 'hao de'";
        Log.d(ACTIVITY_TAG,sql);
        db.execSQL(sql);

        //query
        sql = "select * from " + MyDatabaseHelper.TABLE_NAME + " where username = 'hao de'" ;
        Log.d(ACTIVITY_TAG,sql);
        Cursor c = db.rawQuery(sql, null);
        while (c.moveToNext()){
            Log.d(ACTIVITY_TAG,c.getColumnCount() + "");
        }
    }



}
