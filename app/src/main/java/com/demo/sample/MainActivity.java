package com.demo.sample;

import android.app.Activity;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;

public class MainActivity extends Activity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        registerReceiver(new BroadcastReceiver() {
            @Override
            public void onReceive(Context context, Intent intent) {
                Log.d("gxd", "MainActivity.onReceive-->" + intent.getAction());
            }
        }, new IntentFilter("com.demo.plugin.static.broadcast"));
    }

    public void onLoadPluginClick(View view) {
        copyPluginApk(pluginApkDestPath -> {
            if (pluginApkDestPath.exists()) {
                Log.d("gxd", "拷贝成功!..." + pluginApkDestPath.getAbsolutePath());
                try {
                    PluginManager.getInstance().loadPlugin(MainActivity.this);
                } catch (Exception e) {
                    e.printStackTrace();
                    Log.d("gxd", "插件加载失败!");
                }
            }
        });
    }

    private void copyPluginApk(Func1<File> func1) {
        File pluginApkDestPath = new File(
                getDir("plugin", Context.MODE_PRIVATE), PluginManager.PLUGIN_APK
        ).getAbsoluteFile();
        if (pluginApkDestPath.exists()) {
            pluginApkDestPath.delete();
            Log.d("gxd", "删除data目录下已存在的插件apk");
        }
        FileInputStream inputStream = null;
        FileOutputStream outputStream = null;
        try {
            File pluginApkSrcPath = new File(getExternalFilesDir(null), PluginManager.PLUGIN_APK);
            if (!pluginApkSrcPath.exists()) {
                Log.d("gxd", "外部存储目录没有插件apk..." + pluginApkSrcPath);
                return;
            }

            inputStream = new FileInputStream(pluginApkSrcPath);
            outputStream = new FileOutputStream(pluginApkDestPath);
            int length;
            byte[] buffer = new byte[1024];
            while ((length = inputStream.read(buffer)) != -1) {
                outputStream.write(buffer, 0, length);
            }
        } catch (Exception e) {
            e.printStackTrace();
            Log.d("gxd", "拷贝插件失败!");
        } finally {
            try {
                if (inputStream != null) {
                    inputStream.close();
                }
                if (outputStream != null) {
                    outputStream.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
            func1.call(pluginApkDestPath);
        }
    }

    public void onLaunchPluginClick(View view) {
        ActivityInfo[] activities = PluginManager.getInstance().getPackageInfo().activities;
        String className = activities[0].name;
        Log.d("gxd", "启动插件Activity..." + className);
        ProxyActivity.startActivity(this, className);
    }

    public void onSendBroadcastClick(View view) {
        sendBroadcast(new Intent("com.demo.broadcast"));
    }
}
