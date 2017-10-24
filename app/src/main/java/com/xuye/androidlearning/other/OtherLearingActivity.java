package com.xuye.androidlearning.other;

import android.os.Bundle;
import android.os.Looper;
import android.widget.Toast;

import com.xuye.androidlearning.R;
import com.xuye.androidlearning.base.CommonTestActivity;
import com.xuye.androidlearning.base.DeviceUtils;

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
                getString(R.string.show_phone_tel), "非UI线程弹toast", "内存泄露测试"
        });


        Toast.makeText(getApplicationContext(), "UI线程，测试application弹toast", Toast.LENGTH_SHORT).show();
    }

    @Override
    protected void clickButton1() {
        DeviceUtils.getPhoneTelNumber(OtherLearingActivity.this);
    }

    @Override
    protected void clickButton2() {
        super.clickButton2();
        new Thread(new Runnable() {
            @Override
            public void run() {
                Looper.prepare();
                Toast.makeText(getApplicationContext(), "非UI线程，测试application弹toast", Toast.LENGTH_SHORT).show();
                Looper.loop();
            }
        }).start();
    }

    @Override
    protected void clickButton3() {
        super.clickButton3();
        MemoryLeakTestHelper.getInstance().log(this);
    }
}
