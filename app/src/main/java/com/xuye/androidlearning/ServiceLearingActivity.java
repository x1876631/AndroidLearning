package com.xuye.androidlearning;

import android.content.Intent;
import android.os.Bundle;

/**
 * Created by xuye on 17/01/11
 * service学习页
 */
public class ServiceLearingActivity extends CommonTestActivity {

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        showItemWithCount(new String[]{
                getString(R.string.start_service), getString(R.string.bind_service),
                getString(R.string.unbind_service), getString(R.string.stop_service)
        });
    }

    @Override
    protected void clickButton1() {
        startService(new Intent(this, TestService.class));
    }

    @Override
    protected void clickButton2() {

    }

    @Override
    protected void clickButton3() {

    }

    @Override
    protected void clickButton4() {
        stopService(new Intent(this, TestService.class));
    }

}
