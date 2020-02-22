package com.demo.sample;

import android.content.Context;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.res.AssetManager;
import android.content.res.Resources;

import java.io.File;
import java.lang.reflect.Method;

import dalvik.system.DexClassLoader;

/**
 * Created by guoxiaodong on 2020-02-22 14:14
 */
public class PluginManager {
    private static final PluginManager instance = new PluginManager();
    private Resources resources;
    private DexClassLoader dexClassLoader;
    private PackageInfo packageInfo;

    public static PluginManager getInstance() {
        return instance;
    }

    public void loadPlugin(Context context) throws Exception {
        File pluginDir = context.getDir("plugin", Context.MODE_PRIVATE);
        String pluginApkAbsolutePath = new File(pluginDir, "plugin.apk").getAbsolutePath();

        PackageManager packageManager = context.getPackageManager();
        packageInfo = packageManager.getPackageArchiveInfo(pluginApkAbsolutePath, PackageManager.GET_ACTIVITIES);

        String dexDirAbsolutePath = context.getDir("dex", Context.MODE_PRIVATE).getAbsolutePath();
        dexClassLoader = new DexClassLoader(pluginApkAbsolutePath, dexDirAbsolutePath, null, context.getClassLoader());

        AssetManager assetManager = AssetManager.class.newInstance();
        Method addAssetPath = AssetManager.class.getMethod("addAssetPath", String.class);
        addAssetPath.invoke(assetManager, pluginApkAbsolutePath);
        resources = new Resources(
                assetManager,
                context.getResources().getDisplayMetrics(),
                context.getResources().getConfiguration()
        );
    }

    public Resources getResources() {
        return resources;
    }

    public DexClassLoader getDexClassLoader() {
        return dexClassLoader;
    }

    public PackageInfo getPackageInfo() {
        return packageInfo;
    }
}
