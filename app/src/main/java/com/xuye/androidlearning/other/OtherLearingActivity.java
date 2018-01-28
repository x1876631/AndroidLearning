package com.xuye.androidlearning.other;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Looper;
import android.widget.Toast;

import com.xuye.androidlearning.R;
import com.xuye.androidlearning.base.CommonTestActivity;

/**
 * Created by xuye on 17/02/21
 * 非系统的知识学习
 */
public class OtherLearingActivity extends CommonTestActivity {

    private static final String tag = "OtherLearingActivity";

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        showItemWithCount(new String[]{
                getString(R.string.cautch_exception), getString(R.string.no_ui_show_toast),
                getString(R.string.jump_test_span)
        });
    }

    @Override
    protected void clickButton1() {
//        DeviceUtils.getPhoneTelNumber(OtherLearingActivity.this);
        throw new RuntimeException("exception in main");
    }

    @Override
    protected void clickButton2() {
        super.clickButton2();
        new Thread(new MyTestRunnable(getApplicationContext())).start();
    }

    /**
     * 静态内部类不会持有外部类引用，不会有内存泄露
     */
    private static class MyTestRunnable implements Runnable {

        private Context mContext;

        public MyTestRunnable(Context context) {
            mContext = context.getApplicationContext();
        }

        @Override
        public void run() {
            Looper.prepare();
            Toast.makeText(mContext, "非UI线程，测试application弹toast", Toast.LENGTH_SHORT).show();
            Looper.loop();
        }
    }

    @Override
    protected void clickButton3() {
        super.clickButton3();
        startActivity(new Intent(this, TestSpannableActivity.class));
    }
}
