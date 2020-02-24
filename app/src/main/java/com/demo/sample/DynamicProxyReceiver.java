package com.demo.sample;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

import com.demo.plugin.IProxyBroadcastReceiver;

/**
 * Created by guoxiaodong on 2020-02-24 13:24
 */
public class DynamicProxyReceiver extends BroadcastReceiver {
    private IProxyBroadcastReceiver pluginReceiver;

    public DynamicProxyReceiver(String className, Context context) {
        try {
            Class pluginReceiverClass = PluginManager.getInstance().getDexClassLoader().loadClass(className);
            pluginReceiver = (IProxyBroadcastReceiver) pluginReceiverClass.newInstance();
            pluginReceiver.attachProxy(context);
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    @Override
    public void onReceive(Context context, Intent intent) {
        pluginReceiver.onProxyReceive(context, intent);
    }
}
