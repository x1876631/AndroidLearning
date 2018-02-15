package com.xuye.androidlearning.webLearning;

import android.text.TextUtils;
import android.util.Log;
import android.webkit.JavascriptInterface;
import android.widget.Toast;

import com.xuye.androidlearning.MyApplication;
import com.xuye.androidlearning.base.Constant;

/**
 * Created by xuye on 18/2/8
 * andorid和js映射管理类
 */
public class AndroidToJs {

    @JavascriptInterface
    public void invoke() {
        Log.e(Constant.tag, "js调用了native!");
    }

    @JavascriptInterface
    public void invoke(String content) {
        if (TextUtils.isEmpty(content)) {
            content = "js调用了native!";
        }
        Log.e(Constant.tag, "js call native, content: " + content);
        Toast.makeText(MyApplication.getInstance(), content, Toast.LENGTH_SHORT).show();
    }
}
