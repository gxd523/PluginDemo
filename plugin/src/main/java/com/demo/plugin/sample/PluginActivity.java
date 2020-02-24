package com.demo.plugin.sample;

import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

import com.demo.plugin.sample.base.BasePluginActivity;

public class PluginActivity extends BasePluginActivity {
    @Override
    public void onProxyCreate(Bundle savedInstanceState) {
        setContentView(R.layout.activity_plugin);

        Log.d("gxd", getClass().getSimpleName() + ".onProxyCreate()....");

        View btn = findViewById(R.id.jump_activity_btn);
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(that, AnotherPluginActivity.class));
            }
        });
        findViewById(R.id.jump_service_btn).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startService(new Intent(that, PluginService.class));
            }
        });

        findViewById(R.id.register_receive_btn).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                IntentFilter intentFilter = new IntentFilter();
                intentFilter.addAction("com.demo.plugin.broadcast");
                registerReceiver(new DynamicPluginReceiver(), intentFilter);
            }
        });

        findViewById(R.id.send_broadcast_btn).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent();
                intent.setAction("com.demo.plugin.broadcast");
                sendBroadcast(intent);
            }
        });
    }
}