package com.xuye.androidlearning;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.TextView;

/**
 * Created by xuye on 17/01/06
 * activity启动方式学习测试页3
 */
public class LaunchLearingActivity3 extends CommonTestActivity {

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        showItemWithCount(new String[]{getString(R.string.lauch_activity_3)});
    }

    @Override
    protected void clickButton1() {
        startActivity(new Intent(LaunchLearingActivity3.this, LaunchLearingActivity4.class));
    }
}
