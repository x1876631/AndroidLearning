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
    //让该变量持有外部activity，由于该对象是单例，生命周期和application一样长，会一直持有着外部activity，导致泄露
    private Context mContext;


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
