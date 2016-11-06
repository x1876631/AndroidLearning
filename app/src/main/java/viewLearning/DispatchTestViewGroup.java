package viewLearning;

import android.content.Context;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.widget.RelativeLayout;

/**
 * Created by xuye on 16/11/1
 * view触摸事件分发学习用的ViewGroup
 * --------【事件传递的过程】--------
 * 1、当有触摸事件传递到这个ViewGroup时，ViewGroup会调用dispatchTouchEvent去分发该事件。
 * 2、分发事件过程中，会先调用onInterceptTouchEvent检查是否该ViewGroup是否要拦截事件的处理。
 * 2.1、如果onInterceptTouchEvent返回true，表示该触摸事件ViewGroup要自己处理。然后去执行ViewGroup自己的onTouchEvent。
 * 2.2、如果onInterceptTouchEvent返回false，则ViewGroup会遍历子view，依次调用他们的dispatchTouchEvent，把事件传递下去。
 * <p/>
 * <p/>
 * --------【事件处理的过程】--------
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
 * --------【学习后的一些疑问】--------
 * 1、如果viewGroup没有处理down，则后续的up之类的也不会处理了。为什么?
 * 【答】
 * 我猜是交给上级处理了，然后整个事件序列都交给上级了。
 * 我们把viewGroup的onTouchEvent返回true，不让上级处理再看一下。
 * 结果确实如我所猜想，当viewGroup处理down后，后续的事件都交给viewGroup处理了。
 * <p/>
 * 话说为什么一个view处理了事件后，后续的事件都由这个view处理了？
 * 【答】
 * 1、父容器为什么不处理：如果view会告诉父容器，事件它自己处理了。那么父容器在下发事件时得知子view要处理，父容器就不执行自己的处理函数了。
 * 2、其他子view为什么不处理：viewGroup会遍历子view选择之前处理了事件的那个view处理后续事件
 *
 * <p/>
 * 2、如果onInterceptTouchEvent执行一次后，后续事件默认全部拦截，都由ViewGroup处理。为什么?
 * 【答】当ViewGroup拦截了一次以后，ViewGroup调用onTouchEvent处理了down事件，回导致mFirstTouchTarget==null。
 * 后续的事件(比如up)在判断是否拦截时，一看mFirstTouchTarget==null，就知道ViewGroup要自己处理时间，不再执行拦截判断函数，就自动拦截了。
 * <p/>
 * <p/>
 * 那为什么不用再执行拦截判断函数了？因为现在已经知道ViewGroup要自己处理事件了。不用再去判断是拦截给自己处理，还是不拦截下发事件。
 * 至于为什么知道ViewGroup要自己处理事件，和mFirstTouchTarget的值有关系。
 * 当事件不是down且mFirstTouchTarget为空，就代表ViewGroup要自己处理事件了。具体原因如下：
 * 首先看代码：
 * if (actionMasked == MotionEvent.ACTION_DOWN || mFirstTouchTarget != null) {
 * //这里才可能去执行onInterceptTouchEvent
 * }else{}
 * // There are no touch targets and this action is not an initial down
 * // so this view group continues to intercept touches.
 * intercepted = true;
 * }
 * <p/>
 * viewGroup在下发事件的时候，每次都会先检查下是否拦截事件。
 * 如果当前是down事件，或者有touch target的时候才去判断是否需要拦截。如果既没有touch target又不是down，则直接拦截，自己去处理。
 * 那问题又来了，如果不是down的情况下，仍然拦截，说明mFirstTouchTarget==null。mFirstTouchTarget是干嘛的呢？
 * 继续看代码，发现如果有子view且处理了事件，则mFirstTouchTarget不为空。
 * 那反过来说，当mFirstTouchTarget为空则代表：
 * 1.有子view但子view不处理，事件交给viewGroup
 * 2.没有子view，viewGroup只能都交给自己去处理事件
 * 对于1：有子view，但是子view没有想处理(子view如果处理了的话，mFirstTouchTarget就不会为空了)。
 * 那代表后续的事件就都要交给viewGroup处理了，所以down之后的事件不用再下发了。直接拦截，交给viewGroup处理
 * 对于2：没有子view。那所有事件都应该交给自己处理。所以自然也直接拦截。
 *
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
