package com.demo.plugin;

import android.app.Service;
import android.content.Intent;
import android.content.res.Configuration;
import android.os.IBinder;

/**
 * Created by guoxiaodong on 2020-02-23 16:02
 */
public interface IProxyService {
    void attachProxy(Service proxyService);

    void onProxyCreate();

    void onProxyStart(Intent intent, int startId);

    int onProxyStartCommand(Intent intent, int flags, int startId);

    void onProxyDestroy();

    void onProxyConfigurationChanged(Configuration newConfig);

    void onProxyLowMemory();

    void onProxyTrimMemory(int level);

    IBinder onProxyBind(Intent intent);

    boolean onProxyUnbind(Intent intent);

    void onProxyRebind(Intent intent);

    void onProxyTaskRemoved(Intent rootIntent);
}
