package com.henmory.anonymous.activitys;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import com.henmory.anonymous.R;
import com.henmory.anonymous.ld.MyContacts;
import com.henmory.anonymous.main.Config;
import com.henmory.anonymous.net.Comment;
import com.henmory.anonymous.net.GetComment;
import com.henmory.anonymous.net.LoadMessage;
import com.henmory.anonymous.net.Messsage;
import com.henmory.anonymous.net.UploadContacts;

import java.util.ArrayList;
import java.util.List;

public class TimeLineActivity extends AppCompatActivity {

    private String my_num;
    private String token;
    private String contacts;
    private int curPageIndex; //目前页面索引
    private int curPerageItemNum;//目前页面信息数量
    private List<Messsage> items;//存储所有从服务器得到的数据
    private List<String> msgShow = new ArrayList<>();//只显示的数据，msg
    private ListView lv;
    private  int highMsgID;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_time_line);

        lv = (ListView) findViewById(R.id.lstView_show);

        my_num = getIntent().getStringExtra(Config.KEY_PHONE_NUM);
        token = getIntent().getStringExtra(Config.KEY_TOKEN);
        contacts = MyContacts.getContactsPhoneNumber(this);
        new UploadContacts(my_num, token, contacts, new UploadContacts.SuccessCallback() {
            @Override
            public void onSuccess() {
                loadMessage();
            }
        }, new UploadContacts.FailCallback() {
            @Override
            public void onFail(int result) {
                switch (result){
                    case Config.STATUS_INVALID_TOKEN:
                        Intent intent = new Intent(TimeLineActivity.this, LoginActivity.class);
                        startActivity(intent);
                        finish();
                        break;
                    default:
                        loadMessage();
                        break;
                }
            }
        });

        lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                highMsgID = items.get(position).getMsgId();
                new GetComment(my_num, token, curPageIndex, curPerageItemNum, highMsgID, new GetComment.SuccessCallback() {
                    @Override
                    public void onSuccess(int msgID, int page_index, int perpage_intem_num, List<Comment> data) {
                        List<String> comment = new ArrayList<String>();
                        for (int i = 0; i < data.size(); i++) {
                            comment.add(data.get(i).getComment());
                        }
                        Intent intent = new Intent(TimeLineActivity.this, MessageActivity.class);
                        intent.putExtra(Config.KEY_PHONE_NUM, my_num);
                        intent.putExtra(Config.KEY_MSG_ID, msgID);
                        intent.putExtra(Config.KEY_TOKEN, token);
                        intent.putStringArrayListExtra(Config.KEY_COMMENT, (ArrayList<String>) comment);
                        startActivity(intent);
                    }
                }, new GetComment.FailCallback() {
                    @Override
                    public void onFail(int result) {
                        if (result == Config.STATUS_INVALID_TOKEN) {
                            Toast.makeText(TimeLineActivity.this, "token过期，请重新登录", Toast.LENGTH_SHORT).show();
                            Intent intent = new Intent(TimeLineActivity.this, LoginActivity.class);
                            startActivity(intent);
                            finish();
                        } else {
                            Toast.makeText(TimeLineActivity.this, R.string.fail_to_load_timeline_data, Toast.LENGTH_SHORT).show();
                        }
                    }
                });
            }
        });
    }

    public void loadMessage(){
        new LoadMessage(my_num, token, curPageIndex, curPerageItemNum,new LoadMessage.SuccessCallback() {
            @Override //下载成功后，更新页面
            public void onSuccess(int pageIndex, int perPageItemNum, List<Messsage> timeline) {

                curPageIndex = pageIndex;
                curPerageItemNum = perPageItemNum;

                items = new ArrayList<>();
                Messsage messsage = null;
                for (int i = 0; i < timeline.size(); i++){
                    messsage = new Messsage(timeline.get(i).getMsgId(),
                            timeline.get(i).getPhoneNum(),
                            timeline.get(i).getMsg());
                    items.add(messsage);
                    msgShow.add(timeline.get(i).getMsg());
                }

                ArrayAdapter<String> adapter = new ArrayAdapter<>(TimeLineActivity.this,
                        R.layout.array_item, msgShow);
                lv.setAdapter(adapter);
            }
        }, new LoadMessage.FailCallback() {
            @Override
            public void onFailed(int result) {
                switch (result){
                    case Config.STATUS_INVALID_TOKEN: //token过期
                            Toast.makeText(TimeLineActivity.this, "token过期，请重新登录", Toast.LENGTH_SHORT).show();
                            Intent intent = new Intent(TimeLineActivity.this, LoginActivity.class);
                            startActivity(intent);
                            finish();
                        break;
                    default://下载消息失败
                }
            }
        });

    }

}
