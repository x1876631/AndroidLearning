package com.xuye.androidlearning.webviewLearning;

import android.net.Uri;
import android.os.Bundle;
import android.widget.Toast;

import com.xuye.androidlearning.base.CommonTestActivity;

/**
 * Created by xuye on 18/1/28
 * 可以被scheme吊起来的页面，如果在当期应用中唤起其他app，可以屏蔽scheme协议，貌似微信就屏蔽了，只有配置了白名单的才能在微信中唤起其他app
 */

public class SchemeLearningActivity extends CommonTestActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //读取scheme协议信息，展示数据
        Uri uri = getIntent().getData();

        if (uri.getScheme() != null) {
            Toast.makeText(getApplicationContext(), "本页面由scheme唤起", Toast.LENGTH_SHORT).show();
        }
        String scheme = "scheme协议名：" + uri.getScheme();
        showItemWithCount(new String[]{scheme});
    }

    @Override
    protected void clickButton1() {
    }
}
