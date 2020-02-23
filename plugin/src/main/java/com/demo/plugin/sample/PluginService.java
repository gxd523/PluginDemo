package com.demo.plugin.sample;

import android.util.Log;

import com.demo.plugin.sample.base.BasePluginService;

/**
 * Created by guoxiaodong on 2020-02-23 16:13
 */
public class PluginService extends BasePluginService {
    @Override
    public void onProxyCreate() {
        Log.d("gxd", "PluginService.onProxyCreate()....");
    }
}
