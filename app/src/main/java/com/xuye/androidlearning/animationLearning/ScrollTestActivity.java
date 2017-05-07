package com.xuye.androidlearning.animationLearning;


import android.animation.Animator;
import android.animation.ObjectAnimator;
import android.app.Activity;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewConfiguration;
import android.view.Window;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.SimpleAdapter;

import com.xuye.androidlearning.R;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by xuye on 17/4/26
 * 上下滑动列表，隐藏or展示导航栏，从别的地方copy测试用的
 */
public class ScrollTestActivity extends Activity {

    private ListView mListView;//列表
    private RelativeLayout mTitle;//要隐藏的布局
    private int mTouchSlop;
    private SimpleAdapter mAdapter;
    private float mFirstY;
    private float mCurrentY;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_scroll_test);
        mTouchSlop = ViewConfiguration.get(this).getScaledTouchSlop();
        initViews();
        showHideTitleBar(true);

    }

    private void initViews() {
        mListView = (ListView) findViewById(R.id.id_lv);
        mTitle = (RelativeLayout) findViewById(R.id.id_title);

        mAdapter = new SimpleAdapter(this, getData(),
                R.layout.cell_simple_list_item,
                new String[]{"info"},
                new int[]{R.id.simple_list_item});
        mListView.setAdapter(mAdapter);
        mListView.setOnTouchListener(new View.OnTouchListener() {

            @Override
            public boolean onTouch(View v, MotionEvent event) {
                switch (event.getAction()) {
                    case MotionEvent.ACTION_DOWN:
                        mFirstY = event.getY();
                        break;
                    case MotionEvent.ACTION_MOVE:
                        mCurrentY = event.getY();
                        if (mCurrentY - mFirstY > mTouchSlop) {
                            System.out.println("mtouchislop:" + mTouchSlop);
                            // 下滑 显示titleBar
                            showHideTitleBar(true);
                        } else if (mFirstY - mCurrentY > mTouchSlop) {
                            // 上滑 隐藏titleBar
                            showHideTitleBar(false);
                        }
                        break;
                    case MotionEvent.ACTION_UP:
                        break;
                }
                return false;
            }
        });
    }

    private Animator mAnimatorTitle;
    private Animator mAnimatorContent;

    private void showHideTitleBar(boolean tag) {
        if (mAnimatorTitle != null && mAnimatorTitle.isRunning()) {
            mAnimatorTitle.cancel();
        }
        if (mAnimatorContent != null && mAnimatorContent.isRunning()) {
            mAnimatorContent.cancel();
        }
        if (tag) {
            mAnimatorTitle = ObjectAnimator.ofFloat(mTitle, "translationY", mTitle.getTranslationY(), 0);
            mAnimatorContent = ObjectAnimator.ofFloat(mListView, "translationY", mListView.getTranslationY(),
                    getResources().getDimension(R.dimen.size_20));

        } else {
            mAnimatorTitle =
                    ObjectAnimator.ofFloat(mTitle, "translationY", mTitle.getTranslationY(), -mTitle.getHeight());
            mAnimatorContent = ObjectAnimator.ofFloat(mListView, "translationY", mListView.getTranslationY(), 0);
        }
        mAnimatorTitle.start();
        mAnimatorContent.start();

    }


    private List<Map<String, Object>> getData() {
        List<Map<String, Object>> data = new ArrayList<>();
        for (int i = 'A'; i < 'z'; i++) {
            Map<String, Object> map = new HashMap<>();
            map.put("info", (char) i);
            data.add(map);
        }
        return data;
    }


}
