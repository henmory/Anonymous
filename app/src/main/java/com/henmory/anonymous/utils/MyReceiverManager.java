package com.henmory.anonymous.utils;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.IntentFilter;
import android.support.v4.content.LocalBroadcastManager;

/**
 * Created by dan on 16/6/12.
 * manage BroadcastReceiver autolly, but not to complete
 */
public class MyReceiverManager {
    private MyReceiver receiver;
    private Context context;

    public MyReceiverManager(Context context) {
        this.context = context;
    }

    public void registerGlobalBraodcaseReveiver(){

    }
    public void registerLocalBroadcastReceiver(BroadcastReceiver receiver, IntentFilter intentFilter){
        LocalBroadcastManager localBroadcastManager = LocalBroadcastManager.getInstance(context);
        localBroadcastManager.registerReceiver(receiver,intentFilter);
    }
}
