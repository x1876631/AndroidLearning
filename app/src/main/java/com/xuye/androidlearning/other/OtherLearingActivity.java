package com.xuye.androidlearning.other;

import android.os.Bundle;

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
                getString(R.string.show_phone_tel)
        });
        MemoryLeakTestHelper.getInstance().log(this);
    }

    @Override
    protected void clickButton1() {
        DeviceUtils.getPhoneTelNumber(OtherLearingActivity.this);
    }
}
