package com.henmory.anonymous.activitys;

import android.app.ProgressDialog;
import android.content.Intent;
import android.content.res.ColorStateList;
import android.content.res.Resources;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.henmory.anonymous.R;
import com.henmory.anonymous.custviews.CustomProgressDialog;
import com.henmory.anonymous.net.GetCode;

public class GetCodeActivity extends AppCompatActivity {


    private EditText edPhoneNum = null;
    private Button btnGetCode;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_get_code);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        edPhoneNum = (EditText) findViewById(R.id.edt_phoneNum);
        btnGetCode = (Button) findViewById(R.id.btn_getCode);

        //获取验证码
        btnGetCode.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (TextUtils.isEmpty(edPhoneNum.getText())) {
                    Toast.makeText(GetCodeActivity.this, R.string.phonenum_is_not_null, Toast.LENGTH_SHORT).show();
                    return;
                }
                final CustomProgressDialog customProgressDialog = new CustomProgressDialog(GetCodeActivity.this, R.style.CustomProgressDialog);
                customProgressDialog.setMessage("加载中...");
                customProgressDialog.setCancelable(false);
                customProgressDialog.show();
//                final ProgressDialog dialog = new ProgressDialog(GetCodeActivity.this);
//                dialog.setMessage("验证码获取中，请守候...");
//                dialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
//                dialog.show();
                new GetCode(edPhoneNum.getText().toString(), new GetCode.SuccessCallback() {
                    @Override
                    public void onSuccess() {
                        Toast.makeText(GetCodeActivity.this, R.string.success_to_get_code, Toast.LENGTH_SHORT).show();
//                        dialog.dismiss();
                        customProgressDialog.dismiss();
                        finish();
                        startActivity(new Intent(GetCodeActivity.this, LoginActivity.class));
                    }
                }, new GetCode.FailCallback() {
                    @Override
                    public void onFail() {
                        customProgressDialog.dismiss();
//                        dialog.dismiss();
                        Toast.makeText(GetCodeActivity.this, R.string.fail_to_get_code, Toast.LENGTH_SHORT).show();
//                        finish();
//                        startActivity(new Intent(GetCodeActivity.this, LoginActivity.class));
                    }
                });
            }
        });

    }

}
