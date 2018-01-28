package com.xuye.androidlearning.viewLearning;

import android.animation.ValueAnimator;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.TextView;

import com.xuye.androidlearning.R;

/**
 * Created by xuye on 18/01/16
 * view事件分发学习页
 * 如果groupA包裹groupB包裹view1，且view1消费事件，则点击了view1后的事件分发顺序：
 * A dis->A onIntercept false->B dis->B onIntercept false->view1 dis->view onTouch true->B dis true->A dis true，后续move和up也是这个流程
 */
public class ViewEventLearningActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_event_learning);
    }
}
