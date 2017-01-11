package com.xuye.androidlearning;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.support.annotation.Nullable;
import android.util.Log;

/**
 * Created by xuye on 17/1/11
 * 测试用service
 */
public class TestService extends Service {

    private static final String tag = "TestService";

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        Log.e(tag, "--------test service onBind--------");
        return null;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        Log.e(tag, "--------test service onCreate--------");
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        Log.e(tag, "--------test service onStartCommand--------");
        return super.onStartCommand(intent, flags, startId);
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
    }
}
