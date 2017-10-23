package com.xuye.androidlearning.other;

import android.content.Context;
import android.util.Log;

/**
 * Created by xuye on 17/10/23
 * 内存泄露辅助类，用于测试内存泄露
 */
public class MemoryLeakTestHelper {
    private static final String tag = "MemoryLeakTestHelper";

    private static MemoryLeakTestHelper sInstance;
    private Context mContext;//让该单例持有外部activity，基本就泄露了

    private MemoryLeakTestHelper() {

    }

    public static MemoryLeakTestHelper getInstance() {
        if (sInstance == null) {
            sInstance = new MemoryLeakTestHelper();
        }
        return sInstance;
    }

    public void log(Context context) {
        mContext = context;
        Log.e(tag, "----单例持有外部activity----");
    }
}
