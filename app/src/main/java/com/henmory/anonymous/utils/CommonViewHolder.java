package com.henmory.anonymous.utils;

import android.content.Context;
import android.util.SparseArray;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

/**
 * Created by dan on 16/5/30.
 * 以后可以直接用这个viewHolder
 * if there are other views in menu item, you can add other methord like "setText" to set views content
 */
public class CommonViewHolder {

    private SparseArray<View> mViews; //item中所有的控件
    private View mConvertView;//convertView
    private int mPosition; //item的position

    public CommonViewHolder(Context mContext, ViewGroup parent, int layoutRes, int position) {
        this.mViews = new SparseArray<>();
        mConvertView = LayoutInflater.from(mContext).inflate(layoutRes, parent, false);
        mConvertView.setTag(this);
        mPosition = position;
    }

    public static CommonViewHolder getViewHolder(Context mContext, View convertView, ViewGroup parent, int layoutRes, int position){
        CommonViewHolder holder;
        if (convertView == null) {//第一次加载，new convertView
            holder = new CommonViewHolder(mContext, parent, layoutRes, position);
        }else{
            holder = (CommonViewHolder) convertView.getTag();//直接获取tag
            holder.mPosition = position;
        }
        return holder;
    }

    public View getView(int id){
        View view = mViews.get(id);
        if (view == null) { //第一次添加控件资源
            view = mConvertView.findViewById(id);
            mViews.put(id, view);
        }
        return view; //直接返回
    }

    /*
    * 返回convertView
    * */
    public View getConvertView(){
        return mConvertView;
    }


    public CommonViewHolder setText(int resID, String text){
        TextView tv = (TextView) getView(resID);
        tv.setText(text);
        return this;
    }

    public CommonViewHolder setImageResource(int resID, int res){
        ImageView iv = (ImageView) getView(resID);
        iv.setImageResource(res);
        return this;
    }

    /*
    * 设置点击事件
    * */
    public CommonViewHolder setOnClickListener(int resId, View.OnClickListener listener){
        View view = getView(resId);
        view.setOnClickListener(listener);
        return this;
    }

}
