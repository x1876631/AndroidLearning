package com.xuye.androidlearning;

import android.app.Application;

/**
 * Created by xuye on 17/10/23
 * 当前项目自定义application
 */
public class MyApplication extends Application {
    @Override
    public void onCreate() {
        super.onCreate();
//        LeakCanary.install(this);
        try {
            Thread.sleep(500);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
