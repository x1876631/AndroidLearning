package viewLearning;

import android.content.Context;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;

/**
 * Created by xuye on 16/11/1
 * view事件分发学习用的View
 */
public class DispatchTestView extends View {

    private static final String tag = DispatchTestView.class.getSimpleName();

    public DispatchTestView(Context context) {
        super(context);
    }

    public DispatchTestView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public DispatchTestView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    public boolean dispatchTouchEvent(MotionEvent event) {
        Log.e(tag, "dispatchTouchEvent 开始执行， 事件是" + event.getActionMasked());
        boolean result = super.dispatchTouchEvent(event);
        Log.e(tag, "dispatchTouchEvent 执行完毕， 处理结果：" + (result ? "已处理" : "未处理"));
        return result;
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        Log.e(tag, "onTouchEvent 开始执行， 事件是" + event.getActionMasked());
        boolean result = super.onTouchEvent(event);
//        boolean result = true;
        Log.e(tag, "onTouchEvent 执行完毕， 处理结果：" + (result ? "已处理" : "未处理"));
        return result;
    }
}
