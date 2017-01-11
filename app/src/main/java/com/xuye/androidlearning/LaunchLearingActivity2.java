package com.xuye.androidlearning;

import android.content.Intent;
import android.os.Bundle;

/**
 * Created by xuye on 17/01/06
 * activity启动方式学习测试页2
 */
public class LaunchLearingActivity2 extends CommonTestActivity {

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        showItemWithCount(new String[]{getString(R.string.lauch_activity_2)});
    }

    @Override
    protected void clickButton1() {
        startActivity(new Intent(LaunchLearingActivity2.this, LaunchLearingActivity3.class));
    }
}
