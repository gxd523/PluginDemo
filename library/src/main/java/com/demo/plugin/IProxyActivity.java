package com.demo.plugin;

import android.app.Activity;
import android.os.Bundle;
import android.view.MotionEvent;

/**
 * Created by guoxiaodong on 2020-02-21 18:56
 */
public interface IProxyActivity {
    void attachProxy(Activity proxyActivity);

    void onProxyCreate(Bundle savedInstanceState);

    void onProxyStart();

    void onProxyResume();

    void onProxyPause();

    void onProxyStop();

    void onProxyDestroy();

    void onProxySaveInstanceState(Bundle outState);

    boolean onProxyTouchEvent(MotionEvent event);

    void onProxyBackPressed();
}
