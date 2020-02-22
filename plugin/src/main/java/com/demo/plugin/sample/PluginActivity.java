package com.demo.plugin.sample;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

public class PluginActivity extends BasePluginActivity {
    @Override
    public void onProxyCreate(Bundle savedInstanceState) {
        setContentView(R.layout.activity_plugin);

        Log.d("gxd", getClass().getSimpleName() + ".onProxyCreate()....");

        View btn = findViewById(R.id.btn);
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(that, AnotherPluginActivity.class));
            }
        });
    }
}
