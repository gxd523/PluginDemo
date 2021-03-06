package com.demo.plugin.sample.base;

import android.app.Activity;
import android.content.BroadcastReceiver;
import android.content.ComponentName;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;

import com.demo.plugin.IProxyActivity;

/**
 * Created by guoxiaodong on 2020-02-22 17:22
 */
public abstract class BasePluginActivity extends Activity implements IProxyActivity {
    protected Activity that;

    @Override
    public void attachProxy(Activity proxyActivity) {
        that = proxyActivity;
    }

    @Override
    public void onProxyCreate(Bundle savedInstanceState) {
    }

    @Override
    public void onProxyStart() {
    }

    @Override
    public void onProxyResume() {
    }

    @Override
    public void onProxyPause() {
    }

    @Override
    public void onProxyStop() {
    }

    @Override
    public void onProxyDestroy() {
    }

    @Override
    public void onProxySaveInstanceState(Bundle outState) {
    }

    @Override
    public boolean onProxyTouchEvent(MotionEvent event) {
        return false;
    }

    @Override
    public void onProxyBackPressed() {
    }

    @Override
    public void setContentView(int layoutResID) {
        that.setContentView(layoutResID);
    }

    @Override
    public <T extends View> T findViewById(int id) {
        return that.findViewById(id);
    }

    @Override
    public void startActivity(Intent intent) {
        Intent intent1 = new Intent();
        intent1.putExtra("className", intent.getComponent().getClassName());
        that.startActivity(intent1);
    }

    @Override
    public ComponentName startService(Intent service) {
        Intent intent = new Intent();
        intent.putExtra("className", service.getComponent().getClassName());
        return that.startService(intent);
    }

    @Override
    public Intent registerReceiver(BroadcastReceiver receiver, IntentFilter filter) {
        return that.registerReceiver(receiver, filter);
    }

    @Override
    public void sendBroadcast(Intent intent) {
        that.sendBroadcast(intent);
    }
}
