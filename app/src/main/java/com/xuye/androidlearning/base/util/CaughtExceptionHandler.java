package com.xuye.androidlearning.base.util;

import android.util.Log;

/**
 * Created by xuye on 18/1/29
 * 全局捕获异常的处理类
 */
public class CaughtExceptionHandler implements Thread.UncaughtExceptionHandler {

    @Override
    public void uncaughtException(Thread thread, Throwable ex) {
        Log.e("xuye", "捕获了异常：" + ex.getMessage() + ", 保存异常数据到本地，并自动退出app");
        try {
            Thread.sleep(1500);
            android.os.Process.killProcess(android.os.Process.myPid());
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
