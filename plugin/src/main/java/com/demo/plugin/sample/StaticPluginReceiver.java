package com.demo.plugin.sample;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.util.Log;

/**
 * Created by guoxiaodong on 2020-02-24 16:15
 */
public class StaticPluginReceiver extends BroadcastReceiver {
    @Override
    public void onReceive(Context context, Intent intent) {
        Log.d("gxd", "StaticPluginReceiver-->" + intent.getAction());

        context.sendBroadcast(new Intent("com.demo.plugin.static.broadcast"));
    }
}
