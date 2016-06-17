package com.henmory.anonymous.customviews;

import android.content.Context;

import com.henmory.anonymous.R;
import com.henmory.anonymous.net.Message;
import com.henmory.anonymous.utils.CommonAdapter;
import com.henmory.anonymous.utils.CommonViewHolder;

import java.util.List;


/**
 * Created by dan on 6/16/16.
 */
public class MessageListAdapter extends CommonAdapter<Message> {

    public MessageListAdapter(List mDatas, Context mContext, int mItemLayout) {
        super(mDatas, mContext, mItemLayout);
    }


    @Override
    public void convert(CommonViewHolder holder, Message message) {
        holder.setText(R.id.message_list_phone_num_value, message.getPhoneNum());
        holder.setText(R.id.message_list_msg_id_value, message.getMsg());
        holder.setText(R.id.message_msg_content, message.getMsg());
    }
}
