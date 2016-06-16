package com.henmory.anonymous.custviews;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.widget.TextView;

import com.henmory.anonymous.R;

/**
 * Created by dan on 6/15/16.
 */
public class CustomProgressDialog extends Dialog {

    private TextView tv_message;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        System.out.println("onCreate");
        super.onCreate(savedInstanceState);
    }

    public CustomProgressDialog(Context context) {
        super(context);
    }

    public CustomProgressDialog(Context context, int themeResId) {
        super(context, themeResId);
        setContentView(R.layout.custom_progress_dialog);
        tv_message = (TextView) findViewById(R.id.custom_progress_dailog_text_view);
    }

    protected CustomProgressDialog(Context context, boolean cancelable, OnCancelListener cancelListener) {
        super(context, cancelable, cancelListener);

    }

    public void setMessage(CharSequence msg){
        if (tv_message != null) {
            System.out.println("tv_message is not null");
            tv_message.setText(msg);
            tv_message.setTextSize(20);
            tv_message.invalidate();
        }else{
            System.out.println("null");
        }
    }



}
