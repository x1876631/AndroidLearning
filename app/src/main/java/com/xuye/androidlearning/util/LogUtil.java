package com.xuye.androidlearning.util;

import android.util.Log;

/**
 * Created by xuye on 17/1/11
 * 日志工具类
 */

public class LogUtil {

    /**
     * 打印当前线程信息
     *
     * @param tag    标签
     * @param thread 当前线程
     */
    public static void logCurrentThreadInfo(String tag, Thread thread) {
        Log.e(tag, "current thread id : " +
                thread.getId() + " , name: " + thread.getName() + "");
    }
}
