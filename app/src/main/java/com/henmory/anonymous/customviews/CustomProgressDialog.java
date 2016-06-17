package com.henmory.anonymous.customviews;

import android.app.Dialog;
import android.content.Context;
import android.widget.TextView;

import com.henmory.anonymous.R;

/**
 * Created by dan on 6/15/16.
 */
public class CustomProgressDialog extends Dialog {

    private TextView tv_message;

    public CustomProgressDialog(Context context) {
        super(context);
    }

    public CustomProgressDialog(Context context, int themeResId) {
        super(context, themeResId);
        setContentView(R.layout.custom_progress_dialog);
        tv_message = (TextView) findViewById(R.id.custom_progress_dailog_text_view);
    }

    public CustomProgressDialog(Context context, boolean cancelable, OnCancelListener cancelListener) {
        super(context, cancelable, cancelListener);

    }

    public void setMessage(CharSequence msg) {
        if (tv_message != null) {
            tv_message.setText(msg);
            tv_message.setTextSize(20);
            tv_message.invalidate();
        }
    }

}
