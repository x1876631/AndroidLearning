package com.xuye.androidlearning.viewLearning;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

import com.xuye.androidlearning.R;

/**
 * Created by xuye on 16/11/18
 * view学习相关的页面
 */
public class ViewLearningActivity extends AppCompatActivity {
    private ScrollTestView mScrollTestView;
    private View mTestView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_learning);

    }

    private void scrollTest() {
//        initScrollTestView();
//        setTestScroller();
//        setScrollAnimator();
    }

//    private void initScrollTestView() {
//        mScrollTestView = (ScrollTestView) findViewById(R.id.ScrollTestView);
//        mTestView = findViewById(R.id.testView);
//    }

    private void setTestScroller() {
        if (mScrollTestView != null) {
            mScrollTestView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    mScrollTestView
                            .smoothScrollWithScroller(ViewLearningActivity.this, ScrollTestView.SCROLL_LENGTH, 0);
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
