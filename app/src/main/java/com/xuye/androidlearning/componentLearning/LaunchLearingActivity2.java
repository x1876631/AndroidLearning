package com.xuye.androidlearning.componentLearning;

import android.content.Intent;
import android.os.Bundle;

import com.xuye.androidlearning.R;
import com.xuye.androidlearning.base.CommonTestActivity;

/**
 * Created by xuye on 17/01/06
 * activity启动方式学习测试页2
 */
public class LaunchLearingActivity2 extends CommonTestActivity {

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        showItemWithCount(new String[]{getString(R.string.lauch_activity_2),
                getString(R.string.lauch_activity_to_4)});
    }

    @Override
    protected void clickButton1() {
        startActivity(new Intent(LaunchLearingActivity2.this, LaunchLearingActivity3.class));
    }

    @Override
    protected void clickButton2() {
        Intent intent = new Intent(this, LaunchLearingActivity4.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        startActivity(intent);
    }
}
