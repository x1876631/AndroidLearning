package com.xuye.androidlearning;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.TextView;

/**
 * Created by xuye on 17/01/06
 * activity启动方式学习测试页2
 */
public class LaunchLearingActivity2 extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_common_test);
        initView();
    }

    private void initView() {
        TextView textView = ((TextView) findViewById(R.id.common_test_activity_button));
        if (textView != null) {
            textView.setText(R.string.lauch_activity_2);
            textView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    startActivity(new Intent(LaunchLearingActivity2.this, LaunchLearingActivity3.class));
                }
            });
        }
    }
}
