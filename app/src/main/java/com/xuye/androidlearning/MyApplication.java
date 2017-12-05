package com.xuye.androidlearning;

import android.app.Application;

import com.squareup.leakcanary.LeakCanary;

/**
 * Created by xuye on 17/10/23
 * 当前项目自定义application
 */
public class MyApplication extends Application {
    @Override
    public void onCreate() {
        super.onCreate();
        LeakCanary.install(this);
        try {
            Thread.sleep(1500);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
