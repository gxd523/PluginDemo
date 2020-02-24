package com.demo.sample;

import android.app.Activity;
import android.content.BroadcastReceiver;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.res.Resources;
import android.os.Bundle;
import android.view.MotionEvent;

import com.demo.plugin.IProxyActivity;

public class ProxyActivity extends Activity {
    private IProxyActivity pluginActivity;

    public static void startActivity(Context context, String className) {
        Intent intent = new Intent(context, ProxyActivity.class);
        intent.putExtra("className", className);
        context.startActivity(intent);
    }

    @Override
    public ClassLoader getClassLoader() {
        return PluginManager.getInstance().getDexClassLoader();
    }

    @Override
    public Resources getResources() {
        return PluginManager.getInstance().getResources();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        String className = getIntent().getStringExtra("className");
        try {
            // 注意这里是getClassLoader()
            Class pluginActivityClass = getClassLoader().loadClass(className);
            pluginActivity = (IProxyActivity) pluginActivityClass.newInstance();
            pluginActivity.attachProxy(this);
            pluginActivity.onProxyCreate(savedInstanceState);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void startActivity(Intent intent) {
        String className = intent.getStringExtra("className");
        Intent intent1 = new Intent(this, ProxyActivity.class);
        intent1.putExtra("className", className);
        super.startActivity(intent1);
    }

    @Override
    public ComponentName startService(Intent service) {
        String classNames = service.getStringExtra("className");
        Intent intent = new Intent(this, ProxyService.class);
        intent.putExtra("className", classNames);
        return super.startService(intent);
    }

    @Override
    public Intent registerReceiver(BroadcastReceiver receiver, IntentFilter filter) {
        IntentFilter intentFilter = new IntentFilter();
        for (int i = 0; i < filter.countActions(); i++) {
            intentFilter.addAction(filter.getAction(i));
        }
        return super.registerReceiver(new ProxyReceiver(receiver.getClass().getName(), this), filter);
    }

    @Override
    protected void onResume() {
        super.onResume();
        pluginActivity.onProxyResume();
    }

    @Override
    protected void onPause() {
        super.onPause();
        pluginActivity.onProxyPause();
    }

    @Override
    protected void onStart() {
        super.onStart();
        pluginActivity.onProxyStart();
    }

    @Override
    protected void onStop() {
        super.onStop();
        pluginActivity.onProxyStop();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        pluginActivity.onProxyDestroy();
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        pluginActivity.onProxyTouchEvent(event);
        return super.onTouchEvent(event);
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        pluginActivity.onProxyBackPressed();
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        pluginActivity.onProxySaveInstanceState(outState);
    }
}
