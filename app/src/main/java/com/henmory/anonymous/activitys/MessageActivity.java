package com.henmory.anonymous.activitys;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.henmory.anonymous.R;
import com.henmory.anonymous.main.Config;
import com.henmory.anonymous.net.PublishComments;

import java.util.ArrayList;
import java.util.List;

public class MessageActivity extends AppCompatActivity {

    private int msgID;
    private TextView tvMessage;
    private ListView lv;
    private List<String> data = new ArrayList<>();
    private EditText etPublishComment;
    private Button btPublishComment;
    private String myPhoneNum;
    private String token;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_message);


        //添加up buttom and action
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        ActionBar ab = getSupportActionBar();
        ab.setDisplayHomeAsUpEnabled(true);


        msgID = getIntent().getIntExtra(Config.KEY_MSG_ID, 0);
        tvMessage = (TextView) findViewById(R.id.tv_message);
        tvMessage.setText(msgID + "");

        data = getIntent().getStringArrayListExtra(Config.KEY_COMMENT);
        lv = (ListView) findViewById(R.id.listView_comment);
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, R.layout.array_item, data);
        lv.setAdapter(adapter);

        myPhoneNum = getIntent().getStringExtra(Config.KEY_PHONE_NUM);
        token = getIntent().getStringExtra(Config.KEY_TOKEN);
        etPublishComment = (EditText) findViewById(R.id.ed_publish_comment);
        btPublishComment = (Button) findViewById(R.id.bt_sendMessage);
        btPublishComment.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (TextUtils.isEmpty(etPublishComment.getText().toString())) {
                    Toast.makeText(MessageActivity.this, "发表评论不能为空", Toast.LENGTH_SHORT).show();
                }
                else {
                    new PublishComments(myPhoneNum, token, etPublishComment.getText().toString(), msgID,
                            new PublishComments.SuccessCallback() {
                                @Override
                                public void onSuccess(int result) {
                                    if (1 == result){
                                        data.add(etPublishComment.getText().toString());
                                        ArrayAdapter<String> adapter = new ArrayAdapter<String>(MessageActivity.this,
                                                R.layout.array_item, data);
                                        lv.setAdapter(adapter);
                                        etPublishComment.setText("");
                                    }
                                }
                            }, new PublishComments.FailCallback() {
                        @Override
                        public void onFail(int errorCode) {
                            switch (errorCode){
                                case Config.STATUS_FAIL:
                                    Toast.makeText(MessageActivity.this, "评论发表失败", Toast.LENGTH_SHORT).show();
                                    break;
                                case Config.STATUS_INVALID_TOKEN:
                                    Toast.makeText(MessageActivity.this, "token过期，请重新登录", Toast.LENGTH_SHORT).show();
                                    Intent intent = new Intent(MessageActivity.this, LoginActivity.class);
                                    startActivity(intent);
                                    finish();
                                    break;
                            }
                        }
                    });
                }
            }
        });

    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.menu_action_search) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }


}
