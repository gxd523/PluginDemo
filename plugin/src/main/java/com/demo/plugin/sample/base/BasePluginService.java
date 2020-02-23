package com.demo.plugin.sample.base;

import android.app.Service;
import android.content.Intent;
import android.content.res.Configuration;
import android.os.IBinder;

import com.demo.plugin.IProxyService;

/**
 * Created by guoxiaodong on 2020-02-23 16:09
 */
public abstract class BasePluginService extends Service implements IProxyService {
    private Service that;

    @Override
    public void attachProxy(Service proxyService) {
        that = proxyService;
    }

    @Override
    public void onProxyCreate() {

    }

    @Override
    public void onProxyStart(Intent intent, int startId) {

    }

    @Override
    public int onProxyStartCommand(Intent intent, int flags, int startId) {
        return 0;
    }

    @Override
    public void onProxyDestroy() {

    }

    @Override
    public void onProxyConfigurationChanged(Configuration newConfig) {

    }

    @Override
    public void onProxyLowMemory() {

    }

    @Override
    public void onProxyTrimMemory(int level) {

    }

    @Override
    public IBinder onProxyBind(Intent intent) {
        return null;
    }

    @Override
    public boolean onProxyUnbind(Intent intent) {
        return false;
    }

    @Override
    public void onProxyRebind(Intent intent) {

    }

    @Override
    public void onProxyTaskRemoved(Intent rootIntent) {

    }

    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }
}
