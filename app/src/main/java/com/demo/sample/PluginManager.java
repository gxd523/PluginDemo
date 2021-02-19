package com.demo.sample;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.IntentFilter;
import android.content.pm.ActivityInfo;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.res.AssetManager;
import android.content.res.Resources;

import java.io.File;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.List;

import dalvik.system.PathClassLoader;

/**
 * Created by guoxiaodong on 2020-02-22 14:14
 */
public class PluginManager {
    public static final String PLUGIN_APK = "plugin-debug.apk";
    private static final PluginManager instance = new PluginManager();
    private Resources resources;
    private PathClassLoader dexClassLoader;
    private PackageInfo packageInfo;

    public static PluginManager getInstance() {
        return instance;
    }

    /**
     * 插桩式插件的核心
     */
    public void loadPlugin(Context context) throws Exception {
        File pluginDir = context.getDir("plugin", Context.MODE_PRIVATE);
        String pluginApkAbsolutePath = new File(pluginDir, PLUGIN_APK).getAbsolutePath();

        PackageManager packageManager = context.getPackageManager();
        packageInfo = packageManager.getPackageArchiveInfo(pluginApkAbsolutePath, PackageManager.GET_ACTIVITIES);

        dexClassLoader = new PathClassLoader(pluginApkAbsolutePath, null, context.getClassLoader());

        AssetManager assetManager = AssetManager.class.newInstance();
        Method addAssetPath = AssetManager.class.getMethod("addAssetPath", String.class);
        addAssetPath.invoke(assetManager, pluginApkAbsolutePath);
        resources = new Resources(
                assetManager,
                context.getResources().getDisplayMetrics(),
                context.getResources().getConfiguration()
        );

        parseBroadcastReceivers(context, pluginApkAbsolutePath);
    }

    private void parseBroadcastReceivers(Context context, String pluginApkAbsolutePath) throws Exception {
        // 获取PackageParser
        Class packageParserClass = Class.forName("android.content.pm.PackageParser");
        // 创建PackageParser对象
        Object packageParser = packageParserClass.newInstance();
        // 获取PackageParser中的parsePackage（）
        Method parsePackageMethod = packageParserClass.getDeclaredMethod("parsePackage", File.class, int.class);
        // 调用parsePackage（） 返回Package
        Object packageObj = parsePackageMethod.invoke(packageParser, new File(pluginApkAbsolutePath), PackageManager.GET_ACTIVITIES);

        // 通过Package 来获取这个对象中的成员变量（receivers）==== 》receivers 的集合
        Field receiversField = packageObj.getClass().getDeclaredField("receivers");
        List receiverList = (List) receiversField.get(packageObj);


        // 获取Component 为的是获取IntentFilter集合
        Class componentClass = Class.forName("android.content.pm.PackageParser$Component");
        Field intentsField = componentClass.getDeclaredField("intents");

        // 调用generateActivityInfo 方法, 把PackageParser.Activity 转换成
        Class<?> packageParser$ActivityClass = Class.forName("android.content.pm.PackageParser$Activity");
        // generateActivityInfo方法
        Class<?> packageUserStateClass = Class.forName("android.content.pm.PackageUserState");
        Object packageUserState = packageUserStateClass.newInstance();
        Method generateReceiverInfoMethod = packageParserClass.getDeclaredMethod(
                "generateActivityInfo", packageParser$ActivityClass, int.class, packageUserStateClass, int.class
        );

        //反射获取UserID
        Class userHandleClass = Class.forName("android.os.UserHandle");
        Method getCallingUserIdMethod = userHandleClass.getDeclaredMethod("getCallingUserId");
        int userId = (int) getCallingUserIdMethod.invoke(null);

        for (Object receiver : receiverList) {
            ActivityInfo activityInfo = (ActivityInfo) generateReceiverInfoMethod.invoke(
                    packageParser, receiver, 0, packageUserState, userId
            );
            List<? extends IntentFilter> intentFilterList = (List<? extends IntentFilter>) intentsField.get(receiver);

            BroadcastReceiver broadcastReceiver = (BroadcastReceiver) dexClassLoader.loadClass(activityInfo.name).newInstance();
            for (IntentFilter intentFilter : intentFilterList) {
                context.registerReceiver(broadcastReceiver, intentFilter);
            }
        }
    }

    public Resources getResources() {
        return resources;
    }

    public PathClassLoader getDexClassLoader() {
        return dexClassLoader;
    }

    public PackageInfo getPackageInfo() {
        return packageInfo;
    }
}
