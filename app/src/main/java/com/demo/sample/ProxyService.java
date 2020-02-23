package com.demo.sample;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.util.Log;

import com.demo.plugin.IProxyService;

public class ProxyService extends Service {
    private IProxyService pluginService;

    @Override
    public ClassLoader getClassLoader() {
        return PluginManager.getInstance().getDexClassLoader();
    }

    private void initPluginService(Intent intent) {
        String className = intent.getStringExtra("className");
        try {
            Class pluginServiceClass = getClassLoader().loadClass(className);
            pluginService = (IProxyService) pluginServiceClass.newInstance();

            pluginService.attachProxy(this);
            pluginService.onProxyCreate();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public IBinder onBind(Intent intent) {
        Log.d("gxd", "ProxyService.onBind-->");
        initPluginService(intent);
        pluginService.onProxyBind(intent);
        return null;
    }

    @Override
    public boolean onUnbind(Intent intent) {
        pluginService.onProxyUnbind(intent);
        return super.onUnbind(intent);
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        Log.d("gxd", "ProxyService.onStartCommand-->");
        initPluginService(intent);
        pluginService.onProxyStartCommand(intent, flags, startId);
        return super.onStartCommand(intent, flags, startId);
    }

    @Override
    public void onDestroy() {
        pluginService.onProxyDestroy();
        super.onDestroy();
    }
}
