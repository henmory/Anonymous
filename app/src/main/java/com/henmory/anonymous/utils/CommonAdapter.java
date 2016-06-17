package com.henmory.anonymous.utils;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by dan on 16/5/30.
 * 可以直接使用这个adapter
 * 实际用到的时候，自己再写一个适配器继承commonAdapter，并实现相应的方法即可
 */
public abstract class CommonAdapter<T> extends BaseAdapter {

    protected List<T> mDatas; //data
    protected Context mContext; //context
    private int mItemLayout; //menu item layout

    public CommonAdapter(List<T> mDatas, Context mContext, int mItemLayout) {
        this.mDatas = mDatas;
        this.mContext = mContext;
        this.mItemLayout = mItemLayout;
    }

    @Override
    public int getCount() {
        return mDatas.size();
    }

    @Override
    public T getItem(int position) {
        return mDatas.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }


    @Override
    public  View getView(int position, View convertView, ViewGroup parent){
        CommonViewHolder holder = CommonViewHolder.getViewHolder(mContext,convertView,parent,mItemLayout,position);

        convert(holder, getItem(position));

        return holder.getConvertView();
    }
    /*
    * set menu item content
    * impements this methord
    * for example:
    *   holder.setText(R.id.message_list_phone_num_value, T.getPhoneNum());
    * */
    public abstract void convert(CommonViewHolder holder, T t);

    /*
    * 范型数据增删
    * */
    public void add(T data) {
        if (mDatas == null) {
            mDatas = new ArrayList<>();
        }
        mDatas.add(data);
        notifyDataSetChanged();
    }

    public void add(T data, int position) {
        if (mDatas == null) {
            mDatas = new ArrayList<>();
        }
        mDatas.add(position, data);
        notifyDataSetChanged();
    }

    public void delete(T data) {
        if (mDatas != null) {
            mDatas.remove(data);
        }
        notifyDataSetChanged();
    }

    public void delete(int position) {
        if (mDatas != null) {
            mDatas.remove(position);
        }
        notifyDataSetChanged();
    }


}

