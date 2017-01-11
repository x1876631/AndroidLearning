package com.xuye.androidlearning;

import android.app.Service;
import android.content.Intent;
import android.os.Binder;
import android.os.Handler;
import android.os.IBinder;
import android.os.Message;
import android.support.annotation.Nullable;
import android.util.Log;

import util.LogUtil;

/**
 * Created by xuye on 17/1/11
 * 测试用service
 */
public class TestService extends Service {

    private static final String tag = "TestService";
    private boolean isRun = false;//控制runnable执行

    private Runnable mRunnable = new Runnable() {
        @Override
        public void run() {
            // TODO: 17/1/11  为什么onDestroy了以后Runnable还在工作？Runnable运行机制？回家看一下
            LogUtil.logCurrentThreadInfo(tag, Thread.currentThread());
            if (isRun) {
                mHandler.sendEmptyMessage(0);
            }
        }
    };

    private Handler mHandler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch (msg.what) {
                case 0:
                    isRun = true;
                    mHandler.postDelayed(mRunnable, 1000);
                    break;
            }
        }
    };

    @Override
    public void onCreate() {
        super.onCreate();
        Log.e(tag, "--------test service onCreate--------");
        mHandler.sendEmptyMessage(0);
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        Log.e(tag, "--------test service onStartCommand--------");
        return super.onStartCommand(intent, flags, startId);
    }

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        Log.e(tag, "--------test service onBind--------");
        return null;
    }

    @Override
    public boolean onUnbind(Intent intent) {
        Log.e(tag, "--------test service onUnbind--------");
        return super.onUnbind(intent);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        Log.e(tag, "--------test service onDestroy--------");
        isRun = false;
    }

    /**
     * service数据类
     */
    public class TestServiceBinder extends Binder{
    }
}
