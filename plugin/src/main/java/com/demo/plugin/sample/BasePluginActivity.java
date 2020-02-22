package com.demo.plugin.sample;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;

import com.demo.plugin.IPluginProxy;

/**
 * Created by guoxiaodong on 2020-02-22 17:22
 */
public abstract class BasePluginActivity extends Activity implements IPluginProxy {
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
}
