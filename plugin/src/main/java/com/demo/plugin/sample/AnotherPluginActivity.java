package com.demo.plugin.sample;

import android.os.Bundle;

/**
 * Created by guoxiaodong on 2020-02-22 17:21
 */
public class AnotherPluginActivity extends BasePluginActivity {
    @Override
    public void onProxyCreate(Bundle savedInstanceState) {
        super.onProxyCreate(savedInstanceState);
        setContentView(R.layout.activity_another_plugin);
    }
}
