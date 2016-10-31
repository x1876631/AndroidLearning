package com.xuye.androidlearning;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

import viewLearning.ScrollTestView;

public class MainActivity extends AppCompatActivity {

    private ScrollTestView mScrollTestView;
    private View mTestView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initView();
//        setTestScroller();
        setScrollAnimator();
    }

    private void initView() {
        mScrollTestView = (ScrollTestView) findViewById(R.id.ScrollTestView);
        mTestView = findViewById(R.id.testView);
    }

    private void setTestScroller() {
        if (mScrollTestView != null) {
            mScrollTestView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    mScrollTestView.smoothScrollWithScroller(MainActivity.this, ScrollTestView.SCROLL_LENGTH, 0);
                }
            });
        }
    }

    private void setScrollAnimator() {
        mScrollTestView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mScrollTestView.smoothScrollWithAnimator(mTestView, ScrollTestView.SCROLL_LENGTH);
            }
        });
    }
}
