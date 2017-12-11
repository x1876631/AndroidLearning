package com.xuye.androidlearning;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import com.xuye.androidlearning.animationLearning.AnimationLearningActivity;
import com.xuye.androidlearning.componentLearning.BroadcastLearingActivity;
import com.xuye.androidlearning.componentLearning.LaunchLearingActivity;
import com.xuye.androidlearning.componentLearning.ServiceLearingActivity;
import com.xuye.androidlearning.fragmentLearning.FragmentLearningActivity;
import com.xuye.androidlearning.handleLearning.HandlerLearingActivity;
import com.xuye.androidlearning.imageloader.ImageListActivity;
import com.xuye.androidlearning.memoryLeak.MemoryLeakLearingActivity;
import com.xuye.androidlearning.other.OtherLearingActivity;
import com.xuye.androidlearning.threadLearning.ThreadLearingActivity;
import com.xuye.androidlearning.viewLearning.ViewLearningActivity;
import com.xuye.androidlearning.windowLearning.WindowLearningActivity;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity implements AdapterView.OnItemClickListener {

    public static final String tag = "MainActivity";

    //列表项内容文案
    private ArrayList<String> mData;

    //列表项内容文案资源id，如果想增加列表项，每次需要加文案和点击事件
    private int[] mDataResourceArray = {
            R.string.main_jump_view_learning, R.string.main_jump_fragment_learning, R.string.main_jump_lauch_learning,
            R.string.main_jump_service_learning, R.string.main_jump_broadcast_learning,
            R.string.main_jump_handler_learning, R.string.main_jump_thread_learning,
            R.string.main_jump_window_learning, R.string.main_jump_imageloader_learning,
            R.string.main_jump_animation_learning, R.string.main_jump_memory_leak_learning,
            R.string.main_jump_other_learning,
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        setTheme(R.style.AppTheme);
        super.onCreate(savedInstanceState);
        //这里点击R.bool.on_test时会展示多个选项，实际生产apk时用的是主app库里on_test_config.xml的参数。
        //至于为什么没用base的on_test参数，则与打包时指定的资源编译顺序有关
        //关于资源合并覆盖的参考：http://leenjewel.github.io/blog/2015/12/02/ye-shuo-android-apk-da-bao/
        if (getResources().getBoolean(R.bool.on_test)) {
            //测试读取on_test了哪个on_test参数
            setTitle("测试版");
        } else {
            setTitle("正式版");
        }
        setContentView(R.layout.activity_main);
        initData();
        initView();
    }

    private void initData() {
        mData = new ArrayList<>();
        for (int id : mDataResourceArray) {
            mData.add(getString(id));
        }

    }

    private void initView() {
        ListView listView = (ListView) findViewById(R.id.main_activity_content_list);
        if (listView != null) {

//            ArrayAdapter<String> adapter =
//                    new ArrayAdapter<String>(getApplicationContext(), R.layout.activity_common_click,
//                            R.id.common_click_button, mData);
//            listView.setAdapter(adapter);
            //用上面的arrayAdapter也可以，就不用自己去实现adapter里的getView()了
            listView.setAdapter(new MainListAdapter(getApplicationContext(), mData));
            listView.setOnItemClickListener(this);
        }
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        String content = (String) parent.getItemAtPosition(position);
        if (getString(mDataResourceArray[0]).equals(content)) {
            startActivity(new Intent(MainActivity.this, ViewLearningActivity.class));
        } else if (getString(mDataResourceArray[1]).equals(content)) {
            startActivity(new Intent(MainActivity.this, FragmentLearningActivity.class));
        } else if (getString(mDataResourceArray[2]).equals(content)) {
            startActivity(new Intent(MainActivity.this, LaunchLearingActivity.class));
        } else if (getString(mDataResourceArray[3]).equals(content)) {
            startActivity(new Intent(MainActivity.this, ServiceLearingActivity.class));
        } else if (getString(mDataResourceArray[4]).equals(content)) {
            startActivity(new Intent(MainActivity.this, BroadcastLearingActivity.class));
        } else if (getString(mDataResourceArray[5]).equals(content)) {
            startActivity(new Intent(MainActivity.this, HandlerLearingActivity.class));
        } else if (getString(mDataResourceArray[6]).equals(content)) {
            startActivity(new Intent(MainActivity.this, ThreadLearingActivity.class));
        } else if (getString(mDataResourceArray[7]).equals(content)) {
            startActivity(new Intent(MainActivity.this, WindowLearningActivity.class));
        } else if (getString(mDataResourceArray[8]).equals(content)) {
            startActivity(new Intent(MainActivity.this, ImageListActivity.class));
        } else if (getString(mDataResourceArray[9]).equals(content)) {
            startActivity(new Intent(MainActivity.this, AnimationLearningActivity.class));
            overridePendingTransition(R.anim.anim_enter, 0);
        } else if (getString(mDataResourceArray[10]).equals(content)) {
            startActivity(new Intent(MainActivity.this, MemoryLeakLearingActivity.class));
        } else if (getString(mDataResourceArray[11]).equals(content)) {
            startActivity(new Intent(MainActivity.this, OtherLearingActivity.class));
        }
    }
}
