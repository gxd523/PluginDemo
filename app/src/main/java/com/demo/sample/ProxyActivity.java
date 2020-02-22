package com.demo.sample;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.res.Resources;
import android.os.Bundle;
import android.view.MotionEvent;

import com.demo.plugin.IPluginProxy;

public class ProxyActivity extends Activity {
    private IPluginProxy pluginActivity;

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
            Class pluginActivityClass = getClassLoader().loadClass(className);
            pluginActivity = (IPluginProxy) pluginActivityClass.newInstance();
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
