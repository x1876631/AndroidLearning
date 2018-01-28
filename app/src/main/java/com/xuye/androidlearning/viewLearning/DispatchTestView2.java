package com.xuye.androidlearning.viewLearning;

import android.content.Context;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;

/**
 * Created by xuye on 16/11/1
 * view事件分发学习用的View
 */
public class DispatchTestView2 extends View {

    private static final String tag = "--view2--";

    public DispatchTestView2(Context context) {
        super(context);
    }

    public DispatchTestView2(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public DispatchTestView2(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    public boolean dispatchTouchEvent(MotionEvent event) {
        Log.e(tag, "\ndispatchTouchEvent2 开始执行， 事件是" + event.getActionMasked());
        boolean result = super.dispatchTouchEvent(event);
        Log.e(tag, "dispatchTouchEvent2 执行完毕， 处理结果：" + (result ? "已处理" : "未处理"));
        return result;
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        Log.e(tag, "onTouchEvent2 开始执行， 事件是" + event.getActionMasked());
        boolean result;
//        if (event.getActionMasked() == MotionEvent.ACTION_DOWN) {
            result = true;
//        } else {
//            result = super.onTouchEvent(event);
//        }
        Log.e(tag, "onTouchEvent2 执行完毕， 处理结果：" + (result ? "已处理" : "未处理"));
        return result;
    }
}
