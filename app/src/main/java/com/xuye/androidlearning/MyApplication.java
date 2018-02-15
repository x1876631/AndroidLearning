package com.xuye.androidlearning;

import android.app.Application;
import android.content.Context;

/**
 * Created by xuye on 17/10/23
 * 当前项目自定义application
 */
public class MyApplication extends Application {

    private static Context sInstance;

    @Override
    public void onCreate() {
        super.onCreate();

        sInstance = this;
//        LeakCanary.install(this);
        try {
            Thread.sleep(500);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    public static Context getInstance() {
        return sInstance;
    }
}
