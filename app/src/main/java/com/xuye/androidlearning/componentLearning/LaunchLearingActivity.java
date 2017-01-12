package com.xuye.androidlearning.componentLearning;

import android.content.Intent;
import android.os.Bundle;

import com.xuye.androidlearning.R;
import com.xuye.androidlearning.base.CommonTestActivity;

/**
 * Created by xuye on 16/12/27
 * activity启动方式学习页
 */
public class LaunchLearingActivity extends CommonTestActivity {

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        showItemWithCount(new String[]{getString(R.string.lauch_activity_1)});
    }

    @Override
    protected void clickButton1() {
        startActivity(new Intent(LaunchLearingActivity.this, LaunchLearingActivity2.class));
    }

}