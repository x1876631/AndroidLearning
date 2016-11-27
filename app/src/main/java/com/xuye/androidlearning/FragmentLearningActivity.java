package com.xuye.androidlearning;

import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.view.View;
import android.widget.TextView;

import exceptionTest.TestDialogFragment;

/**
 * Created by xuye on 16/11/25
 * 学习fragment用的activity
 */
public class FragmentLearningActivity extends FragmentActivity {
    private TestDialogFragment mDialogFragment;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_fragment_learning);
        initData();
        initView();
    }

    private void initData() {
        mDialogFragment = new TestDialogFragment();
    }

    private void initView() {
        TextView showFragmentButton = (TextView) findViewById(R.id.show_fragment);
        if (showFragmentButton != null) {
            showFragmentButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    mDialogFragment.show(getSupportFragmentManager(), "common");
                }
            });
        }
    }
}
