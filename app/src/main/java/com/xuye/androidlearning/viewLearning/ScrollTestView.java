package com.xuye.androidlearning.viewLearning;

import android.animation.ObjectAnimator;
import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.Scroller;

/**
 * Created by xuye on 16/10/31
 * 平滑滚动(弹性滑动)的几种实现方式
 * 1、使用scroller
 * 步骤：
 * 1.创建Scroller的实例
 * 2.调用startScroll()方法来初始化滚动数据，并刷新界面
 * 3.重写computeScroll()方法，并在其内部完成平滑滚动的逻辑
 * <p/>
 * 2、使用属性动画
 * 3、使用延时策略，即handler之类的
 */
public class ScrollTestView extends LinearLayout {
    public static final int SCROLL_LENGTH = 150;

    private Scroller mScroller;

    public ScrollTestView(Context context) {
        super(context);
    }

    public ScrollTestView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public ScrollTestView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    /**
     * 使用scroller实现平滑移动
     *
     * @param context context
     * @param destX   x轴要移动的距离
     * @param destY   y轴要移动的距离
     */
    public void smoothScrollWithScroller(Context context, int destX, int destY) {
        if (mScroller == null) {
            mScroller = new Scroller(context);
        }
        int scrollX = getScrollX();
        int deltaX = destX - scrollX;
        //在1000ms内滑动deltaX的距离
        mScroller.startScroll(scrollX, 0, deltaX, 0, 1000);
        invalidate();
    }

    /**
     * 此函数view绘制时会触发
     * 配合scroller可以实现让此view的内容平滑地滑动
     */
    @Override
    public void computeScroll() {
        if (mScroller != null && mScroller.computeScrollOffset()) {
            //如果滑动没结束，则继续滑动
            //注意，scrollTo滑动的是当前view的内容，而不是当前view，所以如果想看滑动效果，要在这个view的里面加个控件
            scrollTo(mScroller.getCurrX(), mScroller.getCurrY());

            //不断地绘制，又不断地触发computeScroll，直到滑动结束
            postInvalidate();
        }
    }

    /**
     * 使用动画实现平滑滚动
     *
     * @param targetView   要滚动的view
     * @param scrollLength 滚动距离
     */
    public void smoothScrollWithAnimator(View targetView, int scrollLength) {
        ObjectAnimator.ofFloat(targetView, "translationX", 0, scrollLength).setDuration(1000).start();
    }

}
