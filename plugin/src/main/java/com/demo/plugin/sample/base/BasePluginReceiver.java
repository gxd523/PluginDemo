package com.demo.plugin.sample.base;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

import com.demo.plugin.IProxyBroadcastReceiver;

/**
 * Created by guoxiaodong on 2020-02-24 13:09
 */
public abstract class BasePluginReceiver extends BroadcastReceiver implements IProxyBroadcastReceiver {
    @Override
    public void attachProxy(Context context) {
    }

    @Override
    public void onProxyReceive(Context context, Intent intent) {
    }

    @Override
    public void onReceive(Context context, Intent intent) {

    }
}
