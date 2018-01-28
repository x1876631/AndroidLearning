package com.xuye.androidlearning.componentLearning;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;

import com.xuye.androidlearning.R;
import com.xuye.androidlearning.base.CommonTestActivity;

/**
 * Created by xuye on 18/01/28
 * activity生命周期和启动方式学习页
 * 各种情况下的生命周期函数调用：
 * 1、正常启动：onCreate->onStart->onResume
 * 2、跳转到其他页面：onPause->onSaveInstanceState->onStop
 * 3、再跳转回来：onRestart->onStart->onStart
 * 4、正常按back销毁：onPause->onStop->onDestroy
 * 5、横竖屏切换：onPause->->onSaveInstanceState->onStop->onDestroy->onCreate->onStart->onRestoreInstanceState->onResume
 */
public class LaunchLearingActivity extends CommonTestActivity {

    private static final String SAVE_INFO_NAME = "save_info";

    @Override
    public void onCreate(Bundle savedInstanceState) {
        Log.e(tag, "  ");
        Log.e(tag, "----onCreate----");
        super.onCreate(savedInstanceState);
        if (savedInstanceState != null) {
            String str = savedInstanceState.getString(SAVE_INFO_NAME);
            //除了SAVE_INFO_NAME，还有其他view的带id的view的信息，可以通过打印savedInstanceState.toString()看到
            Log.e(tag, "onCreate时有要恢复的数据，内容为：" + str);
        }
        showItemWithCount(new String[]{
                getString(R.string.lauch_activity_1),
                getString(R.string.lauch_activity_single_task)});
    }

    @Override
    protected void onStart() {
        Log.e(tag, "----onStart----");
        super.onStart();
    }

    @Override
    protected void onRestart() {
        Log.e(tag, "----onRestart----");
        super.onRestart();
    }

    @Override
    protected void onResume() {
        Log.e(tag, "----onResume----");
        super.onResume();
    }

    @Override
    protected void onPause() {
        Log.e(tag, "----onPause----");
        super.onPause();
    }

    @Override
    protected void onStop() {
        Log.e(tag, "----onStop----");
        super.onStop();
    }

    @Override
    protected void onDestroy() {
        Log.e(tag, "----onDestroy----");
        super.onDestroy();
    }

    /**
     * 参考：http://blog.csdn.net/shulianghan/article/details/38297083
     * <p>
     * 保存信息用的生命周期函数
     * 【调用时机】当activity容易被销毁时会被调用
     * 1、跳转到另一个activity时
     * 2、按下home键
     * 3、锁屏
     * 4、横竖屏销毁重建
     *
     * @param outState 信息保存对象，可以将想保存的信息保存在这里，等onCreate时再从这个对象里把保存的信息恢复过来
     */
    @Override
    protected void onSaveInstanceState(Bundle outState) {
        Log.e(tag, "----onSaveInstanceState----");
        super.onSaveInstanceState(outState);
        outState.putString(SAVE_INFO_NAME, "onSaveInstanceState");
    }

    /**
     * 也是恢复数据时用，在onStart调用之后被调用
     * 【调用时机】当activity确实被非正常的销毁时，重新创建时会调用，通常见于：1、横竖屏切换 2、内存不足被销毁等情况
     *
     * @param savedInstanceState 保存数据用的对象
     */
    @Override
    protected void onRestoreInstanceState(Bundle savedInstanceState) {
        Log.e(tag, "----onRestoreInstanceState----");
        super.onRestoreInstanceState(savedInstanceState);
        if (savedInstanceState != null) {
            Log.e(tag, "onRestoreInstanceState时有要恢复的数据，内容为：" + savedInstanceState.getString(SAVE_INFO_NAME));
        }
    }

    @Override
    protected void clickButton1() {
        startActivity(new Intent(LaunchLearingActivity.this, LaunchLearingActivity2.class));
    }

    @Override
    protected void clickButton2() {
        super.clickButton2();
        Intent intent = new Intent(LaunchLearingActivity.this, LaunchLearingActivity4.class);
        //注意！单独使用FLAG_ACTIVITY_NEW_TASK没有single task效果，需要和FLAG_ACTIVITY_CLEAR_TOP一起使用，才能实现栈内复用
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        startActivity(intent);

    }
}
