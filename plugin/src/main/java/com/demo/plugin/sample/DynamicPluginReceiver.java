package com.demo.plugin.sample;

import android.content.Context;
import android.content.Intent;
import android.util.Log;

import com.demo.plugin.sample.base.BasePluginReceiver;

/**
 * Created by guoxiaodong on 2020-02-24 13:17
 */
public class DynamicPluginReceiver extends BasePluginReceiver {
    @Override
    public void attachProxy(Context context) {
        Log.d("gxd", "DynamicPluginReceiver.attachProxy-->");
    }

    @Override
    public void onProxyReceive(Context context, Intent intent) {
        Log.d("gxd", "DynamicPluginReceiver.onProxyReceive-->" + intent.getAction());
    }
}
