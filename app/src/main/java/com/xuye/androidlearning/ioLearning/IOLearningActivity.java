package com.xuye.androidlearning.ioLearning;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.util.Log;
import android.widget.Toast;

import com.xuye.androidlearning.R;
import com.xuye.androidlearning.base.CommonTestActivity;
import com.xuye.androidlearning.base.util.FileUtils;
import com.xuye.androidlearning.base.util.TimeUtils;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

/**
 * Created by xuye on 18/1/26
 * android读写数据学习页
 */
public class IOLearningActivity extends CommonTestActivity {

    private final static String SP_KEY_FOR_NAME = "name";
    private final static String SP_KEY_FOR_CONTENT = "content";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        showItemWithCount(new String[]{
                getString(R.string.io_share_preferences), getString(R.string.io_serializable_and_parcelable)});
    }

    /**
     * sp学习参考：http://www.qingpingshan.com/rjbc/az/303526.html
     */
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
        sp1.edit().putString(SP_KEY_FOR_NAME, "sp1").apply();
        setButton1Content(sp1.getString(SP_KEY_FOR_NAME, "null"));


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
        sp2.edit().putString(SP_KEY_FOR_NAME, sb.toString()).commit();
        Log.e(tag, "执行commit 耗时: " + TimeUtils.getTimeConsuming(beginTime));//0.79s

        //再测一下apply操作
        setSpApply(sp1, sb.toString());//0.17s
    }


    private void setSpApply(SharedPreferences sp, String content) {
        long beginTime = System.currentTimeMillis();
        sp.edit().putString(SP_KEY_FOR_CONTENT, content).apply();
        Log.e(tag, "执行apply 耗时: " + TimeUtils.getTimeConsuming(beginTime));
    }

    @Override
    protected void clickButton2() {
        super.clickButton2();
        //参考：http://blog.csdn.net/u014606081/article/details/71137243

        /*
         * 将对象写入文件，写入文件内容是这样的：
         * ??sr6com.xuye.androidlearning.ioLearning.SerializableObject?? 9ԇ??LfieldtLjava/lang/String;xpt123
         */
        SerializableObject so = new SerializableObject("123", 456);
        //序列化前：SerializableObject{field='123', transient_field=456}
        Log.e("xuye", "序列化前，对象的内容：" + so.toString());
        try {
            ObjectOutputStream out = new ObjectOutputStream(
                    new FileOutputStream(new File(FileUtils.getAppDefaultFilePath(), "io_serializable.txt")));
            out.writeObject(so);
            out.close();
            Toast.makeText(getApplicationContext(), "数据已序列化到本地", Toast.LENGTH_SHORT).show();
        } catch (IOException e) {
            Log.e("xuye", "io异常");
            e.printStackTrace();
        }

        SerializableObject so2 = null;
        try {
            ObjectInputStream in = new ObjectInputStream(
                    new FileInputStream(new File(FileUtils.getAppDefaultFilePath(), "io_serializable.txt")));
            so2 = (SerializableObject) in.readObject();
            in.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
        if (so2 != null) {
            ////序列化后：SerializableObject{field='123', transient_field=0}
            Log.e("xuye", "序列化后，对象的内容：" + so2.toString());
        }
    }
}
