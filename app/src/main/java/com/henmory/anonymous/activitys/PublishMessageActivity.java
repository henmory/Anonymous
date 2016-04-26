package com.henmory.anonymous.activitys;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.henmory.anonymous.R;
import com.henmory.anonymous.main.Config;
import com.henmory.anonymous.net.PublishMessage;

public class PublishMessageActivity extends AppCompatActivity {

    private EditText edPublishMessage;
    private Button btnPublishMessage;
    private String myPhoneNum;
    private String token;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_publish_message);

        edPublishMessage = (EditText) findViewById(R.id.et_publish_message);
        btnPublishMessage = (Button) findViewById(R.id.bt_publish_message);

        myPhoneNum = getIntent().getStringExtra(Config.KEY_PHONE_NUM);
        token = getIntent().getStringExtra(Config.KEY_TOKEN);

        btnPublishMessage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (TextUtils.isEmpty(edPublishMessage.getText().toString())) {
                    Toast.makeText(PublishMessageActivity.this, "消息体不能为空",Toast.LENGTH_SHORT).show();
                }
                else {
                    new PublishMessage(myPhoneNum, token, edPublishMessage.getText().toString(),
                            new PublishMessage.SuccessCallback() {
                                @Override
                                public void onSuccess() {
                                    Intent intent = new Intent(PublishMessageActivity.this, TimeLineActivity.class);
                                    intent.putExtra(Config.KEY_PHONE_NUM, myPhoneNum);
                                    intent.putExtra(Config.KEY_TOKEN, token);
                                    startActivity(intent);
                                    finish();
                                }
                            }, new PublishMessage.FailCallback() {
                        @Override
                        public void onFail(int errorCode) {
                            switch (errorCode){
                                case Config.STATUS_INVALID_TOKEN:
                                    Toast.makeText(PublishMessageActivity.this, "token过期，请重新登录",
                                            Toast.LENGTH_SHORT).show();
                                    Intent intent = new Intent(PublishMessageActivity.this, LoginActivity.class);
                                    startActivity(intent);
                                    finish();
                                    break;
                                default:
                                    Toast.makeText(PublishMessageActivity.this, "消息发表失败，请稍后重试",
                                            Toast.LENGTH_SHORT).show();
                                    break;
                            }
                        }
                    });
                }
            }
        });
    }

}
