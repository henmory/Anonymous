package com.henmory.anonymous.main;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import com.henmory.anonymous.activitys.LoginActivity;
import com.henmory.anonymous.activitys.TimeLineActivity;

public class MainActivity extends AppCompatActivity {

    private static String token;
    private static String phoneNum;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        token = Config.getToken(this);
        phoneNum = Config.getPhonenum(this);

        if ((token == null) || (phoneNum == null)) {
            startActivity(new Intent(this, LoginActivity.class));
        } else {
            System.out.println("token is valid, and enter main screen");
            Intent i = new Intent(this, TimeLineActivity.class);
            i.putExtra(Config.KEY_TOKEN, token);
            i.putExtra(Config.KEY_PHONE_NUM, phoneNum);
            startActivity(i);
        }
        finish();
    }





}
