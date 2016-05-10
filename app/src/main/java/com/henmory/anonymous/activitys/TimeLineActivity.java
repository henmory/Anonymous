package com.henmory.anonymous.activitys;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.ProgressBar;
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
    private int highMsgID;
    private Button btnPublishMessage;
    final ProgressDialog pd = new ProgressDialog(this, ProgressDialog.STYLE_SPINNER);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_time_line);

        //默认显示activity名字
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        lv = (ListView) findViewById(R.id.lstView_show);

        my_num = getIntent().getStringExtra(Config.KEY_PHONE_NUM);
        token = getIntent().getStringExtra(Config.KEY_TOKEN);
        contacts = MyContacts.getContactsPhoneNumber(this);

        btnPublishMessage = (Button) findViewById(R.id.btn_publish_message);
        //TODO 添加进度条，当网络连接过程中，先学习内容存储区域再回来调整
        pd.setTitle("跳转中，请稍后...");
        pd.show();
        new UploadContacts(my_num, token, contacts, new UploadContacts.SuccessCallback() {
            @Override
            public void onSuccess() {
                loadMessage();
                pd.dismiss();
            }
        }, new UploadContacts.FailCallback() {
            @Override
            public void onFail(int result) {
                pd.dismiss();
                switch (result) {
                    case Config.STATUS_INVALID_TOKEN:
                        Toast.makeText(TimeLineActivity.this, "token 无效！", Toast.LENGTH_SHORT).show();
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

        btnPublishMessage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(TimeLineActivity.this, PublishMessageActivity.class);
                intent.putExtra(Config.KEY_TOKEN, token);
                intent.putExtra(Config.KEY_PHONE_NUM, my_num);
                startActivity(intent);
            }
        });
    }

    public void loadMessage() {
        new LoadMessage(my_num, token, curPageIndex, curPerageItemNum, new LoadMessage.SuccessCallback() {
            @Override //下载成功后，更新页面
            public void onSuccess(int pageIndex, int perPageItemNum, List<Messsage> timeline) {
                pd.dismiss();
                curPageIndex = pageIndex;
                curPerageItemNum = perPageItemNum;

                items = new ArrayList<>();
                Messsage messsage = null;
                for (int i = 0; i < timeline.size(); i++) {
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
                pd.dismiss();
                switch (result) {
                    case Config.STATUS_INVALID_TOKEN: //token过期
                        Toast.makeText(TimeLineActivity.this, "token过期，请重新登录", Toast.LENGTH_SHORT).show();
                        Intent intent = new Intent(TimeLineActivity.this, LoginActivity.class);
                        startActivity(intent);
                        finish();
                        break;
                    default://下载消息失败
                        Toast.makeText(TimeLineActivity.this, "服务器消息获取失败...", Toast.LENGTH_SHORT).show();
                        break;
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
            Toast.makeText(this, "search", Toast.LENGTH_SHORT).show();
            return true;
        } else if (id == R.id.menu_action_notify) {
            Toast.makeText(this, "notify", Toast.LENGTH_SHORT).show();
            return true;
        } else if (id == R.id.menu_item_1) {
            Toast.makeText(this, "menu_item_1", Toast.LENGTH_SHORT).show();
            return true;
        } else if (id == R.id.menu_item_2) {
            Toast.makeText(this, "menu_item_2", Toast.LENGTH_SHORT).show();
            return true;
        } else
            return super.onOptionsItemSelected(item);

    }


}
