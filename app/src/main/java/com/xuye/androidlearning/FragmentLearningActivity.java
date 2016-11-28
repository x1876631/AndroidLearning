package com.xuye.androidlearning;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.TextView;

import exceptionTest.TestDialogFragment;

/**
 * Created by xuye on 16/11/25
 * 学习fragment用的activity
 */
public class FragmentLearningActivity extends AppCompatActivity {
    private TestDialogFragment mDialogFragment;
    private static final String DIALOG_TAG = "common";

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
        TextView showFragmentButton = (TextView) findViewById(R.id.show_fragment1);
        if (showFragmentButton != null) {
            showFragmentButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    mDialogFragment.show(getSupportFragmentManager(), DIALOG_TAG);
                }
            });
        }

        TextView showFragmentButton2 = (TextView) findViewById(R.id.show_fragment2);
        if (showFragmentButton2 != null) {
            showFragmentButton2.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    mDialogFragment.show(getSupportFragmentManager(), DIALOG_TAG);
                }
            });
        }

        TextView showFragmentButton3 = (TextView) findViewById(R.id.show_fragment3);
        if (showFragmentButton3 != null) {
            showFragmentButton3.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    mDialogFragment.show(getSupportFragmentManager(), DIALOG_TAG);
                }
            });
        }
    }
}
