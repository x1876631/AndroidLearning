package com.xuye.androidlearning.viewLearning;

import android.content.Context;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.widget.RelativeLayout;

/**
 * group事件传递测试：
 * 1、只有自己，都不处理event：dispatchTouchEvent->onInterceptTouchEvent false->onTouchEvent false->dispatchTouchEvent false，当dis返回false时之后不再接收本次序列事件
 * 2、只有自己，onTouch处理：dis->oni false->onT true->dis true，后续事件序列oni不再执行，其他正常走
 * 3、有子view，父都不处理，子view也不消费：dis->oni f->子dis->子ont f->子dis f则交给父类处理->onT f->dis f，本次事件序列都不再传递给group
 * 4、有子，父都不处理，子消费：dis->oni f->子dis->子 onT true->子dis t->dis true，本次事件序列的其他action都继续这么走一遍
 * 5、有子，父都不处理，子只消费down，其他不消费：down时和4一样，move和up时：dis->oni f->子dis->子onT f->子dis f->【(父onT t)不会执行这步，当有view消费时以后都给那个view】->父dis f
 * 6、有子，父拦截down，子消费，父不消费：dis->oni t->onT f->dis f，子不会执行任何操作
 * 7、有子，父拦截down，子消费，父也消费：dis->oni t->onT t->dis T，之后所有action都走：dis->ont t->dis t，因为oni一旦拦截了就只会执行一次
 * 8、有子，父拦截move，子消费，父不消费：
 * down时：dis->oni f->子dis->子 onT t->子dis t->父dis t；move时：子先走一次cancel，之后都是dis->oni t->onT f->dis f，事件序列还走但是都给group
 * <p>
 * 总结：
 * 1、onInterceptTouchEvent如果不拦截会每个action都去执行尝试拦截，如果拦截了就不再执行了
 * 2、如果没有view消费down，则down之后的action都不再传递了
 * 3、一次事件动作(down/move/up)只会有一个view执行onTouch，执行了onTouch的view的所有父view只能去执行dispatchTouch = true
 * 4、只要有view消费事件，down之后的action还会传递过来，只不过不一定是谁消费，比如view消费down但是move被拦截，move和up就都给group
 */
public class DispatchTestViewGroup2 extends RelativeLayout {

    private static final String tag = "--group2--";

    public DispatchTestViewGroup2(Context context) {
        super(context);
    }

    public DispatchTestViewGroup2(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public DispatchTestViewGroup2(Context context, AttributeSet attrs, int defStyleAttr) {
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
        Log.e(tag, "dispatchTouchEvent2 开始执行， 事件是" + ev.getActionMasked());
        boolean result = super.dispatchTouchEvent(ev);
        Log.e(tag, "dispatchTouchEvent2 执行完毕， 处理结果：" + (result ? "已处理" : "未处理"));
        Log.e(tag, "   ");
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
//        Log.e(tag, "onInterceptTouchEvent2 开始执行， 事件是" + ev.getActionMasked());
        boolean result;
//        if (ev.getActionMasked() == MotionEvent.ACTION_MOVE) {
//            result = true;
//        } else {
            result = super.onInterceptTouchEvent(ev);
//        }
        Log.e(tag, "onInterceptTouchEvent2 执行完毕， 处理结果：" + (result ? "已拦截" : "未拦截"));
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
        Log.e(tag, "onTouchEvent2 开始执行， 事件是" + event.getActionMasked());
        boolean result;
//        if (event.getActionMasked() == MotionEvent.ACTION_DOWN) {
//            result = true;
//        } else {
            result = super.onTouchEvent(event);
//        }
        Log.e(tag, "onTouchEvent2 执行完毕， 处理结果：" + (result ? "已处理" : "未处理"));
        return result;
    }
}
