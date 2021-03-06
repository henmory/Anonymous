package com.henmory.anonymous.activitys;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.henmory.anonymous.R;
import com.henmory.anonymous.customviews.CustomProgressDialog;
import com.henmory.anonymous.main.Config;
import com.henmory.anonymous.net.Login;

public class LoginActivity extends AppCompatActivity {

    private EditText edPhoneNum = null;
    private EditText edCode = null;
    private Button btnLogin;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        //默认显示activity名字
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);


        edPhoneNum = (EditText) findViewById(R.id.edt_phoneNum);
        edCode = (EditText) findViewById(R.id.edt_Code);
        btnLogin = (Button) findViewById(R.id.btn_login);

        //登录
        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (TextUtils.isEmpty(edPhoneNum.getText()) || TextUtils.isEmpty(edCode.getText())){
                    Toast.makeText(LoginActivity.this, R.string.phoneNum_and_code_is_not_null, Toast.LENGTH_SHORT).show();
                    return;
                }
                final CustomProgressDialog dialog = new CustomProgressDialog(LoginActivity.this, R.style.CustomProgressDialog);
                dialog.setMessage("登录中，请守候...");
                dialog.show();

                new Login(edPhoneNum.getText().toString(), edCode.getText().toString(), new Login.SuccessCallback() {
                    @Override
                    public void onSuccess(String result) {
                        dialog.dismiss();

                        Config.cachedToken(LoginActivity.this, result);
                        Config.cachedPhoneNum(LoginActivity.this, edPhoneNum.getText().toString());

                        Intent intent = new Intent(LoginActivity.this, MessageListActivity.class);
                        intent.putExtra(Config.KEY_PHONE_NUM, edPhoneNum.getText().toString());
                        intent.putExtra(Config.KEY_TOKEN, result);
                        startActivity(intent);
                        finish();
                    }
                }, new Login.FailCallback() {
                    @Override
                    public void onFail() {
                        Toast.makeText(LoginActivity.this, "登录失败", Toast.LENGTH_SHORT).show();
                        dialog.dismiss();
                    }

                });
            }
        });

    }

}
