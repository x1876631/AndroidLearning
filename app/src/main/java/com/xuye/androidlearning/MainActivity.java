package com.xuye.androidlearning;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initData();
        initView();
    }

    private void initData() {
    }

    private void initView() {
        TextView viewLearningButton = (TextView) findViewById(R.id.view_learning_button);
        if (viewLearningButton != null) {
            viewLearningButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    startActivity(new Intent(MainActivity.this, ViewLearningActivity.class));
                }
            });
        }

        TextView fragmentLearningButton = (TextView) findViewById(R.id.fragment_learning_button);
        if (fragmentLearningButton != null) {
            fragmentLearningButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    startActivity(new Intent(MainActivity.this, FragmentLearningActivity.class));
                }
            });
        }

    }
}
