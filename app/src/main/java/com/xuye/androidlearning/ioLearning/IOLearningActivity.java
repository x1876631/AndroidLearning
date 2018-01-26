package com.xuye.androidlearning.ioLearning;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.util.Log;

import com.xuye.androidlearning.R;
import com.xuye.androidlearning.base.CommonTestActivity;
import com.xuye.androidlearning.base.util.TimeUtils;

/**
 * Created by xuye on 18/1/26
 * android读写数据学习页
 */
public class IOLearningActivity extends CommonTestActivity {

    private final static String SP_KEY_NAME = "sp_name";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        showItemWithCount(new String[]{
                getString(R.string.io_share_preferences)});
    }

    @Override
    protected void clickButton1() {
        /*
         * 1、sp数据以xml文件形式，保存在data/data/your_package_name/shared_prefs/下面
         * 2、getSharedPreferences参数，name是xml文件名，mode是访问模式，
         * 由于全局(所有app)可见的sp有安全问题，所以基本不建议使用sp多进程间共享数据了，进程间通信建议用contentProvider或者service
         * 3、顺便一提，activity的getSP用的是android.app.ContextImpl里的getSP
         * 4、进程不安全，线程安全，why？创建sp时有锁，多次获取的是同一个实例，线程安全
         */
        SharedPreferences sp1 = getSharedPreferences("io_learning", MODE_PRIVATE);
        //apply异步写入数据
        sp1.edit().putString(SP_KEY_NAME, "sp1").apply();
        setButton1Content(sp1.getString(SP_KEY_NAME, "null"));


        //getDefault其实就是对getsp封装了一下，name是"包名+_preferences"
        SharedPreferences sp2 = PreferenceManager.getDefaultSharedPreferences(this);
        //同步写入数据，写入时会阻塞线程，数据比较大时可能会ANR
        StringBuilder sb = new StringBuilder("");
        long beginTime = System.currentTimeMillis();
        for (int i = 0; i < 1000000; i++) {
            sb.append("12345");
        }
        Log.e(tag, "生成对象 耗时: " + TimeUtils.getTimeConsuming(beginTime));//0.88s
        beginTime = System.currentTimeMillis();
        sp2.edit().putString(SP_KEY_NAME, sb.toString()).commit();
        Log.e(tag, "执行commit 耗时: " + TimeUtils.getTimeConsuming(beginTime));//0.79s

        //再测一下apply操作
        setSpApply(sp1, sb.toString());//0.17s
    }


    private void setSpApply(SharedPreferences sp, String content) {
        long beginTime = System.currentTimeMillis();
        sp.edit().putString("sp_content", content).apply();
        Log.e(tag, "执行apply 耗时: " + TimeUtils.getTimeConsuming(beginTime));
    }
}
