package viewLearning;

import android.content.Context;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.widget.RelativeLayout;

/**
 * Created by xuye on 16/11/1
 * view事件分发学习用的ViewGroup
 * 触摸事件传递的过程：
 * 1、当有触摸事件传递到这个ViewGroup时，ViewGroup会调用dispatchTouchEvent去分发该事件。
 * 2、分发事件过程中，会先调用onInterceptTouchEvent检查是否该ViewGroup是否要拦截事件的处理。
 * 2.1、如果onInterceptTouchEvent返回true，表示该触摸事件ViewGroup要自己处理。然后去执行ViewGroup自己的onTouchEvent。
 * 2.2、如果onInterceptTouchEvent返回false，则ViewGroup会遍历子view，依次调用他们的dispatchTouchEvent，把事件传递下去。
 * <p/>
 * <p/>
 * 触摸事件处理的过程：
 * 1、该ViewGroup没有子view，或者有子view但没有把事件传递下去(即拦截了)，则会调用自己的onTouchEvent去处理。
 * <p/>
 * 2、该ViewGroup有子view，而且也把事件传递给子view去执行了：
 * 子view的onTouchEvent执行完后，会返回事件处理结果给子view的dispatchTouchEvent。
 * 子view的dispatchTouchEvent再把事件的处理结果返回给ViewGroup的dispatchTouchEvent。
 * 2.1、如果有子View已经处理了，则ViewGroup的dispatchTouchEvent也返回true。
 * 2.2、如果子View都没有处理，则ViewGroup调用自己的onTouchEvent去处理。
 * <p/>
 * <p/>
 * <p/>
 * 一些发现与疑问：
 * 1、如果viewGroup没有处理down，则后续的up之类的也不会处理了。为什么?
 * 2、如果onInterceptTouchEvent执行了，则后续事件默认全部拦截，都由ViewGroup执行。为什么?
 * 3、如果子view处理了down事件，则后续事件都由子view处理了。为什么？
 */
public class DispatchTestViewGroup extends RelativeLayout {

    private static final String tag = DispatchTestViewGroup.class.getSimpleName();

    public DispatchTestViewGroup(Context context) {
        super(context);
    }

    public DispatchTestViewGroup(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public DispatchTestViewGroup(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    /**
     * 触摸事件分发函数
     *
     * @param ev 触摸事件
     * @return 是否处理了触摸事件。true代表已经处理了触摸事件，不用再交给上级处理
     */
    @Override
    public boolean dispatchTouchEvent(MotionEvent ev) {
        Log.e(tag, "dispatchTouchEvent 开始执行， 事件是" + ev.getActionMasked());
        boolean result = super.dispatchTouchEvent(ev);
        Log.e(tag, "dispatchTouchEvent 执行完毕， 处理结果：" + (result ? "已处理" : "未处理"));
        return result;
    }

    /**
     * 触摸事件拦截函数
     *
     * @param ev 触摸事件
     * @return 是否拦截触摸事件的下发。返回true表示触摸事件不再下发给子view，而是调用自己的onTouch函数处理
     */
    @Override
    public boolean onInterceptTouchEvent(MotionEvent ev) {
        Log.e(tag, "onInterceptTouchEvent 开始执行， 事件是" + ev.getActionMasked());
        boolean result = super.onInterceptTouchEvent(ev);
//        boolean result = true;
        Log.e(tag, "onInterceptTouchEvent 执行完毕， 处理结果：" + (result ? "已拦截" : "未拦截"));
        return result;
    }

    /**
     * 触摸事件处理函数
     *
     * @param event 触摸事件
     * @return 是否处理了触摸事件。返回true代表触摸事件已处理，触摸事件到此为止，不再交给父容器处理
     */
    @Override
    public boolean onTouchEvent(MotionEvent event) {
        Log.e(tag, "onTouchEvent 开始执行， 事件是" + event.getActionMasked());
        boolean result = super.onTouchEvent(event);
//        boolean result = true;
        Log.e(tag, "onTouchEvent 执行完毕， 处理结果：" + (result ? "已处理" : "未处理"));
        return result;
    }
}
