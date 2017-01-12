package com.xuye.androidlearning;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import com.xuye.androidlearning.componentLearning.LaunchLearingActivity;
import com.xuye.androidlearning.componentLearning.ServiceLearingActivity;
import com.xuye.androidlearning.fragmentLearning.FragmentLearningActivity;
import com.xuye.androidlearning.viewLearning.ViewLearningActivity;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity implements AdapterView.OnItemClickListener {

    //列表项内容文案
    private ArrayList<String> mData;

    //列表项内容文案资源id，如果想增加列表项，每次需要加文案和点击事件
    private int[] mDataResourceArray = {
            R.string.main_jump_view_learning, R.string.main_jump_fragment_learning,
            R.string.main_jump_lauch_learning, R.string.main_jump_service_learning
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
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
        }
    }
}
