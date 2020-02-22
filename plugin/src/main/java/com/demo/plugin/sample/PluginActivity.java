package com.demo.plugin.sample;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Toast;

import com.demo.plugin.IPluginProxy;

public class PluginActivity extends Activity implements IPluginProxy {
    private Activity that;

    @Override
    public void attachProxy(Activity proxyActivity) {
        that = proxyActivity;
    }

    @Override
    public void onProxyCreate(Bundle savedInstanceState) {
        setContentView(R.layout.activity_plugin);

        Log.d("gxd", "PluginActivity.onProxyCreate-->");

        View titleTv = findViewById(R.id.title_tv);
        titleTv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(v.getContext(), "PluginActivity is clicked!", Toast.LENGTH_SHORT).show();
            }
        });
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
        intent1.putExtra("className", intent.getClass());
        that.startActivity(intent1);
    }
}
