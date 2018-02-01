package com.xuye.androidlearning.webLearning;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;

import com.xuye.androidlearning.R;
import com.xuye.androidlearning.base.CommonTestActivity;

/**
 * Created by xuye on 18/1/30
 * web学习目录页
 */
public class WebMainActivity extends CommonTestActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        showItemWithCount(new String[]{
                getString(R.string.web_scheme), getString(R.string.web_view)
        });
    }

    @Override
    protected void clickButton1() {
        goToSchemeActivity();
    }

    private void goToSchemeActivity() {
        String url = "learning://xuye/path?id=1";
        Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(url));
        startActivity(intent);
    }

    @Override
    protected void clickButton2() {
        super.clickButton2();
        startActivity(new Intent(this, WebViewLearningActivity.class));
    }


}
