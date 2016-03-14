package com.henmory.anonymous.main;

import android.content.Intent;
import android.os.Bundle;

import android.support.v7.app.AppCompatActivity;

import com.henmory.anonymous.activitys.LoginActivity;
import com.henmory.anonymous.activitys.TimeLineActivity;

public class MainActivity extends AppCompatActivity {

    private static String token;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        token = Config.getCachedToken(this);
        if(token == null){
            startActivity(new Intent(this, LoginActivity.class));
        }else{
            Intent i = new Intent(this, TimeLineActivity.class);
            i.putExtra(Config.KEY_TOKEN, token);
            startActivity(i);
        }
        finish();
    }


}
