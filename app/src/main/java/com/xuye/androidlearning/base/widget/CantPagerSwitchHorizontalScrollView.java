package com.xuye.androidlearning.base.widget;

import android.content.Context;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.widget.HorizontalScrollView;

/**
 * Created by xuye on 2018/6/27.
 * 实现了嵌套HorizontalScrollView的ViewPager在横滑ScrollView时，不切换pager的功能
 * todo 会影响上下滑流畅体验，暂时留着，以后需要用时再优化
 */
public class CantPagerSwitchHorizontalScrollView extends HorizontalScrollView {

    private float downX;    //按下时 的X坐标
    private float downY;    //按下时 的Y坐标

    public CantPagerSwitchHorizontalScrollView(Context context) {
        super(context);
    }

    public CantPagerSwitchHorizontalScrollView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public CantPagerSwitchHorizontalScrollView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    public boolean dispatchTouchEvent(MotionEvent ev) {
        getParent().requestDisallowInterceptTouchEvent(true);
        return super.dispatchTouchEvent(ev);
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        float x = event.getX();
        float y = event.getY();
        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
                //将按下时的坐标存储
                downX = x;
                downY = y;
                getParent().requestDisallowInterceptTouchEvent(false);
            case MotionEvent.ACTION_UP:
                getParent().requestDisallowInterceptTouchEvent(false);
                break;
            case MotionEvent.ACTION_MOVE:
                //获取滑动时横竖方向上的移动距离
                float dx = x - downX;
                float dy = y - downY;
                if (Math.abs(dx) > 8 || Math.abs(dy) > 8) {
                    //>8 说明确实是在滑动而不是点击
                    if (Math.abs(dx) > Math.abs(dy)) {
                        //横滑切换时，要让横滑栏去处理触摸事件，不让更上层的viewpager拦截掉，这样就实现了横滑到最边缘时，不切换pager的效果
                        getParent().requestDisallowInterceptTouchEvent(true);
                    } else {
                        //竖滑时，恢复父类可能的事件拦截，以便让横滑栏仍能联动整个页面上下滑
                        getParent().requestDisallowInterceptTouchEvent(false);
                    }
                }
        }
        return super.onTouchEvent(event);
    }
}
