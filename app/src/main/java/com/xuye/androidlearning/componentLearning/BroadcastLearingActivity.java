package com.xuye.androidlearning.componentLearning;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.widget.Toast;

import com.xuye.androidlearning.R;
import com.xuye.androidlearning.base.CommonTestActivity;
import com.xuye.androidlearning.base.MyIntentAction;

/**
 * Created by xuye on 17/01/12
 * broadcast学习页
 */
public class BroadcastLearingActivity extends CommonTestActivity {

    private static final String tag = "BroadcastLearing";
    private MyDynamicReceiver mReceiver;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        showItemWithCount(new String[]{
                getString(R.string.send_dynamic_broadcast), getString(R.string.send_static_broadcast)
        });
        //动态注册广播
        mReceiver = new MyDynamicReceiver();
        IntentFilter filter = new IntentFilter();
        filter.addAction(MyIntentAction.ACTION_DYNAMIC_BROADCAST_LEARNING);
        registerReceiver(mReceiver, filter);
    }

    @Override
    protected void clickButton1() {
        //发广播
        Intent intent = new Intent();
        intent.setAction(MyIntentAction.ACTION_DYNAMIC_BROADCAST_LEARNING);
        sendBroadcast(intent);
    }

    @Override
    protected void clickButton2() {
        super.clickButton2();
        Intent intent = new Intent();
        intent.setAction(MyIntentAction.ACTION_STATIC_BROADCAST_LEARNING);
        sendBroadcast(intent);
    }

    @Override
    protected void onDestroy() {
        //动态广播记得要解注册
        unregisterReceiver(mReceiver);
        super.onDestroy();
    }

    /**
     * 动态注册的广播的接收者
     * 内部类必须使用公开的静态内部类，否则会报异常Unable to instantiate receiver
     */
    public static class MyDynamicReceiver extends BroadcastReceiver {

        @Override
        public void onReceive(Context context, Intent intent) {
            String text = getClass().getSimpleName();
            Toast.makeText(context, text + context.getString(R.string.receive_broadcast),
                    Toast.LENGTH_SHORT).show();
        }
    }

    /**
     * 静态注册的广播接收者
     */
    public static class MyStaticReceiver extends MyDynamicReceiver {

    }


}
