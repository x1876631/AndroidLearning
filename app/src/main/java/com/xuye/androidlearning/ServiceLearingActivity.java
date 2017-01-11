package com.xuye.androidlearning;

import android.app.Service;
import android.content.ComponentName;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.Bundle;
import android.os.IBinder;
import android.util.Log;

import util.LogUtil;

/**
 * Created by xuye on 17/01/11
 * service学习页
 * 不用thread的原因：
 * 1、thread和activity的声明周期无关联，activity销毁后，无法再拿到thread的引用
 * 2、多个activity不能共享一个thread，但同一service可以被多个activity控制
 */
public class ServiceLearingActivity extends CommonTestActivity {

    private static final String tag = "ServiceLearingActivity";
    private TestService.TestServiceBinder mBinder;

    // 与 Service 建立连接
    private ServiceConnection mConnection = new ServiceConnection() {
        // 当 Activity 与 Service 连接时回调该方法
        @Override
        public void onServiceConnected(ComponentName name, IBinder service) {
            // 获取返回的 Service 数据对象
            mBinder = (TestService.TestServiceBinder) service;
            Log.e(tag, "test service is bind to " + tag);
        }

        // 当 Activity 与 Service 断开时回调该方法
        @Override
        public void onServiceDisconnected(ComponentName name) {
            Log.e(tag, "test service is unbind to " + tag);
        }
    };

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        showItemWithCount(new String[]{
                getString(R.string.start_service), getString(R.string.bind_service),
                getString(R.string.unbind_service), getString(R.string.stop_service)
        });
        LogUtil.logCurrentThreadInfo(tag, Thread.currentThread());
    }

    @Override
    protected void clickButton1() {
        //start的service会一直存在后台，与activity的声明周期无关，只有在stopService后才停止
        startService(new Intent(this, TestService.class));
    }

    @Override
    protected void clickButton2() {
        bindService(new Intent(this, TestService.class), mConnection, Service.BIND_AUTO_CREATE);
    }

    @Override
    protected void clickButton3() {
        unbindService(mConnection);
    }

    @Override
    protected void clickButton4() {
        stopService(new Intent(this, TestService.class));
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        clickButton4();
    }
}
