package com.demo.plugin;

import android.content.Context;
import android.content.Intent;

/**
 * Created by guoxiaodong on 2020-02-24 13:06
 */
public interface IProxyBroadcastReceiver {
    void attachProxy(Context context);

    void onProxyReceive(Context context, Intent intent);
}
